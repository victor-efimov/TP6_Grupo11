package dominio;

public class Detalle {
    private int cantidad;
    private double importe;
    private Producto producto;

    public Detalle() {
    }

    public Detalle(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
        this.importe = calcularImporte();
    }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getImporte() { return importe; }
    public void setImporte(double importe) { this.importe = importe; }

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