package fr.univ.amu;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RsaMath {
    private static final Random rand = new Random();

    public static BigInteger modularPow(BigInteger n, BigInteger exposant, BigInteger mod) {
        BigInteger c  = new BigInteger("1");
        for (BigInteger i = new BigInteger("1"); i.compareTo( exposant.add(BigInteger.ONE)) < 0 ; i = i.add(BigInteger.ONE)) {
            c = c.multiply(n);
            c = c.mod(mod);
        }

        return c;
        //return n.modPow(exposant,mod);
    }

    public static BigInteger randNumber(int size) {
        BigInteger gen;
        gen = new BigInteger(size, rand);
        return gen;
    }

    public static BigInteger GCD(BigInteger number1, BigInteger number2) {
        if (number2.compareTo(BigInteger.ZERO) == 0 ) {
            return number1;
        }
        return GCD(number2, number1.mod( number2));
    }

    public static BigInteger generateRandomPrime(int size) {
        return BigInteger.probablePrime(size,rand);
    }
}
