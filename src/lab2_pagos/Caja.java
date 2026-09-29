package lab2_pagos;

class Caja {
    static void cobrar(MetodoPago m, double monto) { // no sabe qué método es
        String resultado = m.pagar(monto) ? "APROBADO" : "RECHAZADO";
        System.out.printf("%-20s $%,10.0f comisión $%,7.0f %s%n",
                m.nombre(), monto, m.comision(monto), resultado);
    }
}
