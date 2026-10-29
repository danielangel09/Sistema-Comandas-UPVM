package model;

import structures.ListaEnlazada;

/**
 * Representa un pedido completo de un cliente.
 * Contiene una lista de items (productos + cantidad).
 */
public class Pedido {
    private static int contador = 1;

    private int numeroPedido;
    private String nombreCliente;
    private ListaEnlazada<ItemPedido> items;
    private String estado; // "EN_COLA", "EN_PREPARACION", "LISTO", "ENTREGADO", "CANCELADO"
    private double total;

    public Pedido(String nombreCliente) {
        this.numeroPedido = contador++;
        this.nombreCliente = nombreCliente;
        this.items = new ListaEnlazada<>();
        this.estado = "EN_COLA";
        this.total = 0.0;
    }

    public void agregarItem(Producto producto, int cantidad) {
        if (producto != null && cantidad > 0 && producto.isDisponible()) {
            items.insertar(new ItemPedido(producto, cantidad));
            total += producto.getPrecio() * cantidad;
        }
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public ListaEnlazada<ItemPedido> getItems() {
        return items;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public int getCantidadItems() {
        return items.getTamanio();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pedido otro = (Pedido) obj;
        return this.numeroPedido == otro.numeroPedido;
    }

    @Override
    public String toString() {
        return String.format("Pedido #%d | Cliente: %-15s | Items: %d | Total: $%.2f | Estado: %s",
                numeroPedido, nombreCliente, items.getTamanio(), total, estado);
    }

    /**
     * Muestra el detalle completo del pedido.
     */
    public void mostrarDetalle() {
        System.out.println("\n========== DETALLE DEL PEDIDO ==========");
        System.out.println("Número de pedido : #" + numeroPedido);
        System.out.println("Cliente          : " + nombreCliente);
        System.out.println("Estado           : " + estado);
        System.out.println("----------------------------------------");
        System.out.println("Productos:");
        items.listar();
        System.out.println("----------------------------------------");
        System.out.printf("TOTAL: $%.2f%n", total);
        System.out.println("========================================\n");
    }
}
