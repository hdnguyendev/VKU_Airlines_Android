package hdn.dev.baseproject3.models;

import java.util.List;

public class FlightResponse {
    private String status;
    private String message;
    private List<Flight> data;

    public FlightResponse() {
    }

    public FlightResponse(String status, String message, List<Flight> data) {
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

    public List<Flight> getData() {
        return data;
    }

    public void setData(List<Flight> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Object Response {" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}