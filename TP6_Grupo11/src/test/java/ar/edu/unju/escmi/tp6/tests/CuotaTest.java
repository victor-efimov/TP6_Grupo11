package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;

class CuotaTest {

    private Cliente cliente;
    private TarjetaCredito tarjeta;
    private Credito credito;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("12345678", "Juan", "Perez", "San Salvador");
        tarjeta = new TarjetaCredito("4555111122223333", "Juan Perez", 3000000.0, cliente);
        
        credito = new Credito(tarjeta, 1500000.0, 10);
    }

    @Test
    void testListaCuotasNoSeaNull() {
        assertNotNull(credito.getCuotas(), "La lista de cuotas no debe ser null");
    }

    @Test
    void testCantidadDeCuotasGeneradas() {
        assertEquals(10, credito.getCuotas().size(), "El credito debe generar exactamente 10 cuotas");
    }

    @Test
    void testCantidadCuotasNoSuperePermitido() {
        assertTrue(credito.getCuotas().size() <= 30, "La cantidad de cuotas no debe superar el limite permitido");
    }
}