package hdn.dev.baseproject3.models;

public class TicketDetail {
    private Ticket ticket;
    private User user;
    private Flight flight;

    public TicketDetail(Ticket ticket, User user, Flight flight) {
        this.ticket = ticket;
        this.user = user;
        this.flight = flight;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}
