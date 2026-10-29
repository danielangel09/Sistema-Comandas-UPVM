package structures;

/**
 * Lista doblemente enlazada genérica implementada desde cero.
 * Permite inserciones, búsquedas y eliminaciones dinámicas.
 * Complejidad de búsqueda y eliminación por ID: O(n).
 */
public class ListaEnlazada<T> {
    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int tamanio;

    public ListaEnlazada() {
        this.cabeza = null;
        this.cola = null;
        this.tamanio = 0;
    }

    /**
     * Inserta un elemento al final. O(1)
     */
    public void insertar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.setSiguiente(nuevo);
            nuevo.setAnterior(cola);
            cola = nuevo;
        }
        tamanio++;
    }

    /**
     * Inserta al inicio. O(1)
     */
    public void insertarInicio(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            nuevo.setSiguiente(cabeza);
            cabeza.setAnterior(nuevo);
            cabeza = nuevo;
        }
        tamanio++;
    }

    /**
     * Busca un elemento por igualdad (usa equals del objeto).
     * Complejidad O(n)
     */
    public T buscar(T criterio) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (actual.getDato().equals(criterio)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    /**
     * Elimina el primer elemento que coincida con el criterio.
     * Complejidad O(n)
     * @return true si se eliminó, false si no se encontró
     */
    public boolean eliminar(T criterio) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (actual.getDato().equals(criterio)) {
                // Caso: es el único nodo
                if (actual == cabeza && actual == cola) {
                    cabeza = null;
                    cola = null;
                }
                // Caso: es la cabeza
                else if (actual == cabeza) {
                    cabeza = actual.getSiguiente();
                    cabeza.setAnterior(null);
                }
                // Caso: es la cola
                else if (actual == cola) {
                    cola = actual.getAnterior();
                    cola.setSiguiente(null);
                }
                // Caso: está en medio
                else {
                    actual.getAnterior().setSiguiente(actual.getSiguiente());
                    actual.getSiguiente().setAnterior(actual.getAnterior());
                }
                tamanio--;
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    /**
     * Lista todos los elementos sin alterar la estructura.
     */
    public void listar() {
        if (cabeza == null) {
            System.out.println("   (Lista vacía)");
            return;
        }
        Nodo<T> actual = cabeza;
        int pos = 1;
        while (actual != null) {
            System.out.println("   " + pos + ". " + actual.getDato());
            actual = actual.getSiguiente();
            pos++;
        }
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public int getTamanio() {
        return tamanio;
    }

    /**
     * Obtiene el nodo cabeza (útil para recorridos externos).
     */
    public Nodo<T> getCabeza() {
        return cabeza;
    }
}
