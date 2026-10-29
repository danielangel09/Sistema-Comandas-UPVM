# Sistema de Comandas - Cafetería Universitaria

**Universidad Politécnica del Valle de México**  
**Asignatura:** Estructura de Datos  
**Proyecto Final**

---

## Descripción

Sistema informático funcional para la gestión de pedidos en tiempo real de la cafetería universitaria.  
Implementa de forma profesional y desde cero las estructuras de datos requeridas.

### Estructuras de Datos Implementadas (desde cero)

| Estructura       | Uso en el sistema                          | Complejidad principal |
|------------------|--------------------------------------------|-----------------------|
| **Cola (Queue)** | Flujo de pedidos (FIFO)                    | O(1) encolar/desencolar |
| **Lista Enlazada** | Catálogo de productos                    | O(n) búsqueda/eliminación |
| **Pila (Stack)** | Historial de acciones / Deshacer (Undo)    | O(1) apilar/desapilar |

> **Nota importante:** No se utiliza ninguna colección nativa de Java (`ArrayList`, `LinkedList`, `Queue`, `Stack`, etc.) para la lógica central del negocio. Todas las estructuras fueron programadas manualmente.

---

## Estructura del Proyecto

```
SistemaComandas/
├── src/
│   ├── structures/          # Estructuras de datos propias
│   │   ├── Nodo.java
│   │   ├── Cola.java
│   │   ├── ListaEnlazada.java
│   │   └── Pila.java
│   ├── model/               # Entidades de negocio
│   │   ├── Producto.java
│   │   ├── ItemPedido.java
│   │   ├── Pedido.java
│   │   └── Accion.java
│   ├── controller/          # Lógica de negocio
│   │   └── GestorCafeteria.java
│   └── view/                # Interfaz de consola
│       └── Main.java
├── docs/                    # Documentación adicional
└── README.md
```

---

## Cómo Compilar y Ejecutar

### Requisitos
- JDK 11 o superior

### Compilación (desde la raíz del proyecto)

```bash
javac -d bin src/structures/*.java src/model/*.java src/controller/*.java src/view/*.java
```

### Ejecución

```bash
java -cp bin view.Main
```

---

## Funcionalidades Principales

1. **Gestión de Catálogo**
   - Agregar, eliminar y buscar productos
   - Listado completo del menú

2. **Toma de Pedidos**
   - Crear pedidos por cliente
   - Agregar múltiples productos con cantidad
   - Enviar pedido a la cola de cocina

3. **Módulo de Cocina**
   - Visualizar cola de pedidos (FIFO)
   - Ver siguiente pedido a preparar
   - Procesar/entregar pedido

4. **Historial y Deshacer**
   - Registro de todas las acciones importantes
   - Función de deshacer (Undo) con Pila

---

## Roles Implementados (simulado en consola)

- **Cajero / Atendedor**: Toma pedidos y gestiona catálogo
- **Cocinero**: Procesa la cola de pedidos
- **Administrador**: Puede deshacer acciones y ver resumen

---

## Autor

Proyecto desarrollado de forma individual conforme a las instrucciones del Proyecto Final de Estructura de Datos.

---

## Notas Técnicas

- Manejo de excepciones en todas las entradas del usuario.
- El programa nunca se cierra abruptamente por datos incorrectos.
- Complejidad temporal respetada según lo solicitado en las instrucciones.
- Código modular, encapsulado y documentado.
