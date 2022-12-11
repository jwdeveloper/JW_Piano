package jw.piano.data.midi;

public record ChangeTempoEvent(long tick, int tempo, int signature) {
}
