package ru.bigint.webapp.utils;

import java.util.Random;

public class Util {
    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
