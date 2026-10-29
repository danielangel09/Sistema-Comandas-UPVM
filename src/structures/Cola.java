package structures;

/**
 * Cola (Queue) implementada con nodos enlazados.
 * Política FIFO (First In, First Out).
 * Complejidad de encolar y desencolar: O(1).
 * PROHIBIDO usar java.util.Queue o similares.
 */
public class Cola<T> {
    private Nodo<T> frente;
    private Nodo<T> fin;
    private int tamanio;

    public Cola() {
        this.frente = null;
        this.fin = null;
        this.tamanio = 0;
    }

    /**
     * Encola un elemento al final. O(1)
     */
    public void encolar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (estaVacia()) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
        tamanio++;
    }

    /**
     * Desencola el elemento del frente. O(1)
     * @return el dato o null si la cola está vacía
     */
    public T desencolar() {
        if (estaVacia()) {
            return null;
        }
        T dato = frente.getDato();
        frente = frente.getSiguiente();
        if (frente == null) {
            fin = null; // La cola quedó vacía
        }
        tamanio--;
        return dato;
    }

    /**
     * Consulta el frente sin eliminar. O(1)
     */
    public T verFrente() {
        if (estaVacia()) {
            return null;
        }
        return frente.getDato();
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int getTamanio() {
        return tamanio;
    }

    /**
     * Muestra todos los elementos de la cola sin alterar la estructura.
     */
    public void mostrar() {
        if (estaVacia()) {
            System.out.println("   (Cola vacía)");
            return;
        }
        Nodo<T> actual = frente;
        int pos = 1;
        while (actual != null) {
            System.out.println("   " + pos + ". " + actual.getDato());
            actual = actual.getSiguiente();
            pos++;
        }
    }
}
