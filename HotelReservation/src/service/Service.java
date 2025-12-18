package service;

import enums.RoomType;
import exception.*;
import java.util.Date;

public interface Service {

    void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight);

    void setUser(int userId, int balance);

    void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut)
            throws InvalidDateException, UserNotFoundException, RoomNotFoundException,
            InsufficientBalanceException, RoomAlreadyBookedException;

    void printAll();

    void printAllUsers();
}
