package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.Stock;

class StockTest {

    private Producto producto;
    private Stock stock;

    @BeforeEach
    void setUp() {
        producto = new Producto(10, "Aire Acondicionado", 900000.0);
        stock = new Stock(producto, 15);
    }

    @Test
    void testDecrementarStock() {
        int cantidadAComprar = 3;
        stock.decrementarStock(cantidadAComprar);

        assertEquals(12, stock.getCantidad(), "El stock debio decrementar de 15 a 12 unidades");
    }
}