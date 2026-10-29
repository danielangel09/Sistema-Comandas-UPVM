package view;

import controller.GestorCafeteria;
import model.Pedido;
import model.Producto;

import java.util.Scanner;

/**
 * Interfaz de consola profesional del Sistema de Comandas.
 * Universidad Politécnica del Valle de México - Estructura de Datos
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final GestorCafeteria gestor = new GestorCafeteria();

    public static void main(String[] args) {
        mostrarBienvenida();
        boolean salir = false;

        while (!salir) {
            try {
                mostrarMenuPrincipal();
                int opcion = leerEntero("Seleccione una opción: ");

                switch (opcion) {
                    case 1 -> menuCatalogo();
                    case 2 -> menuPedidos();
                    case 3 -> menuCocina();
                    case 4 -> menuHistorial();
                    case 5 -> gestor.mostrarResumen();
                    case 0 -> {
                        System.out.println("\n¡Gracias por usar el Sistema de Comandas!");
                        System.out.println("Universidad Politécnica del Valle de México");
                        salir = true;
                    }
                    default -> System.out.println("✗ Opción no válida. Intente de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("✗ Error inesperado: " + e.getMessage());
                System.out.println("  El sistema continúa funcionando normalmente.");
            }
        }
        sc.close();
    }

    // ==================== MENÚS ====================

    private static void mostrarBienvenida() {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║     SISTEMA DE COMANDAS - CAFETERÍA UNIVERSITARIA    ║");
        System.out.println("║         Universidad Politécnica del Valle de México  ║");
        System.out.println("║              Estructura de Datos - Proyecto Final    ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.println("  Estructuras implementadas desde cero:");
        System.out.println("  • Cola (Queue)      → Flujo de pedidos FIFO");
        System.out.println("  • Lista Enlazada    → Catálogo de productos");
        System.out.println("  • Pila (Stack)      → Historial / Deshacer");
        System.out.println();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│         MENÚ PRINCIPAL              │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│  1. Gestión de Catálogo             │");
        System.out.println("│  2. Tomar / Registrar Pedido        │");
        System.out.println("│  3. Cocina / Procesar Pedidos       │");
        System.out.println("│  4. Historial y Deshacer            │");
        System.out.println("│  5. Resumen del Sistema             │");
        System.out.println("│  0. Salir                           │");
        System.out.println("└─────────────────────────────────────┘");
    }

    private static void menuCatalogo() {
        boolean regresar = false;
        while (!regresar) {
            System.out.println("\n--- Gestión de Catálogo ---");
            System.out.println("1. Ver catálogo completo");
            System.out.println("2. Agregar producto");
            System.out.println("3. Eliminar producto");
            System.out.println("4. Buscar producto por ID");
            System.out.println("0. Regresar al menú principal");

            int op = leerEntero("Opción: ");
            switch (op) {
                case 1 -> gestor.listarCatalogo();
                case 2 -> {
                    System.out.print("Nombre del producto: ");
                    String nombre = sc.nextLine().trim();
                    double precio = leerDouble("Precio: $");
                    System.out.print("Categoría (Bebida/Comida/Postre): ");
                    String cat = sc.nextLine().trim();
                    if (!nombre.isEmpty() && precio > 0) {
                        gestor.agregarProducto(nombre, precio, cat.isEmpty() ? "General" : cat);
                    } else {
                        System.out.println("✗ Datos inválidos.");
                    }
                }
                case 3 -> {
                    int id = leerEntero("ID del producto a eliminar: ");
                    gestor.eliminarProducto(id);
                }
                case 4 -> {
                    int id = leerEntero("ID del producto a buscar: ");
                    Producto p = gestor.buscarProductoPorId(id);
                    if (p != null) {
                        System.out.println("✓ Encontrado: " + p);
                    } else {
                        System.out.println("✗ Producto no encontrado.");
                    }
                }
                case 0 -> regresar = true;
                default -> System.out.println("✗ Opción inválida.");
            }
        }
    }

    private static void menuPedidos() {
        System.out.println("\n--- Registrar Nuevo Pedido ---");
        System.out.print("Nombre del cliente: ");
        String cliente = sc.nextLine().trim();

        Pedido pedido = gestor.crearPedido(cliente);
        if (pedido == null) return;

        boolean terminado = false;
        while (!terminado) {
            System.out.println("\nAgregando productos al pedido de " + cliente);
            System.out.println("1. Ver catálogo");
            System.out.println("2. Agregar producto al pedido");
            System.out.println("3. Ver pedido actual");
            System.out.println("4. Finalizar y enviar a cola");
            System.out.println("0. Cancelar pedido");

            int op = leerEntero("Opción: ");
            switch (op) {
                case 1 -> gestor.listarCatalogo();
                case 2 -> {
                    int id = leerEntero("ID del producto: ");
                    Producto p = gestor.buscarProductoPorId(id);
                    if (p == null) {
                        System.out.println("✗ Producto no encontrado.");
                    } else if (!p.isDisponible()) {
                        System.out.println("✗ Producto agotado.");
                    } else {
                        int cant = leerEntero("Cantidad: ");
                        if (cant > 0) {
                            pedido.agregarItem(p, cant);
                            System.out.println("✓ Agregado: " + cant + "x " + p.getNombre());
                        } else {
                            System.out.println("✗ Cantidad inválida.");
                        }
                    }
                }
                case 3 -> pedido.mostrarDetalle();
                case 4 -> {
                    if (pedido.getCantidadItems() > 0) {
                        gestor.finalizarPedido(pedido);
                        terminado = true;
                    } else {
                        System.out.println("✗ El pedido está vacío.");
                    }
                }
                case 0 -> {
                    System.out.println("Pedido cancelado.");
                    terminado = true;
                }
                default -> System.out.println("✗ Opción inválida.");
            }
        }
    }

    private static void menuCocina() {
        boolean regresar = false;
        while (!regresar) {
            System.out.println("\n--- Módulo de Cocina ---");
            System.out.println("1. Ver cola de pedidos");
            System.out.println("2. Ver siguiente pedido");
            System.out.println("3. Procesar (entregar) siguiente pedido");
            System.out.println("0. Regresar");

            int op = leerEntero("Opción: ");
            switch (op) {
                case 1 -> gestor.verColaPedidos();
                case 2 -> gestor.verSiguientePedido();
                case 3 -> gestor.procesarSiguientePedido();
                case 0 -> regresar = true;
                default -> System.out.println("✗ Opción inválida.");
            }
        }
    }

    private static void menuHistorial() {
        boolean regresar = false;
        while (!regresar) {
            System.out.println("\n--- Historial y Deshacer ---");
            System.out.println("1. Ver historial de acciones");
            System.out.println("2. Deshacer última acción");
            System.out.println("0. Regresar");

            int op = leerEntero("Opción: ");
            switch (op) {
                case 1 -> gestor.verHistorial();
                case 2 -> gestor.deshacerUltimaAccion();
                case 0 -> regresar = true;
                default -> System.out.println("✗ Opción inválida.");
            }
        }
    }

    // ==================== UTILIDADES DE ENTRADA ====================

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String linea = sc.nextLine().trim();
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("✗ Debe ingresar un número entero válido.");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String linea = sc.nextLine().trim();
                return Double.parseDouble(linea);
            } catch (NumberFormatException e) {
                System.out.println("✗ Debe ingresar un número válido (ej. 25.50).");
            }
        }
    }
}
