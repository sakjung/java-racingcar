package racingcar.utils;

import java.util.Random;

public class RandomNumberGenerator implements NumberGenerator{
    private static final int RANDOM_NUMBER_UPPER_BOUND = 10;

    private final Random random = new Random();

    @Override
    public int generateNumber() {
        return random.nextInt(RANDOM_NUMBER_UPPER_BOUND);
    }
}
