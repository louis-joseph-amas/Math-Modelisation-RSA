package fr.univ.amu;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RsaMath {
    private static final Random rand = new Random();

    public static BigInteger modularPow(BigInteger n, BigInteger exposant, BigInteger mod) {
        /*BigInteger c  = new BigInteger("1");
        for (BigInteger i = new BigInteger("1"); i.compareTo( exposant.add(BigInteger.ONE)) < 0 ; i = i.add(BigInteger.ONE)) {
            c = c.multiply(n);
            c = c.mod(mod);
        }

        return c;*/
        return n.modPow(exposant,mod);
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
    private static boolean isIntegerValue(BigDecimal bd) {
        return bd.signum() == 0 || bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
    }
    static RsaPrivateKey crack(RsaPublicKey rsaPublicKey) {
        BigDecimal N = new BigDecimal(rsaPublicKey.getN());
        BigDecimal i = new BigDecimal("2");
        while (true) {
            BigDecimal division = N.divide(i, 2,BigDecimal.ROUND_HALF_UP);
            if (isIntegerValue(division)) {
                RsaKeyGenerator rsaKeyGenerator = new RsaKeyGenerator(i.toBigInteger(),division.toBigInteger(),rsaPublicKey.getE());
                return rsaKeyGenerator.getPrivateKey();
            }
            i = i.add(BigDecimal.ONE);
        }
    }

    public static BigInteger generateRandomPrime(int size) {
        return BigInteger.probablePrime(size,rand);
    }
}
