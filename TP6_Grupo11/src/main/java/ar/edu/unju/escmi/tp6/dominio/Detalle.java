package ar.edu.unju.escmi.tp6.dominio;

public class Detalle {
    private int cantidad;
    private double importe;
    private boolean esElectrodomestico;
    private Producto producto;

    public Detalle() {
    }

    public Detalle(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
        if (producto != null) {
            this.esElectrodomestico = !producto.isEsCelular();
        }
        this.importe = calcularImporte();
    }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getImporte() { return importe; }
    public void setImporte(double importe) { this.importe = importe; }

    public boolean isEsElectrodomestico() { return esElectrodomestico; }
    public void setEsElectrodomestico(boolean esElectrodomestico) { this.esElectrodomestico = esElectrodomestico; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public double calcularImporte() {
        if (producto != null) {
            return producto.getPrecioUnitario() * cantidad;
        }
        return 0.0;
    }

    @Override
    public String toString() {
        return "Producto: " + (producto != null ? producto.getDescripcion() : "") 
                + " x" + cantidad + " | Importe: $" + importe;
    }
}