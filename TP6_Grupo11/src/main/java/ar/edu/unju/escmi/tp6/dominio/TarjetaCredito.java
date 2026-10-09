package main.java.ar.edu.unju.escmi.tp6.dominio;

import java.time.LocalDate;

public class TarjetaCredito {

    private long numero;
    private LocalDate fechaCaducacion;
    private String tipo;
    private Cliente cliente;
    private double limiteCompra;
    private double saldoDisponible;

    public TarjetaCredito() {
    }

    public TarjetaCredito(long numero, LocalDate fechaCaducacion, String tipo, Cliente cliente, double limiteCompra) {
        this.numero = numero;
        this.fechaCaducacion = fechaCaducacion;
        this.tipo = tipo;
        this.cliente = cliente;
        this.limiteCompra = limiteCompra;
        this.saldoDisponible = limiteCompra;
    }

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public LocalDate getFechaCaducacion() {
        return fechaCaducacion;
    }

    public void setFechaCaducacion(LocalDate fechaCaducacion) {
        this.fechaCaducacion = fechaCaducacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getLimiteCompra() {
        return limiteCompra;
    }

    public void setLimiteCompra(double limiteCompra) {
        this.limiteCompra = limiteCompra;
    }

    public double getSaldoDisponible() {
        return saldoDisponible;
    }

    public boolean tieneSaldoSuficiente(double monto) {
        return saldoDisponible >= monto;
    }

    public void descontarMonto(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        }
        if (!tieneSaldoSuficiente(monto)) {
            throw new IllegalStateException("Saldo insuficiente en la tarjeta.");
        }
        saldoDisponible -= monto;
    }

    @Override
    public String toString() {
        String titular = (cliente != null) ? cliente.getNombre() : "Sin titular";
        return "\nNumero: " + numero + "\nTipo: " + tipo + "\nFecha De Caducacion: " + fechaCaducacion 
                + "\nNombre Titular: " + titular + "\nLimite De Compra Actual: " + limiteCompra 
                + "\nSaldo Disponible: " + saldoDisponible;
    }
}