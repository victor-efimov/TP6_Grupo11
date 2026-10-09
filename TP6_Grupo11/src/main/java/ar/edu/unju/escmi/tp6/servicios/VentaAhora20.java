package ar.edu.unju.escmi.tp6.servicios;

import java.time.Clock;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ar.edu.unju.escmi.tp6.collections.CollectionCliente;
import ar.edu.unju.escmi.tp6.collections.CollectionCredito;
import ar.edu.unju.escmi.tp6.collections.CollectionFactura;
import ar.edu.unju.escmi.tp6.collections.CollectionProducto;
import ar.edu.unju.escmi.tp6.collections.CollectionStock;
import ar.edu.unju.escmi.tp6.collections.CollectionTarjetaCredito;
import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Detalle;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.Stock;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;

public class VentaAhora20 {
    public static final LocalDate FIN_PROGRAMA = LocalDate.of(2026, 12, 22);
    public static final double MONTO_MAXIMO_CELULARES = 1000000.0;
    private final Clock reloj;

    public VentaAhora20() {
        this(Clock.systemDefaultZone());
    }

    public VentaAhora20(Clock reloj) {
        this.reloj = reloj;
    }

    public void validarVigencia() {
        if (LocalDate.now(reloj).isAfter(FIN_PROGRAMA)) {
            throw new IllegalStateException("El programa Ahora 20 finalizó el 22/12/2026.");
        }
    }

    public Credito registrarVenta(Cliente cliente, TarjetaCredito tarjeta, List<Detalle> detalles) {
        validarVigencia();
        LocalDate fechaVenta = LocalDate.now(reloj);
        if (cliente == null || cliente.getDni() <= 0 || tarjeta == null || tarjeta.getNumero() <= 0
                || tarjeta.getCliente() == null || tarjeta.getCliente().getDni() != cliente.getDni()) {
            throw new IllegalArgumentException("Cliente o tarjeta inválidos; la tarjeta debe pertenecer al cliente.");
        }
        TarjetaCredito registrada = CollectionTarjetaCredito.buscarTarjetaCredito(tarjeta.getNumero());
        if (registrada != null && registrada != tarjeta) {
            throw new IllegalArgumentException("El número de tarjeta ya está registrado.");
        }
        if (tarjeta.getFechaCaducacion() == null || tarjeta.getFechaCaducacion().isBefore(fechaVenta)) {
            throw new IllegalArgumentException("La tarjeta está vencida.");
        }
        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException("La compra debe tener al menos un producto.");
        }

        Map<Producto, Integer> cantidades = new LinkedHashMap<>();
        double total = 0;
        double totalCelulares = 0;
        for (Detalle detalle : detalles) {
            if (detalle == null || detalle.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
            }
            Producto producto = detalle.getProducto();
            if (!CollectionProducto.esProductoAhora20(producto)
                    || CollectionProducto.buscarProducto(producto.getCodigo()) != producto) {
                throw new IllegalArgumentException("El producto no pertenece al catálogo nacional de Ahora 20.");
            }
            double precio = producto.getPrecioUnitario();
            double importe = detalle.calcularImporte();
            if (!Double.isFinite(precio) || precio <= 0 || !Double.isFinite(importe)
                    || Double.compare(detalle.getImporte(), importe) != 0) {
                throw new IllegalArgumentException("Precio o importe del detalle inválido.");
            }
            long cantidad = (long) cantidades.getOrDefault(producto, 0) + detalle.getCantidad();
            Stock stock = CollectionStock.buscarEfectivoStock(producto);
            if (stock == null || cantidad > stock.getCantidad() || cantidad > producto.getStock()) {
                throw new IllegalArgumentException("Stock insuficiente para " + producto.getDescripcion() + ".");
            }
            cantidades.put(producto, (int) cantidad);
            total += importe;
            if (producto.isEsCelular()) totalCelulares += importe;
        }
        if (!Double.isFinite(total) || total > Credito.MONTO_MAXIMO) {
            throw new IllegalArgumentException("La compra supera el máximo de $2.500.000.");
        }
        if (totalCelulares > MONTO_MAXIMO_CELULARES) {
            throw new IllegalArgumentException("La compra de celulares supera el máximo de $1.000.000.");
        }
        if (!Double.isFinite(tarjeta.getLimiteCompra()) || total > tarjeta.getLimiteCompra()
                || !Double.isFinite(tarjeta.getSaldoDisponible()) || !tarjeta.tieneSaldoSuficiente(total)) {
            throw new IllegalStateException("La tarjeta no tiene límite o saldo suficiente para esta compra.");
        }

        Factura factura = new Factura(cliente, fechaVenta);
        for (Detalle detalle : detalles) {
            factura.agregarDetalle(new Detalle(detalle.getCantidad(), detalle.getProducto()));
        }
        Credito credito = new Credito(tarjeta, factura, cliente, total);
        credito.generarCuotas();

        // Se actualiza el sistema solamente después de validar toda la compra.
        tarjeta.descontarMonto(total);
        for (Map.Entry<Producto, Integer> entrada : cantidades.entrySet()) {
            Producto producto = entrada.getKey();
            CollectionStock.buscarEfectivoStock(producto).descontarStock(entrada.getValue());
            producto.reducirStock(entrada.getValue());
        }
        if (CollectionCliente.buscarCliente(cliente.getDni()) == null) CollectionCliente.agregarCliente(cliente);
        if (registrada == null) CollectionTarjetaCredito.agregarTarjetaCredito(tarjeta);
        CollectionFactura.agregarFactura(factura);
        CollectionCredito.agregarCredito(credito);
        return credito;
    }
}
