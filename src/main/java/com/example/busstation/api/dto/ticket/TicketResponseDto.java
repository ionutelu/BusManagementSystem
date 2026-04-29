package com.example.busstation.api.dto.ticket;

public class TicketResponseDto {

    private Long id;
    private Long busTripId;
    private String busTripSummary;
    private Long passengerId;
    private String passengerName;
    private String seatNumber;
    private double price;

    public TicketResponseDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getBusTripId() { return busTripId; }
    public void setBusTripId(Long busTripId) { this.busTripId = busTripId; }

    public String getBusTripSummary() { return busTripSummary; }
    public void setBusTripSummary(String busTripSummary) { this.busTripSummary = busTripSummary; }

    public Long getPassengerId() { return passengerId; }
    public void setPassengerId(Long passengerId) { this.passengerId = passengerId; }

    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}

