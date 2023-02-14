package br.com.project.utils;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Random;

public class GeneratedValuesRandom {

    private GeneratedValuesRandom(){}

    private  static Random random = new Random();

    public static <T> T getRandomElement(List<T> list) {
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }

    public static LocalDate randomBirthday() {
        return LocalDate.now().minus(Period.ofDays((random.nextInt(365 * 70))));
    }
}
