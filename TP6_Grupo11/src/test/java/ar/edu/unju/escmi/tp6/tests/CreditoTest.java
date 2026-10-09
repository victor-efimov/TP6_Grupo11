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

class CreditoTest {

    private Cliente cliente;
    private TarjetaCredito tarjeta;
    private Factura factura;
    private Credito credito;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(12345678L, "Juan", "Perez", "3881234567");

        tarjeta = new TarjetaCredito(
                4555111122223333L,
                LocalDate.of(2030, 12, 31),
                "Visa",
                cliente,
                3000000.0);

        Producto producto1 = new Producto(1L, "Lavarropas", 500000.0, "Nacional", 10, false);
        Producto producto2 = new Producto(2L, "Heladera", 800000.0, "Nacional", 10, false);

        factura = new Factura(cliente);
        factura.agregarDetalle(new Detalle(1, producto1));
        factura.agregarDetalle(new Detalle(1, producto2));

        credito = new Credito(tarjeta, factura, cliente, factura.calcularTotalAhora20());
    }

    @Test
    void testTotalCreditoNoSupereMontoMaximo() {
        credito.generarCuotas();
        assertTrue(credito.getMontoCredito() <= Credito.MONTO_MAXIMO);
    }

    @Test
    void testSumaDetallesIgualTotalFactura() {
        double suma = 0;
        for (Detalle d : factura.getDetalles()) {
            suma += d.getImporte();
        }
        assertEquals(factura.calcularTotalAhora20(), suma, 0.001);
    }

    @Test
    void testCompraNoSupereLimiteCreditoNiSaldoTarjeta() {
        double totalCompra = factura.calcularTotalAhora20();

        assertTrue(totalCompra <= Credito.MONTO_MAXIMO);
        assertTrue(totalCompra <= tarjeta.getSaldoDisponible());
    }
}