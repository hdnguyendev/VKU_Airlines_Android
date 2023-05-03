package hdn.dev.baseproject3.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TicketRequest {

    @SerializedName("userId")
    @Expose
    private Long userId;
    @SerializedName("flightCode")
    @Expose
    private String flightCode;
    @SerializedName("seatIds")
    @Expose
    private List<Integer> seatIds;

    public TicketRequest(Long userId, String flightCode, List<Integer> seatIds) {
        this.userId = userId;
        this.flightCode = flightCode;
        this.seatIds = seatIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public List<Integer> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<Integer> seatIds) {
        this.seatIds = seatIds;
    }

    @Override
    public String toString() {
        return "TicketRequest{" +
                "userId=" + userId +
                ", flightCode='" + flightCode + '\'' +
                ", seatIds=" + seatIds +
                '}';
    }
}
