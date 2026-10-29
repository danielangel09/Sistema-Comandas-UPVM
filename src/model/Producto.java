package model;

/**
 * Entidad Producto del catálogo de la cafetería.
 */
public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private String categoria; // Bebida, Comida, Postre, etc.
    private boolean disponible;

    public Producto(int id, String nombre, double precio, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.disponible = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Producto otro = (Producto) obj;
        return this.id == otro.id;
    }

    @Override
    public String toString() {
        String estado = disponible ? "Disponible" : "Agotado";
        return String.format("ID: %-3d | %-20s | $%-7.2f | %-10s | %s",
                id, nombre, precio, categoria, estado);
    }
}
