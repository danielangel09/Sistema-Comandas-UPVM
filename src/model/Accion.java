package model;

/**
 * Representa una acción realizada en el sistema (para el historial / Undo).
 * Se almacena en la Pila.
 */
public class Accion {
    public enum Tipo {
        AGREGAR_PRODUCTO,
        ELIMINAR_PRODUCTO,
        CREAR_PEDIDO,
        CANCELAR_PEDIDO,
        PROCESAR_PEDIDO
    }

    private Tipo tipo;
    private Object dato; // Puede ser Producto o Pedido
    private String descripcion;

    public Accion(Tipo tipo, Object dato, String descripcion) {
        this.tipo = tipo;
        this.dato = dato;
        this.descripcion = descripcion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Object getDato() {
        return dato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
