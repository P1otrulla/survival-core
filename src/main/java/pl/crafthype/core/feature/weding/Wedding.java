package pl.crafthype.core.feature.weding;

import java.util.UUID;

public class Wedding {

    private static final Wedding EMPTY = new Wedding(null, null);

    private final UUID playerOne;
    private final UUID playerTwo;

    private Wedding(UUID playerOne, UUID playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public static Wedding empty() {
        return EMPTY;
    }

    public static Wedding create(UUID playerOne, UUID playerTwo) {
        return new Wedding(playerOne, playerTwo);
    }

    public UUID playerOne() {
        return this.playerOne;
    }

    public UUID playerTwo() {
        return this.playerTwo;
    }

    public boolean isEmpty() {
        return this.playerOne == null && this.playerTwo == null;
    }
}
