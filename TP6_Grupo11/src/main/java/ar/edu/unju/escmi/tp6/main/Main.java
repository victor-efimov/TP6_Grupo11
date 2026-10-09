package ar.edu.unju.escmi.tp6.main;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;


        do {
            System.out.println("\n--- SISTEMA DE VENTAS 'AHORA 20' ---");
            System.out.println("1. Realizar una venta con programa 'Ahora 20'");
            System.out.println("2. Ver Compras realizadas por el cliente");
            System.out.println("3. Lista de los electrodomésticos disponibles");
            System.out.println("4. Consultar stock de los electrodomésticos");
            System.out.println("5. Revisar los créditos de un cliente");
            System.out.println("6. Salir");
            System.out.print("Elija una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                    case 1:
                        System.out.println("Iniciando venta...");
                        break;
                    case 2:
                        System.out.print("Ingrese el DNI del cliente: ");
                        long dniCompras = scanner.nextLong();
                        break;
                    case 3:
                        System.out.println("Electrodomésticos incluidos en el programa:");
                        break;
                    case 4:
                        System.out.println("Consultando stock...");
                        break;
                    case 5:
                        System.out.print("Ingrese el DNI del cliente: ");
                        long dniCreditos = scanner.nextLong();
                        break;
                    case 6:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción incorrecta. Elija un número del 1 al 6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un valor numérico válido.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }

        } while (opcion != 6);

        scanner.close();
    }
}