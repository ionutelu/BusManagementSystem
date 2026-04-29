package com.example.busstation.api.dto.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TicketRequestDto {

    @NotNull(message = "Bus trip ID is required")
    private Long busTripId;

    @NotNull(message = "Passenger ID is required")
    private Long passengerId;

    @NotBlank(message = "Seat number is required")
    private String seatNumber;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    public Long getBusTripId() { return busTripId; }
    public void setBusTripId(Long busTripId) { this.busTripId = busTripId; }

    public Long getPassengerId() { return passengerId; }
    public void setPassengerId(Long passengerId) { this.passengerId = passengerId; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}

