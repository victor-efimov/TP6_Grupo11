package main.java.ar.edu.unju.escmi.tp6.dominio;


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
		return cantidad!=0;
	}
	public void actualizarStock(int nuevo) {
		//se debe verificar que el numero sea mayor que -1 antes
		cantidad=nuevo;
	}
}