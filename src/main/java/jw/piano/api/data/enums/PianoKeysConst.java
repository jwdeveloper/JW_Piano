package jw.piano.api.data.enums;

public enum PianoKeysConst {
    WHITE_KEY_SELECTED(100),
    BLACK_KEY_SELECTED(101),
    WHITE_KEY(102),
    WHITE_KEY_PRESSED(103),
    BLACK_KEY(104),
    BLACK_KEY_PRESSED(105),
    PEDAL(106),
    PEDAL_DOWN(107),
    FLYING_NOTE(200),
    BENCH(400);
    private final int id;

    PianoKeysConst(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
