package ar.edu.unju.escmi.tp6.collections;

import java.util.ArrayList;
import java.util.List;

import main.java.ar.edu.unju.escmi.tp6.dominio.Producto;

public class CollectionProducto {

    public static List<Producto> productos = new ArrayList<>();

    public static void precargarProductos() {
        if (productos.isEmpty()) {
            productos.add(new Producto(101, "Smart TV 50'' 4K", 650000.0, "Nacional", 10, false));
            productos.add(new Producto(102, "Aire Acondicionado Inverter 3000FC", 850000.0, "Nacional", 5, false));
            productos.add(new Producto(103, "Heladera No Frost 330L", 920000.0, "Nacional", 8, false));
            productos.add(new Producto(104, "Lavarropas Automático 7kg", 540000.0, "Nacional", 12, false));
            productos.add(new Producto(105, "Celular 5G 128GB", 450000.0, "Nacional", 15, true));
        }
    }

    public static void agregarProducto(Producto producto) {
        if (producto == null) {
            System.out.println("NO SE PUEDE GUARDAR EL PRODUCTO: dato nulo");
            return;
        }
        if (buscarProducto(producto.getCodigo()) != null) {
            System.out.println("YA EXISTE UN PRODUCTO CON ESE CÓDIGO");
            return;
        }
        productos.add(producto);
    }

    public static Producto buscarProducto(long codigo) {
        for (Producto p : productos) {
            if (p.getCodigo() == codigo) {
                return p;
            }
        }
        return null;
    }

    public static void buscarProductoPorOrigen(String origen) {
        System.out.println("\n--- PRODUCTOS ORIGEN: " + origen + " ---");
        boolean encontrado = false;
        for (Producto p : productos) {
            if (p.getOrigenFabricacion().equalsIgnoreCase(origen)) {
                System.out.println(p);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontraron productos con origen " + origen);
        }
    }

    public static void mostrarProductosAhora20() {
        System.out.println("\n--- PRODUCTOS DISPONIBLES AHORA 20 ---");
        for (Producto p : productos) {
            System.out.println(p);
        }
    }
}