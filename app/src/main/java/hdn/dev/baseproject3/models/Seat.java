

package hdn.dev.baseproject3.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Seat {

    @SerializedName("seatId")
    @Expose
    private Integer seatId;
    @SerializedName("flightCode")
    @Expose
    private String flightCode;
    @SerializedName("ticketId")
    @Expose
    private String ticketId;
    @SerializedName("seat_name")
    @Expose
    private String seatName;
    @SerializedName("available")
    @Expose
    private Boolean available;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("description")
    @Expose
    private String description;

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}