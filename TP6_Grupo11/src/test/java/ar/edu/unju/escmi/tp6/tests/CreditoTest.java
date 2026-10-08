package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.ar.edu.unju.escmi.tp6.dominio.Cliente;
import main.java.ar.edu.unju.escmi.tp6.dominio.Credito;
import main.java.ar.edu.unju.escmi.tp6.dominio.Detalle;
import main.java.ar.edu.unju.escmi.tp6.dominio.Factura;
import main.java.ar.edu.unju.escmi.tp6.dominio.Producto;
import main.java.ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;

class CreditoTest {

    private Cliente cliente;
    private TarjetaCredito tarjeta;
    private Producto producto1;
    private Producto producto2;
    private Factura factura;
    private Credito credito;

    @BeforeEach
    void setUp() {

        cliente = new Cliente(
                12345678L,
                "Juan",
                "Perez",
                "3881234567"
        );

        tarjeta = new TarjetaCredito(
                4555111122223333L,
                java.time.LocalDate.of(2030, 12, 31),
                cliente,
                3000000.0
        );

        producto1 = new Producto(
                1L,
                "Lavarropas",
                500000.0,
                "Nacional",
                10,
                false
        );

        producto2 = new Producto(
                2L,
                "Heladera",
                800000.0,
                "Nacional",
                10,
                false
        );

        Detalle detalle1 = new Detalle(1, producto1);
        Detalle detalle2 = new Detalle(1, producto2);

        factura = new Factura(cliente);
        factura.agregarDetalle(detalle1);
        factura.agregarDetalle(detalle2);

        credito = new Credito(
                tarjeta,
                factura,
                cliente,
                factura.calcularTotalAhora20()
        );
    }

    @Test
    void testTotalCreditoNoSupereMontoMaximo() {

        credito.generarCuotas();

        assertTrue(
                credito.getMontoCredito() <= Credito.MONTO_MAXIMO
        );
    }

    @Test
    void testSumaDetallesIgualTotalFactura() {

        double sumaDetalles = 0;

        for (Detalle detalle : factura.getDetalles()) {
            sumaDetalles += detalle.getImporte();
        }

        assertEquals(
                factura.calcularTotalAhora20(),
                sumaDetalles
        );
    }

    @Test
    void testCompraNoSupereLimiteCreditoNiSaldoTarjeta() {

        double totalCompra = factura.calcularTotalAhora20();

        assertTrue(
                totalCompra <= Credito.MONTO_MAXIMO
        );

        assertTrue(
                totalCompra <= tarjeta.getSaldoDisponible()
        );
    }
}