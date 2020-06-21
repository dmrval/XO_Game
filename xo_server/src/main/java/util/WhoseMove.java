package util;

import java.util.Random;

public enum WhoseMove {
    CLIENT,
    SERVER;

    public static WhoseMove getRandomPlayer() {
        WhoseMove[] values = WhoseMove.values();
        int i = new Random().nextInt(values.length);
        return values[i];
    }
}
