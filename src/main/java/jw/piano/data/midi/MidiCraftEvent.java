package jw.piano.data.midi;

public record MidiCraftEvent(int noteId, int channelId, int velocity, int eventType) {
}
