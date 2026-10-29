package structures;

/**
 * Pila (Stack) implementada con nodos enlazados.
 * Política LIFO (Last In, First Out).
 * Usada para historial de acciones / Deshacer (Undo).
 * Complejidad de apilar y desapilar: O(1).
 */
public class Pila<T> {
    private Nodo<T> cima;
    private int tamanio;

    public Pila() {
        this.cima = null;
        this.tamanio = 0;
    }

    /**
     * Apila un elemento. O(1)
     */
    public void apilar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.setSiguiente(cima);
        cima = nuevo;
        tamanio++;
    }

    /**
     * Desapila el elemento de la cima. O(1)
     * @return el dato o null si la pila está vacía
     */
    public T desapilar() {
        if (estaVacia()) {
            return null;
        }
        T dato = cima.getDato();
        cima = cima.getSiguiente();
        tamanio--;
        return dato;
    }

    /**
     * Consulta la cima sin eliminar. O(1)
     */
    public T verCima() {
        if (estaVacia()) {
            return null;
        }
        return cima.getDato();
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public int getTamanio() {
        return tamanio;
    }

    /**
     * Muestra el contenido de la pila (desde la cima).
     */
    public void mostrar() {
        if (estaVacia()) {
            System.out.println("   (Pila vacía - no hay acciones para deshacer)");
            return;
        }
        Nodo<T> actual = cima;
        int pos = 1;
        while (actual != null) {
            System.out.println("   " + pos + ". " + actual.getDato());
            actual = actual.getSiguiente();
            pos++;
        }
    }
}
