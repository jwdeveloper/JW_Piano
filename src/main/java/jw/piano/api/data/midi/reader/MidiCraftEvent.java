package jw.piano.api.data.midi.reader;

public record MidiCraftEvent(int noteId, int channelId, int velocity, int eventType) {
}
