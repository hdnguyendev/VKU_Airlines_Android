package hdn.dev.baseproject3.models;

public class Choices {
    String text;
    int index;

    public Choices(String text, int index) {
        this.text = text;
        this.index = index;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
