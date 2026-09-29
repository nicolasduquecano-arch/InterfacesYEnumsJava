# Interfaces y Enums en Java — Laboratorios

Solución de los **4 laboratorios** de la guía *"Interfaces y Enums en Java"* (Java 21 LTS, compatible con Java 25).
Proyecto de IntelliJ IDEA sin dependencias externas: solo el JDK estándar (`java.util`, `java.util.function`).

| Lab | Paquete | Nivel | Conceptos de la guía |
|-----|---------|-------|----------------------|
| 1 · Mi playlist ordenada | `lab1_playlist` | Básico | `Comparable`, `Comparator`, `Predicate` (1.4 · 1.6 · 1.7) |
| 2 · Pasarela de pagos | `lab2_pagos` | Guiado | Interfaz, métodos `default`, polimorfismo (1.2 · 1.4 · 1.5) |
| 3 · Semáforo inteligente | `lab3_semaforo` | Básico | Enum con atributos, `switch` de expresión, `values()` (2.2 · 2.5 · 2.6 · 2.9) |
| 4 · Cafetería "El Algoritmo" | `lab4_cafeteria` | Integrador | Enums, interfaz funcional, record, máquina de estados (1.5 · 1.6 · 1.9 · 2.6 · 2.9) |

---

## Arquitectura del proyecto

```
InterfacesYEnumsJava/
├── .idea/                          # Configuración de IntelliJ (JDK 21, UTF-8, run configs)
│   └── runConfigurations/          # Lab1 … Lab4 listos para ejecutar
├── InterfacesYEnumsJava.iml
├── README.md
└── src/
    ├── lab1_playlist/
    │   ├── Cancion.java            # Comparable<Cancion> por título, duracion() "m:ss"
    │   └── Main.java
    ├── lab2_pagos/
    │   ├── MetodoPago.java         # Contrato + default comision / totalACobrar
    │   ├── TarjetaCredito.java     # Cupo, 3 % de comisión
    │   ├── BilleteraDigital.java   # Saldo, sin comisión
    │   ├── Efectivo.java           # Siempre aprueba
    │   ├── Caja.java               # static cobrar(MetodoPago, double) polimórfico
    │   └── Main.java
    ├── lab3_semaforo/
    │   ├── Semaforo.java           # enum: segundos, accion, siguiente(), duracionCiclo()
    │   └── Main.java
    └── lab4_cafeteria/
        ├── Bebida.java             # enum con precio base
        ├── Tamano.java             # enum con recargo
        ├── Promocion.java          # @FunctionalInterface + fábricas static
        ├── Item.java               # record (Bebida, Tamano, cantidad) + subtotal()
        ├── EstadoPedido.java       # enum máquina de estados (sección 2.9)
        ├── Pedido.java             # clase con estado y métodos encadenables
        └── Main.java
```

Cada laboratorio vive en su propio paquete con su propio `Main`, de modo que los tipos de un laboratorio
no se mezclan con los de otro (por ejemplo, `lab2_pagos.Main` y `lab4_cafeteria.Main` coexisten sin conflicto).

---



---

## Tecnologías
- Java 21 LTS — interfaces con métodos `default`/`static`, lambdas, `record`, `enum`, `switch` de expresión.
- IntelliJ IDEA.
- Sin librerías externas.
