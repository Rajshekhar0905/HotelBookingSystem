import exception.*;
import model.*;
import service.HotelService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Interactive Hotel Booking System
 * Uses Scanner for all user input — fully menu-driven.
 */
public class Main {

    static Scanner scanner = new Scanner(System.in);
    static HotelService hotel = new HotelService("Hotel Paradise");
    static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(String[] args) {

        // Load sample rooms
        hotel.addRoom(new StandardRoom(101, 1, true,  true));
        hotel.addRoom(new StandardRoom(102, 1, true,  false));
        hotel.addRoom(new DeluxeRoom  (201, 2, true,  true));
        hotel.addRoom(new DeluxeRoom  (202, 2, false, true));
        hotel.addRoom(new SuiteRoom   (301, 3, true,  true));
        hotel.addRoom(new SuiteRoom   (302, 3, false, false));

        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║      HOTEL PARADISE -- BOOKING SYSTEM       ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1: registerGuest();            break;
                case 2: hotel.showAllRooms();       break;
                case 3: hotel.showAvailableRooms(); break;
                case 4: bookRoom();                 break;
                case 5: cancelBooking();            break;
                case 6: makePayment();              break;
                case 7: hotel.showAllBookings();    break;
                case 8: hotel.showRevenueSummary(); break;
                case 0:
                    System.out.println("\n  Goodbye! Thank you for using Hotel Paradise.");
                    running = false;
                    break;
                default:
                    System.out.println("  [!] Invalid choice. Please enter a number from the menu.");
            }
        }
        scanner.close();
    }

    // MAIN MENU
    static void printMainMenu() {
        System.out.println("\n  +----------------------------------+");
        System.out.println("  |           MAIN MENU              |");
        System.out.println("  +----------------------------------+");
        System.out.println("  |  1. Register Guest               |");
        System.out.println("  |  2. View All Rooms               |");
        System.out.println("  |  3. View Available Rooms         |");
        System.out.println("  |  4. Book a Room                  |");
        System.out.println("  |  5. Cancel a Booking             |");
        System.out.println("  |  6. Make Payment                 |");
        System.out.println("  |  7. View All Bookings            |");
        System.out.println("  |  8. Revenue Summary              |");
        System.out.println("  |  0. Exit                         |");
        System.out.println("  +----------------------------------+");
    }

    // 1. REGISTER GUEST
    static void registerGuest() {
        System.out.println("\n  --- Register New Guest ---");
        try {
            System.out.print("  Guest ID   (e.g. G001) : ");
            String id = scanner.nextLine().trim();
            System.out.print("  Full Name              : ");
            String name = scanner.nextLine().trim();
            System.out.print("  Phone (10 digits)      : ");
            String phone = scanner.nextLine().trim();
            System.out.print("  Email                  : ");
            String email = scanner.nextLine().trim();
            System.out.print("  ID Proof (Aadhar/Pass) : ");
            String idPrf = scanner.nextLine().trim();

            // ENCAPSULATION: validated setters called inside constructor
            // IllegalArgumentException (Unchecked) thrown if input is invalid
            Guest guest = new Guest(id, name, phone, email, idPrf);
            hotel.registerGuest(guest);

        } catch (IllegalArgumentException e) {
            System.out.println("  [ERROR] Invalid input: " + e.getMessage());
        }
    }

    // 4. BOOK A ROOM
    static void bookRoom() {
        System.out.println("\n  --- Book a Room ---");
        hotel.showAvailableRooms();

        try {
            System.out.print("\n  Enter Guest ID         : ");
            String guestId = scanner.nextLine().trim();
            System.out.print("  Enter Room Number      : ");
            int roomNumber = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("  Check-In  (dd-MM-yyyy) : ");
            String inStr = scanner.nextLine().trim();
            System.out.print("  Check-Out (dd-MM-yyyy) : ");
            String outStr = scanner.nextLine().trim();

            // Parse dates
            LocalDate checkIn  = LocalDate.parse(inStr,  fmt);
            LocalDate checkOut = LocalDate.parse(outStr, fmt);

            // 4 checked exceptions declared — all caught below
            hotel.bookRoom(guestId, roomNumber, checkIn, checkOut);

        } catch (DateTimeParseException e) {
            System.out.println("  [ERROR] Invalid date format. Please use dd-MM-yyyy.");

        } catch (NumberFormatException e) {
            System.out.println("  [ERROR] Room number must be a valid number.");

        } catch (GuestNotFoundException e) {
            // CHECKED exception
            System.out.println("  [GuestNotFoundException] " + e.getMessage());
            System.out.println("  Tip: Register the guest first using Menu Option 1.");

        } catch (RoomNotFoundException e) {
            // CHECKED exception
            System.out.println("  [RoomNotFoundException] " + e.getMessage());
            System.out.println("  Tip: Use Menu Option 3 to see valid room numbers.");

        } catch (RoomAlreadyBookedException e) {
            // CHECKED exception
            System.out.println("  [RoomAlreadyBookedException] " + e.getMessage());
            System.out.println("  Room #" + e.getRoomNumber() + " is currently occupied.");

        } catch (InvalidCheckoutDateException e) {
            // CHECKED exception
            System.out.println("  [InvalidCheckoutDateException] " + e.getMessage());
            System.out.println("  Check-In: " + e.getCheckIn() + " | Check-Out: " + e.getCheckOut());
        }
    }

    // 5. CANCEL BOOKING
    static void cancelBooking() {
        System.out.println("\n  --- Cancel a Booking ---");
        hotel.showAllBookings();
        System.out.print("\n  Enter Booking ID to cancel (e.g. BKG1001): ");
        String bookingId = scanner.nextLine().trim();
        // cancelBooking() uses try-finally internally for guaranteed cleanup
        hotel.cancelBooking(bookingId);
    }

    // 6. MAKE PAYMENT
    static void makePayment() {
        System.out.println("\n  --- Make Payment ---");
        hotel.showAllBookings();
        System.out.print("\n  Enter Booking ID to pay for: ");
        String bookingId = scanner.nextLine().trim();

        Booking target = hotel.findBookingById(bookingId);
        if (target == null) {
            System.out.println("  [ERROR] Booking not found: " + bookingId);
            return;
        }

        System.out.printf("  Total Bill : Rs. %.2f%n", target.getTotalBill());
        System.out.print("  Enter Amount to Pay : Rs. ");

        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            // processPayment() throws InvalidPaymentException (UNCHECKED) if <= 0
            hotel.processPayment(target, amount);

        } catch (NumberFormatException e) {
            System.out.println("  [ERROR] Please enter a valid amount.");

        } catch (InvalidPaymentException e) {
            // UNCHECKED exception caught for graceful user experience
            System.out.println("  [InvalidPaymentException] " + e.getMessage());
            System.out.println("  Amount entered: Rs." + e.getAmount());
        }
    }

    // Helper: safe integer input with validation loop
    static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  [!] Please enter a valid number.");
            }
        }
    }
}
