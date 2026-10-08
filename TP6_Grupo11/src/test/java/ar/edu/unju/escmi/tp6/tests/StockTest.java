package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.java.ar.edu.unju.escmi.tp6.dominio.Producto;
import main.java.ar.edu.unju.escmi.tp6.dominio.Stock;

class StockTest {

    @Test
    void testStockSeReduceEnCantidadIndicada() {

        Producto producto = new Producto(
                1L,
                "Lavarropas",
                500000.0,
                "Nacional",
                10,
                false
        );

        Stock stock = new Stock(10, producto);

        int cantidad = 3;

        stock.actualizarStock(stock.getCantidad() - cantidad);

        assertEquals(7, stock.getCantidad());
    }
}