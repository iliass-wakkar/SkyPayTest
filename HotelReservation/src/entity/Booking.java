package entity;

import enums.RoomType;
import java.util.Date;

public class Booking {
    private int bookingId;
    private int userId;
    private int roomNumber;
    private Date checkIn;
    private Date checkOut;

    // Historical data (can't be derived from current Room/User)
    private RoomType bookedRoomType;
    private int pricePerNight;
    private int numberOfNights;
    private int totalCost;

    // Constructor
    public Booking(int bookingId, int userId, int roomNumber, Date checkIn, Date checkOut,
            RoomType bookedRoomType, int pricePerNight, int numberOfNights, int totalCost) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.bookedRoomType = bookedRoomType;
        this.pricePerNight = pricePerNight;
        this.numberOfNights = numberOfNights;
        this.totalCost = totalCost;
    }

    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public RoomType getBookedRoomType() {
        return bookedRoomType;
    }

    public void setBookedRoomType(RoomType bookedRoomType) {
        this.bookedRoomType = bookedRoomType;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(int pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", userId=" + userId +
                ", roomNumber=" + roomNumber +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", bookedRoomType=" + bookedRoomType +
                ", pricePerNight=" + pricePerNight +
                ", numberOfNights=" + numberOfNights +
                ", totalCost=" + totalCost +
                '}';
    }
}
