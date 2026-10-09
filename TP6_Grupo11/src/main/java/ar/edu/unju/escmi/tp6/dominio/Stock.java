package ar.edu.unju.escmi.tp6.dominio;

public class Stock {

	private int cantidad;
	private Producto producto;

	public Stock(int cantidad, Producto producto) {
		this.cantidad = cantidad;
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public boolean validarStockDisponible() {
		return cantidad != 0;
	}

	public void actualizarStock(int nuevo) {
		if (nuevo < 0) {
			throw new IllegalArgumentException("El stock no puede ser negativo.");
		}
		cantidad = nuevo;
	}

	public void descontarStock(int cantidadADescontar) {
		if (cantidadADescontar <= 0) {
			throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
		}
		if (cantidadADescontar > this.cantidad) {
			throw new IllegalArgumentException("Stock insuficiente.");
		}
		this.cantidad -= cantidadADescontar;
	}
}