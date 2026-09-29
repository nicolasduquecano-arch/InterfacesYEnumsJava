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

## Desglose de los laboratorios

### Laboratorio 1 · Mi playlist ordenada
- `Cancion` implementa `Comparable<Cancion>`: el **orden natural** es por título (`titulo.compareTo(...)`).
- `duracion()` formatea los segundos con `String.format("%d:%02d", s / 60, s % 60)`.
- Orden secundario de la más larga a la más corta con `Comparator.comparingInt(Cancion::getDuracionSeg).reversed()`.
- `Predicate<Cancion> esLarga` (> 200 s) filtra y el `map` pasa los títulos a mayúsculas.
- La variable se declara como `List<Cancion>` (tipo de la interfaz), no como `ArrayList`.

### Laboratorio 2 · Pasarela de pagos
- `MetodoPago` define el contrato (`nombre()`, `pagar(double)`) y dos métodos `default`:
  `comision` (0 por defecto) y `totalACobrar` (monto + comisión).
- `TarjetaCredito` sobrescribe `comision` (3 %), rechaza si el total supera el cupo y, si aprueba, lo descuenta.
- `BilleteraDigital` valida contra su saldo; `Efectivo` siempre aprueba.
- `Caja.cobrar(MetodoPago m, double monto)` **no sabe** qué implementación recibe: bajo acoplamiento y despacho dinámico.
  Agregar un nuevo método de pago (p. ej. `Criptomoneda`) no exige modificar `Caja`.

### Laboratorio 3 · Semáforo inteligente
- `enum Semaforo { VERDE(30, "Avance"), AMARILLO(5, "Precaución"), ROJO(35, "Pare") }` con atributos `final`.
- `siguiente()` usa una **expresión `switch`** exhaustiva: sin `break` y sin `default`; si se agrega una luz nueva,
  el compilador obliga a actualizarlo.
- `static duracionCiclo()` suma los segundos recorriendo `values()`.

### Laboratorio 4 · Cafetería "El Algoritmo" (integrador)
- **Enums** para lo cerrado: `Bebida` (precio base), `Tamano` (recargo) y `EstadoPedido`.
- **Interfaz funcional** para lo que cambia: `Promocion` con fábricas `ninguna()`, `porcentaje(pct)` y `fijaDesde(min, valor)` que devuelven lambdas.
- **Record** para datos inmutables: `Item(Bebida, Tamano, int cantidad)` con `subtotal()`.
- **Clase** para lo que evoluciona: `Pedido` (cliente, `List<Item>`, `EstadoPedido`, `Promocion`);
  `agregar(...)` devuelve `this` para encadenar llamadas.
- `EstadoPedido` valida transiciones: `siguiente()` y `cancelar()` lanzan `IllegalStateException` en estados no válidos;
  el `main` la captura e imprime el aviso.

---

## Salida real obtenida

Compilado con `javac -Xlint:all -Werror` (sin advertencias) y ejecutado con Java 21.0.12, configuración regional `es-CO`.

**Lab 1 — `lab1_playlist.Main`**
```
Orden natural (título):
 Bailando       Enrique Iglesias  4:03
 Despacito      Luis Fonsi        3:48
 La Bicicleta   Carlos Vives      3:47
 Tusa           Karol G           3:20
De la más larga a la más corta:
 Bailando (4:03)
 Despacito (3:48)
 La Bicicleta (3:47)
 Tusa (3:20)
Canciones largas: [BAILANDO, DESPACITO, LA BICICLETA]
```

**Lab 2 — `lab2_pagos.Main`**
```
Tarjeta de crédito   $   120.000 comisión $  3.600 APROBADO
Billetera digital    $    80.000 comisión $      0 RECHAZADO
Efectivo             $    35.000 comisión $      0 APROBADO
Tarjeta de crédito   $    99.000 comisión $  2.970 RECHAZADO
```

**Lab 3 — `lab3_semaforo.Main`**
```
Paso 1: VERDE    30 s → Avance
Paso 2: AMARILLO  5 s → Precaución
Paso 3: ROJO     35 s → Pare
Paso 4: VERDE    30 s → Avance
Duración del ciclo completo: 70 s
```

**Lab 4 — `lab4_cafeteria.Main`**
```
=== Cafetería El Algoritmo ===
Cliente: Camila | Estado: LISTO
2 x CAPUCHINO  GRANDE   $  16.000
1 x TINTO      PEQUENO  $   2.500
1 x CHOCOLATE  MEDIANO  $   6.000
Subtotal                $  24.500
Descuento               $   2.450
TOTAL                   $  22.050
Aviso: No se puede cancelar en estado LISTO
```

> **Nota sobre la configuración regional:** los separadores de miles (`120.000`) dependen del *locale* del sistema,
> porque `%,` usa el formato local. La guía se ejecutó con `es-CO`. Las configuraciones de ejecución incluidas ya pasan
> `-Duser.language=es -Duser.country=CO`; en otro equipo con locale en inglés verías `120,000` si no se usan esas opciones.

---

## Cómo compilar y ejecutar

### Requisitos
- JDK 21 LTS (o posterior, p. ej. Java 25).
- IntelliJ IDEA (Community o Ultimate) — opcional si se usa la consola.

### Desde IntelliJ IDEA
1. `File > Open…` y selecciona la carpeta `InterfacesYEnumsJava`.
2. Si lo pide, asigna el SDK en `File > Project Structure > Project > SDK` → JDK 21.
3. En el selector de ejecución (arriba a la derecha) elige **Lab1 - lab1_playlist**, **Lab2 - lab2_pagos**,
   **Lab3 - lab3_semaforo** o **Lab4 - lab4_cafeteria** y pulsa ▶ Run.
   También puedes abrir cualquier `Main.java` y pulsar el ▶ verde junto a `main`.

### Desde consola (PowerShell o Git Bash, en la raíz del proyecto)
```bash
# Compilar todos los laboratorios
javac -Xlint:all -encoding UTF-8 -d out src/lab1_playlist/*.java src/lab2_pagos/*.java src/lab3_semaforo/*.java src/lab4_cafeteria/*.java

# Ejecutar cada laboratorio
java -Duser.language=es -Duser.country=CO -Dstdout.encoding=UTF-8 -cp out lab1_playlist.Main
java -Duser.language=es -Duser.country=CO -Dstdout.encoding=UTF-8 -cp out lab2_pagos.Main
java -Duser.language=es -Duser.country=CO -Dstdout.encoding=UTF-8 -cp out lab3_semaforo.Main
java -Duser.language=es -Duser.country=CO -Dstdout.encoding=UTF-8 -cp out lab4_cafeteria.Main
```

> En la consola clásica de Windows, si ves `?` en lugar de `é` o `→`, ejecuta antes `chcp 65001` para usar UTF-8.

---

## Tecnologías
- Java 21 LTS — interfaces con métodos `default`/`static`, lambdas, `record`, `enum`, `switch` de expresión.
- IntelliJ IDEA.
- Sin librerías externas.
