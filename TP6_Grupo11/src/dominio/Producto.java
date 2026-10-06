package dominio;

public class Producto {
    private long codigo;
    private String descripcion;
    private double precioUnitario;
    private String origenFabricacion;
    private int stock;
    private boolean esCelular;

    public Producto() {
    }

    public Producto(long codigo, String descripcion, double precioUnitario, String origenFabricacion, int stock, boolean esCelular) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.origenFabricacion = origenFabricacion;
        this.stock = stock;
        this.esCelular = esCelular;
    }

    public long getCodigo() { return codigo; }
    public void setCodigo(long codigo) { this.codigo = codigo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public String getOrigenFabricacion() { return origenFabricacion; }
    public void setOrigenFabricacion(String origenFabricacion) { this.origenFabricacion = origenFabricacion; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public boolean isEsCelular() { return esCelular; }
    public void setEsCelular(boolean esCelular) { this.esCelular = esCelular; }

    public void reducirStock(int cantidad) {
        if (cantidad > this.stock) {
            throw new IllegalArgumentException("Stock insuficiente.");
        }
        this.stock -= cantidad;
    }

    @Override
    public String toString() {
        return "Código: " + codigo + " | " + descripcion + " | Precio: $" + precioUnitario 
                + " | Origen: " + origenFabricacion + " | Stock: " + stock;
    }
}