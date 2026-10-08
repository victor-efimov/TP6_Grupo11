package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.java.ar.edu.unju.escmi.tp6.dominio.Cliente;
import main.java.ar.edu.unju.escmi.tp6.dominio.Credito;
import main.java.ar.edu.unju.escmi.tp6.dominio.Detalle;
import main.java.ar.edu.unju.escmi.tp6.dominio.Factura;
import main.java.ar.edu.unju.escmi.tp6.dominio.Producto;
import main.java.ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;

class CuotaTest {

    @Test
    void testListaCuotasNoSeaNull() {
        Cliente cliente = new Cliente(12345678L, "Juan", "Perez", "3881234567");

        TarjetaCredito tarjeta = new TarjetaCredito(
                4555111122223333L,
                java.time.LocalDate.of(2030, 12, 31),
                cliente,
                3000000.0
        );

        Producto producto = new Producto(
                1L,
                "Lavarropas",
                500000.0,
                "Nacional",
                10,
                false
        );

        Detalle detalle = new Detalle(1, producto);

        Factura factura = new Factura(cliente);
        factura.agregarDetalle(detalle);

        Credito credito = new Credito(
                tarjeta,
                factura,
                cliente,
                500000.0
        );

        credito.generarCuotas();

        assertNotNull(credito.getCuotas());
    }

    @Test
    void testListaCuotasSiempreTiene20Cuotas() {
        Cliente cliente = new Cliente(12345678L, "Juan", "Perez", "3881234567");

        TarjetaCredito tarjeta = new TarjetaCredito(
                4555111122223333L,
                java.time.LocalDate.of(2030, 12, 31),
                cliente,
                3000000.0
        );

        Producto producto = new Producto(
                1L,
                "Lavarropas",
                500000.0,
                "Nacional",
                10,
                false
        );

        Factura factura = new Factura(cliente);
        factura.agregarDetalle(new Detalle(1, producto));

        Credito credito = new Credito(
                tarjeta,
                factura,
                cliente,
                500000.0
        );

        credito.generarCuotas();

        assertEquals(20, credito.getCuotas().size());
    }

    @Test
    void testCantidadCuotasNoSuperePermitido() {
        Cliente cliente = new Cliente(12345678L, "Juan", "Perez", "3881234567");

        TarjetaCredito tarjeta = new TarjetaCredito(
                4555111122223333L,
                java.time.LocalDate.of(2030, 12, 31),
                cliente,
                3000000.0
        );

        Producto producto = new Producto(
                1L,
                "Lavarropas",
                500000.0,
                "Nacional",
                10,
                false
        );

        Factura factura = new Factura(cliente);
        factura.agregarDetalle(new Detalle(1, producto));

        Credito credito = new Credito(
                tarjeta,
                factura,
                cliente,
                500000.0
        );

        credito.generarCuotas();

        assertTrue(
                credito.getCuotas().size() <= Credito.CUOTAS_MAXIMAS
        );
    }
}