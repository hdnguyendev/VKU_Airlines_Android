package hdn.dev.baseproject3.models;

public class Message {
    private String content;
    private int send_id; // 0 la cau hoi, 1 la cau tra loi

    public Message() {
    }

    public Message(String content, int send_id) {
        this.content = content;
        this.send_id = send_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSend_id() {
        return send_id;
    }

    public void setSend_id(int send_id) {
        this.send_id = send_id;
    }
}
