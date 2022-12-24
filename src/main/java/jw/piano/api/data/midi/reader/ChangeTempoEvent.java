package jw.piano.api.data.midi.reader;

public record ChangeTempoEvent(long tick, int tempo, int signature) {
}
