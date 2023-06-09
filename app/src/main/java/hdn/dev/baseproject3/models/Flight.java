package hdn.dev.baseproject3.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Flight {
    @SerializedName("flightCode")
    @Expose
    private String flightCode;
    @SerializedName("flightName")
    @Expose
    private String flightName;
    @SerializedName("departure")
    @Expose
    private String departure;
    @SerializedName("departureSort")
    @Expose
    private String departureSort;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("destinationSort")
    @Expose
    private String destinationSort;
    @SerializedName("departureTime")
    @Expose
    private String departureTime;
    @SerializedName("arrivalTime")
    @Expose
    private String arrivalTime;
    @SerializedName("remaining_seat")
    @Expose
    private Integer remainingSeat;

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDepartureSort() {
        return departureSort;
    }

    public void setDepartureSort(String departureSort) {
        this.departureSort = departureSort;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestinationSort() {
        return destinationSort;
    }

    public void setDestinationSort(String destinationSort) {
        this.destinationSort = destinationSort;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getRemainingSeat() {
        return remainingSeat;
    }

    public void setRemainingSeat(Integer remainingSeat) {
        this.remainingSeat = remainingSeat;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightCode='" + flightCode + '\'' +
                ", flightName='" + flightName + '\'' +
                ", departure='" + departure + '\'' +
                ", departureSort='" + departureSort + '\'' +
                ", destination='" + destination + '\'' +
                ", destinationSort='" + destinationSort + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", remainingSeat=" + remainingSeat +
                '}';
    }
}