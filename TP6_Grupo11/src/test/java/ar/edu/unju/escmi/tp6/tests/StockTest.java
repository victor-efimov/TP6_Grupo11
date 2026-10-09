package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.Stock;

class StockTest {

    private Stock stock;

    @BeforeEach
    void setUp() {
        Producto producto = new Producto(1L, "Lavarropas", 500000.0, "Nacional", 10, false);
        stock = new Stock(10, producto);
    }

    @Test
    void testStockSeDecrementaEnLaCantidadIndicada() {
        stock.descontarStock(3);
        assertEquals(7, stock.getCantidad());
    }

    @Test
    void testNoPermiteDescontarMasQueElStockDisponible() {
        assertThrows(IllegalArgumentException.class, () -> stock.descontarStock(11));
        assertEquals(10, stock.getCantidad());
    }

    @Test
    void testValidarStockDisponible() {
        assertTrue(stock.validarStockDisponible());
    }
}
