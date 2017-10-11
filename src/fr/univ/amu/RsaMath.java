package fr.univ.amu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RsaMath {
    private static final Random rand = new Random();

    public static int modularPow(int n,int exposant,int mod) {
        int c = 1;
        for (int i = 1; i < exposant + 1; ++i) {
            c = c * n;
            c %= mod;
        }

        return c;
    }
    public static int GCD(int number1, int number2) {
        if (number2 == 0) {
            return number1;
        }
        return GCD(number2, number1 % number2);
    }

    public static int randNumber(int min, int max) {
        return rand.nextInt(max) + min;
    }
    public static boolean isPrime(int nb) {
        if (nb <= 1)
            return false;
        if (nb <= 3)
            return true;
        if (nb % 2 == 0 || nb % 3 == 0)
            return false;
        int i = 5;
        while (i * i <= nb + 1) {
            if (nb % i == 0 || nb % (i + 2) == 0)
                return false;
            i = i + 6;
        }
        return true;
    }


    public static int generateRandomPrime(int size) {
        int gen = 4;
        while (!isPrime(gen))
            gen = randNumber(size,size+size);
        return gen;
    }
}
