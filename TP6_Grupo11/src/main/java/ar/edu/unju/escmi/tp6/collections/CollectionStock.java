package ar.edu.unju.escmi.tp6.collections;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.Stock;

public class CollectionStock {

    public static List<Stock> stock = new ArrayList<Stock>();

    public static void precargarStock() {
        if (stock.isEmpty()) {
            for (Producto p : CollectionProducto.productos) {
                stock.add(new Stock(p.getStock(), p));
            }
        }
    }

    public static void agregarStock(Stock nuevo) {
        if (nuevo == null) {
            System.out.println("NO SE PUEDE GUARDAR EL STOCK: dato nulo");
            return;
        }
        if (buscarStock(nuevo.getProducto().getCodigo()) != null) {
            System.out.println("YA EXISTE UN STOCK REGISTRADO PARA ESTE PRODUCTO");
            return;
        }
        stock.add(nuevo);
    }

    public static Stock buscarStock(long buscado) {
        for (Stock sto : stock) {
            if (sto.getProducto() != null && sto.getProducto().getCodigo() == buscado) {
                return sto;
            }
        }
        return null;
    }

    public static Stock buscarEfectivoStock(Producto producto) {
        if (producto == null) return null;
        return buscarStock(producto.getCodigo());
    }

    public static void modificarStock(Stock stockModificado, int cantidad) {
        if (stockModificado != null && cantidad >= 0) {
            stockModificado.actualizarStock(cantidad);
        }
    }

    public static void mostrarStockAhora20() {
        System.out.println("\n--- STOCK DE ELECTRODOMÉSTICOS AHORA 20 ---");
        for (Stock sto : stock) {
            System.out.println("Producto: " + sto.getProducto().getDescripcion() 
                    + " | Cantidad disponible: " + sto.getCantidad());
        }
    }
}