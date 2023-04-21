

package hdn.dev.baseproject3.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Seat {

    @SerializedName("seat_id")
    @Expose
    private Integer seatId;
    @SerializedName("flight")
    @Expose
    private Flight flight;
    @SerializedName("seat_name")
    @Expose
    private String seatName;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("is_available")
    @Expose
    private Boolean isAvailable;

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
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

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Seat.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("seatId");
        sb.append('=');
        sb.append(((this.seatId == null) ? "<null>" : this.seatId));
        sb.append(',');
        sb.append("flight");
        sb.append('=');
        sb.append(((this.flight == null) ? "<null>" : this.flight));
        sb.append(',');
        sb.append("seatName");
        sb.append('=');
        sb.append(((this.seatName == null) ? "<null>" : this.seatName));
        sb.append(',');
        sb.append("price");
        sb.append('=');
        sb.append(((this.price == null) ? "<null>" : this.price));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null) ? "<null>" : this.description));
        sb.append(',');
        sb.append("isAvailable");
        sb.append('=');
        sb.append(((this.isAvailable == null) ? "<null>" : this.isAvailable));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}