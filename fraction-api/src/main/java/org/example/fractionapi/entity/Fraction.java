package org.example.fractionapi.entity;

public class Fraction {
    private final int a;
    private final int b;

    public Fraction(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public boolean isRightFraction() {
        return a < b;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public Fraction cutFraction() {
        int div = nod(a, b);
        if (div == 1) {
            return this;
        } else {
            return new Fraction(a / div, b / div);
        }
    }

    public int nod(int a, int b) {
        return (b == 0) ? a : nod(b, a % b);
    }

    public int nok(int a, int b) {
        return a / nod(a, b) * b;
    }

    private Fraction siplyFraction(){
        int nod = nod(a, b);
        return new Fraction(a / nod, b / nod);
    }

    public Fraction addFraction(Fraction second) {
        if (b == second.getB()) {
            return new Fraction(a + second.getA(), b);
        }
        int nok = nok(b, second.getB());
        int newA = (a * (nok / b)) + (second.getA() * (nok / second.getB()));
        return new Fraction(newA, nok).siplyFraction();
    }

    public Fraction decFraction(Fraction second) {
        if (b == second.getB()) {
            return new Fraction(a - second.getA(), b);
        }
        int nok = nok(b, second.getB());
        int newA = (a * (nok / b)) - (second.getA() * (nok / second.getB()));
        return new Fraction(newA, nok).siplyFraction();

    }

    public Fraction multiplyFraction(Fraction second) {
        return new Fraction(a * second.getA(), b * second.getB()).siplyFraction();
    }

    public Fraction divFraction(Fraction second) {
        return multiplyFraction(new Fraction(second.getB(), second.getA()));
    }
}
