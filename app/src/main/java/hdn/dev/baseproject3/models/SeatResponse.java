package hdn.dev.baseproject3.models;

import java.util.List;

public class SeatResponse {
    private String status;
    private String message;
    private List<Seat> data;

    public SeatResponse() {
    }

    public SeatResponse(String status, String message, List<Seat> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

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

    public List<Seat> getData() {
        return data;
    }

    public void setData(List<Seat> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SeatResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
