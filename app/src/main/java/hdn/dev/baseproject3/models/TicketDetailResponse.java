package hdn.dev.baseproject3.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketDetailResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private TicketDetail data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TicketDetail getData() {
        return data;
    }

    public void setData(TicketDetail data) {
        this.data = data;
    }
}
