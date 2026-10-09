package ar.edu.unju.escmi.tp6.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Factura {
    private static int contador = 1000;
    private long nroFactura;
    private LocalDate fecha;
    private Cliente cliente;
    private List<Detalle> detalles = new ArrayList<>();

    public Factura() {
        this.nroFactura = ++contador;
        this.fecha = LocalDate.now();
    }

    public Factura(Cliente cliente) {
        this();
        this.cliente = cliente;
    }

    public long getNroFactura() { return nroFactura; }
    public LocalDate getFecha() { return fecha; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public List<Detalle> getDetalles() { return detalles; }

    public void agregarDetalle(Detalle detalle) {
        this.detalles.add(detalle);
    }

    public double calcularTotalAhora20() {
        double total = 0;
        for (Detalle d : detalles) {
            total += d.getImporte();
        }
        return total;
    }

    public boolean esFacturaAhora20() {
        if (detalles.isEmpty()) return false;
        for (Detalle d : detalles) {
            if (d.getProducto() == null || !"Nacional".equalsIgnoreCase(d.getProducto().getOrigenFabricacion())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FACTURA N°: ").append(nroFactura)
          .append(" | Fecha: ").append(fecha)
          .append("\nCliente: ").append(cliente != null ? cliente.getNombre() : "Sin cliente")
          .append("\nDetalles:\n");
        for (Detalle d : detalles) {
            sb.append("  - ").append(d).append("\n");
        }
        sb.append("TOTAL: $").append(calcularTotalAhora20());
        return sb.toString();
    }
}