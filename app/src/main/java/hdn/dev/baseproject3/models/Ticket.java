package hdn.dev.baseproject3.models;

public class Ticket {
    private Long ticketId;
    private double totalAmount;
    private String time_booking;
    private String list_seat;
    private String momo;
    private Long userId;
    private String flightCode;

    public Ticket(Long ticketId, double totalAmount, String time_booking, String list_seat, String momo, Long userId, String flightCode) {
        this.ticketId = ticketId;
        this.totalAmount = totalAmount;
        this.time_booking = time_booking;
        this.list_seat = list_seat;
        this.momo = momo;
        this.userId = userId;
        this.flightCode = flightCode;
    }


    public Ticket() {

    }

    public String getList_seat() {
        return list_seat;
    }

    public void setList_seat(String list_seat) {
        this.list_seat = list_seat;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTime_booking() {
        return time_booking;
    }

    public void setTime_booking(String time_booking) {
        this.time_booking = time_booking;
    }

    public String getMomo() {
        return momo;
    }

    public void setMomo(String momo) {
        this.momo = momo;
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

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", totalAmount=" + totalAmount +
                ", time_booking='" + time_booking + '\'' +
                ", list_seat='" + list_seat + '\'' +
                ", momo='" + momo + '\'' +
                ", userId=" + userId +
                ", flightCode='" + flightCode + '\'' +
                '}';
    }
}