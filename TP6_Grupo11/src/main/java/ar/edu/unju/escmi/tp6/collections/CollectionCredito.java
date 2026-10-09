package ar.edu.unju.escmi.tp6.collections;

import java.util.ArrayList;
import java.util.List;

import main.java.ar.edu.unju.escmi.tp6.dominio.Credito;

public class CollectionCredito {

	public static List<Credito> creditos = new ArrayList<Credito>();

	public static void agregarCredito(Credito credito) {
		if (credito == null) {
			System.out.println("NO SE PUEDE GUARDAR EL CREDITO: dato nulo");
			return;
		}
		creditos.add(credito);
	}

	public static void buscarCreditoPorDni(long dni) {
		System.out.println("BUSCANDO CRÉDITOS PARA DNI: " + dni);

		boolean encontrado = false;

		try {
			for (Credito credito : creditos) {
				if (credito.getCliente() != null && credito.getCliente().getDni() == dni) {
					System.out.println("\n--- Crédito Encontrado ---");
					credito.mostrarCredito();
					encontrado = true;
				}
			}

			if (!encontrado) {
				System.out.println("No se encontraron créditos asociados al DNI " + dni + ".");
			}
		} finally {
			System.out.println(" Fin de la buqueda");
		}
	}
}