package ar.edu.unju.escmi.tp6.main;

import java.time.LocalDate;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import ar.edu.unju.escmi.tp6.collections.CollectionCliente;
import ar.edu.unju.escmi.tp6.collections.CollectionCredito;
import ar.edu.unju.escmi.tp6.collections.CollectionFactura;
import ar.edu.unju.escmi.tp6.collections.CollectionProducto;
import ar.edu.unju.escmi.tp6.collections.CollectionStock;
import ar.edu.unju.escmi.tp6.collections.CollectionTarjetaCredito;
import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Detalle;
import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.Stock;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;
import ar.edu.unju.escmi.tp6.servicios.VentaAhora20;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            CollectionCliente.precargarClientes();
            CollectionProducto.precargarProductos();
            CollectionStock.precargarStock();
            CollectionTarjetaCredito.precargarTarjetas();
            ejecutarMenu(scanner, new VentaAhora20());
        } finally {
            scanner.close();
        }
    }

    static void ejecutarMenu(Scanner scanner, VentaAhora20 ventas) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- SISTEMA DE VENTAS 'AHORA 20' ---");
            System.out.println("1. Realizar una venta con programa 'Ahora 20'");
            System.out.println("2. Ver compras realizadas por el cliente");
            System.out.println("3. Lista de los electrodomésticos del programa");
            System.out.println("4. Consultar stock de los electrodomésticos");
            System.out.println("5. Revisar los créditos de un cliente");
            System.out.println("6. Salir");
            try {
                long opcion = leerNumero(scanner, "Elija una opción: ");
                if (opcion < 1 || opcion > 6) {
                    throw new IllegalArgumentException("Elija un número del 1 al 6.");
                }
                switch ((int) opcion) {
                    case 1:
                        realizarVenta(scanner, ventas);
                        break;
                    case 2:
                        CollectionFactura.buscarFacturasPorDni(leerDni(scanner));
                        break;
                    case 3:
                        CollectionProducto.mostrarProductosAhora20();
                        break;
                    case 4:
                        CollectionStock.mostrarStockAhora20();
                        break;
                    case 5:
                        CollectionCredito.buscarCreditoPorDni(leerDni(scanner));
                        break;
                    case 6:
                        salir = true;
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        break;
                }
            } catch (IllegalArgumentException | IllegalStateException | DateTimeException e) {
                System.out.println("No se pudo completar la operación: " + e.getMessage());
            } catch (NoSuchElementException e) {
                salir = true;
                System.out.println("Fin de la entrada. Saliendo del sistema...");
            }
        }
    }

    private static void realizarVenta(Scanner scanner, VentaAhora20 ventas) {
        ventas.validarVigencia();
        System.out.println("\nClientes registrados:");
        for (Cliente cliente : CollectionCliente.clientes) {
            System.out.println("DNI: " + cliente.getDni() + " | " + cliente.getNombre());
        }
        long dni = leerDni(scanner);
        Cliente cliente = CollectionCliente.buscarCliente(dni);
        if (cliente == null) {
            System.out.println("Cliente nuevo. Complete sus datos:");
            cliente = new Cliente(dni, leerTexto(scanner, "Nombre: "),
                    leerTexto(scanner, "Dirección: "), leerTexto(scanner, "Teléfono: "));
        }

        TarjetaCredito tarjeta = seleccionarTarjeta(scanner, cliente);
        List<Detalle> detalles = new ArrayList<>();
        CollectionProducto.mostrarProductosAhora20();
        while (true) {
            long codigo = leerNumero(scanner, "Código del producto (0 para finalizar): ");
            if (codigo == 0) break;
            Producto producto = CollectionProducto.buscarProducto(codigo);
            if (!CollectionProducto.esProductoAhora20(producto)) {
                throw new IllegalArgumentException("El producto no existe o no está incluido en Ahora 20.");
            }
            long cantidad = leerNumero(scanner, "Cantidad: ");
            if (cantidad <= 0 || cantidad > Integer.MAX_VALUE) {
                throw new IllegalArgumentException("La cantidad debe ser un entero positivo válido.");
            }
            long acumulada = cantidad;
            for (Detalle detalle : detalles) {
                if (detalle.getProducto().getCodigo() == codigo) acumulada += detalle.getCantidad();
            }
            Stock stock = CollectionStock.buscarStock(codigo);
            if (stock == null || acumulada > stock.getCantidad() || acumulada > producto.getStock()) {
                throw new IllegalArgumentException("Stock insuficiente para la cantidad acumulada del producto.");
            }
            detalles.add(new Detalle((int) cantidad, producto));
            System.out.println("Producto agregado a la compra.");
        }
        if (detalles.isEmpty()) {
            System.out.println("Venta cancelada: no se seleccionaron productos.");
            return;
        }

        double total = 0;
        System.out.println("\nResumen de compra de " + cliente.getNombre() + ":");
        for (Detalle detalle : detalles) {
            System.out.println(detalle);
            total += detalle.getImporte();
        }
        System.out.printf("TOTAL: $%.2f | %d cuotas de $%.2f%n", total, Credito.CUOTAS_MAXIMAS,
                total / Credito.CUOTAS_MAXIMAS);
        String confirmar = leerTexto(scanner, "¿Confirmar venta? (S/N): ");
        if ("N".equalsIgnoreCase(confirmar)) {
            System.out.println("Venta cancelada.");
            return;
        }
        if (!"S".equalsIgnoreCase(confirmar)) {
            throw new IllegalArgumentException("Debe confirmar con S o cancelar con N.");
        }
        Credito credito = ventas.registrarVenta(cliente, tarjeta, detalles);
        System.out.println("\nVenta registrada correctamente.");
        credito.mostrarCredito();
    }

    private static TarjetaCredito seleccionarTarjeta(Scanner scanner, Cliente cliente) {
        System.out.println("\nTarjetas del cliente:");
        boolean encontrada = false;
        for (TarjetaCredito tarjeta : CollectionTarjetaCredito.tarjetas) {
            if (tarjeta.getCliente() != null && tarjeta.getCliente().getDni() == cliente.getDni()) {
                System.out.println(tarjeta);
                encontrada = true;
            }
        }
        if (!encontrada) System.out.println("No tiene tarjetas registradas.");
        long numero = leerNumero(scanner, "Número de tarjeta (0 para registrar una nueva): ");
        if (numero != 0) {
            TarjetaCredito tarjeta = CollectionTarjetaCredito.buscarTarjetaCredito(numero);
            if (tarjeta == null || tarjeta.getCliente() == null
                    || tarjeta.getCliente().getDni() != cliente.getDni()) {
                throw new IllegalArgumentException("La tarjeta no está registrada para este cliente.");
            }
            return tarjeta;
        }
        numero = leerNumero(scanner, "Número de la nueva tarjeta: ");
        if (numero <= 0 || CollectionTarjetaCredito.buscarTarjetaCredito(numero) != null) {
            throw new IllegalArgumentException("Número de tarjeta inválido o ya registrado.");
        }
        String tipo = leerTexto(scanner, "Tipo de tarjeta: ");
        LocalDate caducacion = LocalDate.parse(leerTexto(scanner, "Fecha de caducación (AAAA-MM-DD): "));
        double limite = leerMonto(scanner, "Límite de compra disponible: ");
        return new TarjetaCredito(numero, caducacion, tipo, cliente, limite);
    }

    private static long leerDni(Scanner scanner) {
        long dni = leerNumero(scanner, "Ingrese el DNI del cliente: ");
        if (dni <= 0) throw new IllegalArgumentException("El DNI debe ser positivo.");
        return dni;
    }

    private static long leerNumero(Scanner scanner, String mensaje) {
        String entrada = leerTexto(scanner, mensaje);
        try {
            return Long.parseLong(entrada);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Debe ingresar un número entero válido.");
        }
    }

    private static double leerMonto(Scanner scanner, String mensaje) {
        String entrada = leerTexto(scanner, mensaje);
        try {
            double monto = Double.parseDouble(entrada.replace(',', '.'));
            if (!Double.isFinite(monto) || monto <= 0) {
                throw new IllegalArgumentException("El monto debe ser positivo y finito.");
            }
            return monto;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ingrese un monto sin separadores de miles.");
        }
    }

    private static String leerTexto(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        String entrada = scanner.nextLine().trim();
        if (entrada.isEmpty()) throw new IllegalArgumentException("El dato no puede estar vacío.");
        return entrada;
    }
}
