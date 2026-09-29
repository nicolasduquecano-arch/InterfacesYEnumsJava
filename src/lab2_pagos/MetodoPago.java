package lab2_pagos;

interface MetodoPago {
    String nombre();
    boolean pagar(double monto);

    default double comision(double monto) { return 0; }
    default double totalACobrar(double monto) { return monto + comision(monto); }
}
