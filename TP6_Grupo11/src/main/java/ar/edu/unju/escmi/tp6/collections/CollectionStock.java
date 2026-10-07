package main.java.ar.edu.unju.escmi.tp6.collections;

import java.util.ArrayList;
import java.util.List;

import main.java.ar.edu.unju.escmi.tp6.dominio.Stock;

public class CollectionStock{
	
	public static List<Stock> stock = new ArrayList<Stock>();
	
	
	
	public static void agregarStock(Stock nuevo) {
	    if (nuevo == null) {
	        System.out.println("NO SE PUEDE GUARDAR EL STOCK: dato nulo");
	        return;
	    }
	    if (buscarStock(nuevo.getProducto().getCodigo()) != null) {
	        System.out.println("YA EXISTE UN CLIENTE CON ESE DNI");
	        return;
	    }
	    stock.add(nuevo);
	}
	public static Stock buscarStock(long buscado) {

	    for (Stock sto : stock) {
	        if (sto.getProducto().getCodigo() == buscado) {
	            return sto;
	        }
	    }
		return null;
	}

}
