import service.Service;
import service.ServiceImpl;
import enums.RoomType;
import exception.*;
import java.util.Calendar;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        Service service = new ServiceImpl();

        System.out.println("========== HOTEL RESERVATION SYSTEM ==========\n");

        // Create 3 rooms
        System.out.println("--- Creating Rooms ---");
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setRoom(2, RoomType.JUNIOR, 2000);
        service.setRoom(3, RoomType.MASTER, 3000);

        // Create 2 users
        System.out.println("\n--- Creating Users ---");
        service.setUser(1, 5000);
        service.setUser(2, 10000);

        // Test bookings
        System.out.println("\n--- Booking Attempts ---");

        // User 1 tries booking Room 2 from 30/06/2026 to 07/07/2026 (7 nights)
        // Total cost: 7 * 2000 = 14000, User balance: 5000 -> Should fail
        try {
            Date checkIn1 = createDate(30, 6, 2026);
            Date checkOut1 = createDate(7, 7, 2026);
            service.bookRoom(1, 2, checkIn1, checkOut1);
        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        // User 1 tries booking Room 2 from 07/07/2026 to 30/06/2026 (invalid dates)
        try {
            Date checkIn2 = createDate(7, 7, 2026);
            Date checkOut2 = createDate(30, 6, 2026);
            service.bookRoom(1, 2, checkIn2, checkOut2);
        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        // User 1 tries booking Room 1 from 07/07/2026 to 08/07/2026 (1 night)
        // Total cost: 1 * 1000 = 1000, User balance: 5000 -> Should succeed
        try {
            Date checkIn3 = createDate(7, 7, 2026);
            Date checkOut3 = createDate(8, 7, 2026);
            service.bookRoom(1, 1, checkIn3, checkOut3);
        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        // User 2 tries booking Room 1 from 07/07/2026 to 09/07/2026 (2 nights)
        // Room 1 is booked from 07/07 to 08/07 -> Should fail (overlap)
        try {
            Date checkIn4 = createDate(7, 7, 2026);
            Date checkOut4 = createDate(9, 7, 2026);
            service.bookRoom(2, 1, checkIn4, checkOut4);
        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        // User 2 tries booking Room 3 from 07/07/2026 to 08/07/2026 (1 night)
        // Total cost: 1 * 3000 = 3000, User balance: 10000 -> Should succeed
        try {
            Date checkIn5 = createDate(7, 7, 2026);
            Date checkOut5 = createDate(8, 7, 2026);
            service.bookRoom(2, 3, checkIn5, checkOut5);
        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        // Update Room 1: setRoom(1, MASTER, 10000)
        System.out.println("\n--- Updating Room 1 ---");
        service.setRoom(1, RoomType.MASTER, 10000);

        // Print all rooms and bookings
        service.printAll();

        // Print all users
        service.printAllUsers();
    }

    // Helper method to create Date objects
    private static Date createDate(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1); // Month is 0-indexed
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
