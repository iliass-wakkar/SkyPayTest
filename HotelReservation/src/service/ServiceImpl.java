package service;

import entity.Room;
import entity.User;
import entity.Booking;
import enums.RoomType;
import exception.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class ServiceImpl implements Service {
    private ArrayList<Room> rooms;
    private ArrayList<User> users;
    private ArrayList<Booking> bookings;
    private int nextBookingId = 1;

    // Constructor
    public ServiceImpl() {
        this.rooms = new ArrayList<>();
        this.users = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    @Override
    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight) {
        Room existingRoom = findRoomByNumber(roomNumber);
        if (existingRoom != null) {
            // Update existing room (does not affect previous bookings)
            existingRoom.setRoomType(roomType);
            existingRoom.setPricePerNight(roomPricePerNight);
            System.out.println("Room " + roomNumber + " updated successfully.");
        } else {
            // Create new room
            Room newRoom = new Room(roomNumber, roomType, roomPricePerNight);
            rooms.add(newRoom);
            System.out.println("Room " + roomNumber + " created successfully.");
        }
    }

    @Override
    public void setUser(int userId, int balance) {
        User existingUser = findUserById(userId);
        if (existingUser != null) {
            // Update existing user
            existingUser.setBalance(balance);
            System.out.println("User " + userId + " updated successfully.");
        } else {
            // Create new user
            User newUser = new User(userId, balance);
            users.add(newUser);
            System.out.println("User " + userId + " created successfully.");
        }
    }

    @Override
    public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut)
            throws InvalidDateException, UserNotFoundException, RoomNotFoundException,
            InsufficientBalanceException, RoomAlreadyBookedException {

        // Validate dates
        if (checkIn == null || checkOut == null) {
            throw new InvalidDateException("Check-in and check-out dates cannot be null.");
        }
        if (!checkOut.after(checkIn)) {
            throw new InvalidDateException("Check-out date must be after check-in date.");
        }

        // Find user
        User user = findUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }

        // Find room
        Room room = findRoomByNumber(roomNumber);
        if (room == null) {
            throw new RoomNotFoundException("Room with number " + roomNumber + " not found.");
        }

        // Calculate nights and total cost
        int numberOfNights = calculateNights(checkIn, checkOut);
        int totalCost = numberOfNights * room.getPricePerNight();

        // Check user balance
        if (user.getBalance() < totalCost) {
            throw new InsufficientBalanceException("User " + userId + " has insufficient balance. " +
                    "Required: " + totalCost + ", Available: " + user.getBalance());
        }

        // Check room availability
        if (!isRoomAvailable(roomNumber, checkIn, checkOut)) {
            throw new RoomAlreadyBookedException("Room " + roomNumber + " is not available for the specified period.");
        }

        // Create booking with snapshot of room data
        Booking booking = new Booking(
                nextBookingId++,
                userId,
                roomNumber,
                checkIn,
                checkOut,
                room.getRoomType(), // Snapshot of room type
                room.getPricePerNight(), // Snapshot of price
                numberOfNights,
                totalCost);
        bookings.add(booking);

        // Deduct balance from user
        user.setBalance(user.getBalance() - totalCost);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Booking successful! User " + userId + " booked Room " + roomNumber +
                " from " + sdf.format(checkIn) + " to " + sdf.format(checkOut) +
                " for " + numberOfNights + " night(s). Total cost: " + totalCost);
    }

    @Override
    public void printAll() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("\n========== ALL ROOMS (Latest to Oldest) ==========");
        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
        } else {
            // Print from latest to oldest (reverse order)
            for (int i = rooms.size() - 1; i >= 0; i--) {
                Room room = rooms.get(i);
                System.out.println("Room Number: " + room.getRoomNumber() +
                        ", Type: " + room.getRoomType() +
                        ", Price/Night: " + room.getPricePerNight());
            }
        }

        System.out.println("\n========== ALL BOOKINGS (Latest to Oldest) ==========");
        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
        } else {
            // Print from latest to oldest (reverse order)
            for (int i = bookings.size() - 1; i >= 0; i--) {
                Booking booking = bookings.get(i);
                System.out.println("Booking ID: " + booking.getBookingId() +
                        ", User ID: " + booking.getUserId() +
                        ", Room Number: " + booking.getRoomNumber() +
                        ", Check-In: " + sdf.format(booking.getCheckIn()) +
                        ", Check-Out: " + sdf.format(booking.getCheckOut()) +
                        ", Room Type (at booking): " + booking.getBookedRoomType() +
                        ", Price/Night (at booking): " + booking.getPricePerNight() +
                        ", Nights: " + booking.getNumberOfNights() +
                        ", Total Cost: " + booking.getTotalCost());
            }
        }
        System.out.println();
    }

    @Override
    public void printAllUsers() {
        System.out.println("\n========== ALL USERS (Latest to Oldest) ==========");
        if (users.isEmpty()) {
            System.out.println("No users available.");
        } else {
            // Print from latest to oldest (reverse order)
            for (int i = users.size() - 1; i >= 0; i--) {
                User user = users.get(i);
                System.out.println("User ID: " + user.getUserId() +
                        ", Balance: " + user.getBalance());
            }
        }
        System.out.println();
    }

    // Helper Methods
    private Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    private User findUserById(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    private boolean isRoomAvailable(int roomNumber, Date checkIn, Date checkOut) {
        for (Booking booking : bookings) {
            if (booking.getRoomNumber() == roomNumber) {
                // Check for date overlaps
                // Two periods overlap if: NOT (end1 <= start2 OR start1 >= end2)
                if (!(checkOut.compareTo(booking.getCheckIn()) <= 0 ||
                        checkIn.compareTo(booking.getCheckOut()) >= 0)) {
                    return false; // Overlap found, room not available
                }
            }
        }
        return true; // Room is available
    }

    private int calculateNights(Date checkIn, Date checkOut) {
        long diffInMillis = checkOut.getTime() - checkIn.getTime();
        long diffInDays = diffInMillis / (1000 * 60 * 60 * 24);
        return (int) diffInDays;
    }
}
