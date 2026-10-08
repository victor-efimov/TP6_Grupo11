package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private List<Detalle> detalles;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("12345678", "Carlos", "Gomes", "San Salvador");
        tarjeta = new TarjetaCredito("4555111122223333", "Carlos Gomez", 3000000.0, cliente);
        
        Producto prod1 = new Producto(1, "Lavarropas", 500000.0);
        Producto prod2 = new Producto(2, "Heladera", 800000.0);

        Detalle detalle1 = new Detalle(prod1, 1);
        Detalle detalle2 = new Detalle(prod2, 1);

        detalles = new ArrayList<>();
        detalles.add(detalle1);
        detalles.add(detalle2);

        factura = new Factura(1001, cliente);
        factura.agregarDetalle(detalle1);
        factura.agregarDetalle(detalle2);

        credito = new Credito(tarjeta, 1300000.0, 10);
    }

    @Test
    void testMontoTotalCreditoNoSupereLimiteGeneral() {
        double montoCredito = credito.getMontoTotal();
        assertTrue(montoCredito <= 2500000.0, "El monto total del crédito no debe superar el límite de $2.500.000");
    }

    @Test
    void testSumaImporteDetallesIgualTotalFactura() {
        double sumaDetalles = 0;
        for (Detalle d : detalles) {
            sumaDetalles += d.getSubtotal();
        }
        assertEquals(factura.getTotal(), sumaDetalles, "La suma de los detalles debe coincidir con el total de la factura");
    }

    @Test
    void testMontoCompraNoSupereLimiteYDisponibleTarjeta() {
        double montoCompra = credito.getMontoTotal();
        double limiteMaximo = 2500000.0;
        double disponibleTarjeta = tarjeta.getLimite();

        assertTrue(montoCompra <= limiteMaximo && montoCompra <= disponibleTarjeta, 
            "El monto total de la compra no debe superar el límite general ni el disponible de la tarjeta");
    }
}