package ar.edu.unju.escmi.tp6.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Credito {

	public static final int CUOTAS_MAXIMAS = 20;
	public static final double MONTO_MAXIMO = 2500000.0;

	private TarjetaCredito tarjetaCredito;
	private Factura factura;
	private List<Cuota> cuotas = new ArrayList<Cuota>();
	private Cliente cliente;
	private double montoCredito;

	public Credito() {
	}

	public Credito(TarjetaCredito tarjetaCredito, Factura factura, Cliente cliente, double montoCredito) {
		this.tarjetaCredito = tarjetaCredito;
		this.factura = factura;
		this.cliente = cliente;
		this.montoCredito = montoCredito;
	}

	public Credito(List<Cuota> cuotas) {
		if (cuotas != null) {
			this.cuotas = new ArrayList<Cuota>(cuotas);
		}
	}

	public TarjetaCredito getTarjetaCredito() {
		return tarjetaCredito;
	}

	public void setTarjetaCredito(TarjetaCredito tarjetaCredito) {
		this.tarjetaCredito = tarjetaCredito;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public List<Cuota> getCuotas() {
		return cuotas;
	}

	public void setCuotas(List<Cuota> cuotas) {
		this.cuotas = cuotas;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public double getMontoCredito() {
		return montoCredito;
	}

	public void setMontoCredito(double montoCredito) {
		this.montoCredito = montoCredito;
	}

	public void generarCuotas() {
		if (factura == null) {
			throw new IllegalStateException("El crédito no tiene una factura asociada.");
		}
		double total = factura.calcularTotalAhora20();
		if (total > MONTO_MAXIMO) {
			throw new IllegalStateException("El monto supera el máximo permitido de $" + MONTO_MAXIMO);
		}
		if (tarjetaCredito != null && !tarjetaCredito.tieneSaldoSuficiente(total)) {
			throw new IllegalStateException("La tarjeta no tiene saldo suficiente para esta compra.");
		}

		montoCredito = total;
		cuotas.clear();

		double montoCuota = total / CUOTAS_MAXIMAS;
		LocalDate hoy = LocalDate.now();

		for (int i = 1; i <= CUOTAS_MAXIMAS; i++) {
			Cuota cuota = new Cuota();
			cuota.setNroCuota(i);
			cuota.setMonto(montoCuota);
			cuota.setFechaGeneracion(hoy);
			cuota.setFechaVencimiento(hoy.plusMonths(i));
			cuotas.add(cuota);
		}
	}

	public void mostrarCredito() {
		System.out.println("***Tarjeta De Credito***" + tarjetaCredito + "\n" + factura + "\n\nCUOTAS:");
		for (Cuota cuota : cuotas) {
			System.out.println(cuota);
		}
	}
}
