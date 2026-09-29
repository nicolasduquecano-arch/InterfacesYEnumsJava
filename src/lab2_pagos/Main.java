package lab2_pagos;

public class Main {
    public static void main(String[] args) {
        Caja.cobrar(new TarjetaCredito(500_000), 120_000);
        Caja.cobrar(new BilleteraDigital(50_000), 80_000);
        Caja.cobrar(new Efectivo(), 35_000);
        Caja.cobrar(new TarjetaCredito(100_000), 99_000); // 99.000 + 2.970 > cupo
    }
}
