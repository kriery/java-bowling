package bowling.domian.state;

import bowling.domian.frame.Score;
import bowling.domian.state.exception.InvalidPinCountException;

public class Pins {
    private static int MIN_PIN_COUNT = 0;
    private static int MAX_PIN_COUNT = 10;

    private final int falledPinCount;

    private Pins(int falledPinCount) {
        this.falledPinCount = falledPinCount;
    }

    public static Pins bowl(int pinCount) {
        if (!isValidPinCount(pinCount)) {
            throw new InvalidPinCountException();
        }

        return new Pins(pinCount);
    }

    private static boolean isValidPinCount(int pinCount) {
        return pinCount >= MIN_PIN_COUNT && pinCount <= MAX_PIN_COUNT;
    }

    public Pins secondBowl(int pinCount) {
        if (!isValidSecondBowl(pinCount)) {
            throw new InvalidPinCountException();
        }

        return new Pins(pinCount);
    }

    private boolean isValidSecondBowl(int secondPinCount) {
        return isValidPinCount(secondPinCount) &&
                this.falledPinCount + secondPinCount <= MAX_PIN_COUNT;
    }

    public boolean isGutter() {
        return this.falledPinCount == MIN_PIN_COUNT;
    }

    public boolean isStrike() {
        return this.falledPinCount == MAX_PIN_COUNT;
    }

    public boolean isSpare(Pins secondPins) {
        return this.falledPinCount + secondPins.falledPinCount == MAX_PIN_COUNT;
    }

    public boolean isMiss(Pins secondPins) {
        return this.falledPinCount + secondPins.falledPinCount < MAX_PIN_COUNT;
    }

    public int totalPinsCount(Pins secondPins) {
        return this.falledPinCount + secondPins.falledPinCount;
    }

    public Score addScore(Score score) {
        return score.additionalBowl(this.falledPinCount);
    }

    public int getPinsCount() {
        return falledPinCount;
    }

    public String getDesc() {
        if (isStrike()) {
            return "X";
        }

        if (isGutter()) {
            return "-";
        }

        return String.valueOf(falledPinCount);
    }

    public String getDesc(Pins secondPins) {
        if (isSpare(secondPins)) {
            return getDesc() + "|/";
        }

        return getDesc() + "|" + secondPins.getDesc();
    }
}
