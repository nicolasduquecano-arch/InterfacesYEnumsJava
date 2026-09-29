package lab2_pagos;

class BilleteraDigital implements MetodoPago {
    private double saldo;

    BilleteraDigital(double saldo) { this.saldo = saldo; }

    @Override public String nombre() { return "Billetera digital"; }

    @Override
    public boolean pagar(double monto) {
        if (monto > saldo) return false;
        saldo -= monto;
        return true;
    }
}
