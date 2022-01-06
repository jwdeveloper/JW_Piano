package jw.piano.autoplayer;

public class ChordFactory {
    public static Chord minor(Sound note) {
        return new Chord(note.name() + " minor",
                new Note(note, 3),
                new Note(Sound.getById(note.getId() + 5), 3),
                new Note(Sound.getById(note.getId() + 7), 3),
                new Note(note, 4),
                new Note(Sound.getById(note.getId() + 7), 3),
                new Note(Sound.getById(note.getId() + 5), 3));

    }

    public static Chord majnor(Sound note) {
        return new Chord(note.name() + " major",
                new Note(note, 3),
                new Note(Sound.getById(note.getId() + 4), 3),
                new Note(Sound.getById(note.getId() + 7), 3),
                new Note(note, 4),
                new Note(Sound.getById(note.getId() + 7), 3),
                new Note(Sound.getById(note.getId() + 4), 3));
    }

}
