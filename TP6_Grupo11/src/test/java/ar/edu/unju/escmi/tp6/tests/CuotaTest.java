package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Detalle;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;

class CuotaTest {

    private Credito credito;

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente(12345678L, "Juan", "Perez", "3881234567");

        TarjetaCredito tarjeta = new TarjetaCredito(
                4555111122223333L,
                LocalDate.of(2030, 12, 31),
                "Visa",
                cliente,
                3000000.0);

        Producto producto = new Producto(1L, "Lavarropas", 500000.0, "Nacional", 10, false);

        Factura factura = new Factura(cliente);
        factura.agregarDetalle(new Detalle(1, producto));

        credito = new Credito(tarjeta, factura, cliente, factura.calcularTotalAhora20());
    }

    @Test
    void testListaCuotasNoSeaNull() {
        credito.generarCuotas();
        assertNotNull(credito.getCuotas());
    }

    @Test
    void testListaCuotasSiempreTiene20Cuotas() {
        credito.generarCuotas();
        assertEquals(20, credito.getCuotas().size());
    }

    @Test
    void testCantidadCuotasNoSuperePermitido() {
        credito.generarCuotas();
        credito.generarCuotas(); // llamarlo dos veces no debe duplicar cuotas
        assertTrue(credito.getCuotas().size() <= Credito.CUOTAS_MAXIMAS);
    }
}