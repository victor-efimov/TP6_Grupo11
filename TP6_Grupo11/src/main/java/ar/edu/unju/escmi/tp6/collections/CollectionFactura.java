package ar.edu.unju.escmi.tp6.collections;

import java.util.ArrayList;
import java.util.List;

import main.java.ar.edu.unju.escmi.tp6.dominio.Factura;

public class CollectionFactura {

    public static List<Factura> facturas = new ArrayList<>();

    public static void agregarFactura(Factura factura) {
        if (factura == null) {
            System.out.println("NO SE PUEDE GUARDAR LA FACTURA: dato nulo");
            return;
        }
        facturas.add(factura);
    }

    public static void buscarFacturasPorDni(long dni) {
        System.out.println("\nBUSCANDO FACTURAS/COMPRAS PARA DNI: " + dni);
        boolean encontrado = false;

        for (Factura f : facturas) {
            if (f.getCliente() != null && f.getCliente().getDni() == dni) {
                System.out.println("\n----------------------------------");
                System.out.println(f);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron compras/facturas para el DNI " + dni);
        }
    }
}