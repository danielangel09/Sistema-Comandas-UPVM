package controller;

import model.*;
import structures.*;

/**
 * Controlador principal del sistema de comandas.
 * Gestiona el catálogo (Lista), la cola de pedidos y el historial (Pila).
 */
public class GestorCafeteria {

    private ListaEnlazada<Producto> catalogo;      // Lista enlazada de productos
    private Cola<Pedido> colaPedidos;              // Cola FIFO de pedidos
    private Pila<Accion> historial;                // Pila para deshacer acciones
    private int siguienteIdProducto;

    public GestorCafeteria() {
        this.catalogo = new ListaEnlazada<>();
        this.colaPedidos = new Cola<>();
        this.historial = new Pila<>();
        this.siguienteIdProducto = 1;
        cargarProductosIniciales();
    }

    // ==================== CATÁLOGO DE PRODUCTOS ====================

    private void cargarProductosIniciales() {
        agregarProductoInterno("Café Americano", 25.00, "Bebida");
        agregarProductoInterno("Café Latte", 35.00, "Bebida");
        agregarProductoInterno("Té Verde", 22.00, "Bebida");
        agregarProductoInterno("Jugo de Naranja", 28.00, "Bebida");
        agregarProductoInterno("Torta de Jamón", 40.00, "Comida");
        agregarProductoInterno("Sándwich Club", 55.00, "Comida");
        agregarProductoInterno("Ensalada César", 48.00, "Comida");
        agregarProductoInterno("Pizza Personal", 65.00, "Comida");
        agregarProductoInterno("Brownie", 30.00, "Postre");
        agregarProductoInterno("Galletas (3 pzas)", 18.00, "Postre");
    }

    private void agregarProductoInterno(String nombre, double precio, String categoria) {
        Producto p = new Producto(siguienteIdProducto++, nombre, precio, categoria);
        catalogo.insertar(p);
    }

    public void agregarProducto(String nombre, double precio, String categoria) {
        Producto p = new Producto(siguienteIdProducto++, nombre, precio, categoria);
        catalogo.insertar(p);
        historial.apilar(new Accion(Accion.Tipo.AGREGAR_PRODUCTO, p,
                "Se agregó producto: " + p.getNombre() + " (ID " + p.getId() + ")"));
        System.out.println("✓ Producto agregado correctamente: " + p.getNombre());
    }

    public boolean eliminarProducto(int id) {
        Producto criterio = new Producto(id, "", 0, "");
        Producto encontrado = catalogo.buscar(criterio);
        if (encontrado != null) {
            catalogo.eliminar(criterio);
            historial.apilar(new Accion(Accion.Tipo.ELIMINAR_PRODUCTO, encontrado,
                    "Se eliminó producto: " + encontrado.getNombre() + " (ID " + id + ")"));
            System.out.println("✓ Producto eliminado: " + encontrado.getNombre());
            return true;
        }
        System.out.println("✗ No se encontró producto con ID " + id);
        return false;
    }

    public Producto buscarProductoPorId(int id) {
        Producto criterio = new Producto(id, "", 0, "");
        return catalogo.buscar(criterio);
    }

    public void listarCatalogo() {
        System.out.println("\n========== CATÁLOGO DE PRODUCTOS ==========");
        if (catalogo.estaVacia()) {
            System.out.println("   (No hay productos registrados)");
        } else {
            catalogo.listar();
        }
        System.out.println("Total de productos: " + catalogo.getTamanio());
        System.out.println("===========================================\n");
    }

    // ==================== GESTIÓN DE PEDIDOS ====================

    public Pedido crearPedido(String nombreCliente) {
        if (nombreCliente == null || nombreCliente.trim().isEmpty()) {
            System.out.println("✗ El nombre del cliente no puede estar vacío.");
            return null;
        }
        Pedido pedido = new Pedido(nombreCliente.trim());
        return pedido;
    }

    public void finalizarPedido(Pedido pedido) {
        if (pedido == null || pedido.getCantidadItems() == 0) {
            System.out.println("✗ El pedido está vacío. No se puede registrar.");
            return;
        }
        colaPedidos.encolar(pedido);
        historial.apilar(new Accion(Accion.Tipo.CREAR_PEDIDO, pedido,
                "Se creó pedido #" + pedido.getNumeroPedido() + " de " + pedido.getNombreCliente()));
        System.out.println("✓ Pedido #" + pedido.getNumeroPedido() + " agregado a la cola.");
        System.out.printf("  Cliente: %s | Total: $%.2f%n", pedido.getNombreCliente(), pedido.getTotal());
    }

    public void verColaPedidos() {
        System.out.println("\n========== COLA DE PEDIDOS (FIFO) ==========");
        System.out.println("Pedidos en espera: " + colaPedidos.getTamanio());
        colaPedidos.mostrar();
        System.out.println("============================================\n");
    }

    public void procesarSiguientePedido() {
        if (colaPedidos.estaVacia()) {
            System.out.println("✗ No hay pedidos en la cola.");
            return;
        }
        Pedido pedido = colaPedidos.desencolar();
        pedido.setEstado("LISTO");
        historial.apilar(new Accion(Accion.Tipo.PROCESAR_PEDIDO, pedido,
                "Se procesó (entregó) pedido #" + pedido.getNumeroPedido()));
        System.out.println("✓ Pedido procesado y listo para entregar:");
        pedido.mostrarDetalle();
    }

    public void verSiguientePedido() {
        Pedido siguiente = colaPedidos.verFrente();
        if (siguiente == null) {
            System.out.println("✗ No hay pedidos en la cola.");
            return;
        }
        System.out.println("\n--- Siguiente pedido a preparar ---");
        siguiente.mostrarDetalle();
    }

    // ==================== HISTORIAL / DESHACER ====================

    public void verHistorial() {
        System.out.println("\n========== HISTORIAL DE ACCIONES (PILA) ==========");
        System.out.println("Acciones registradas: " + historial.getTamanio());
        historial.mostrar();
        System.out.println("==================================================\n");
    }

    public void deshacerUltimaAccion() {
        if (historial.estaVacia()) {
            System.out.println("✗ No hay acciones para deshacer.");
            return;
        }

        Accion accion = historial.desapilar();
        System.out.println("Deshaciendo: " + accion.getDescripcion());

        switch (accion.getTipo()) {
            case AGREGAR_PRODUCTO:
                Producto pAgregado = (Producto) accion.getDato();
                catalogo.eliminar(pAgregado);
                System.out.println("✓ Se revirtió la adición del producto: " + pAgregado.getNombre());
                break;

            case ELIMINAR_PRODUCTO:
                Producto pEliminado = (Producto) accion.getDato();
                catalogo.insertar(pEliminado);
                System.out.println("✓ Se restauró el producto eliminado: " + pEliminado.getNombre());
                break;

            case CREAR_PEDIDO:
                // Para simplificar, solo informamos (desencolar un pedido específico es más complejo)
                System.out.println("⚠ El pedido ya está en la cola. Se recomienda cancelarlo manualmente si es necesario.");
                System.out.println("  (En una versión más avanzada se podría buscar y eliminar de la cola)");
                break;

            case PROCESAR_PEDIDO:
                Pedido pProcesado = (Pedido) accion.getDato();
                pProcesado.setEstado("EN_COLA");
                // Re-encolar al frente sería ideal, pero con cola simple lo ponemos al final
                colaPedidos.encolar(pProcesado);
                System.out.println("✓ El pedido #" + pProcesado.getNumeroPedido() + " regresó a la cola.");
                break;

            default:
                System.out.println("Acción no reversible automáticamente.");
        }
    }

    // ==================== ESTADÍSTICAS ====================

    public void mostrarResumen() {
        System.out.println("\n========== RESUMEN DEL SISTEMA ==========");
        System.out.println("Productos en catálogo : " + catalogo.getTamanio());
        System.out.println("Pedidos en cola       : " + colaPedidos.getTamanio());
        System.out.println("Acciones en historial : " + historial.getTamanio());
        System.out.println("=========================================\n");
    }
}
