package dra.mobile.todotide;

public class Nar_Note {
    private String noteId;
    private String content;

    public Nar_Note() {
    }

    public Nar_Note(String noteId, String content) {
        this.noteId = noteId;
        this.content = content;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
