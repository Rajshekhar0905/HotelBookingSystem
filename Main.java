import java.util.Scanner;

// Entry point — shows a menu and calls hotel operations based on user choice
public class Main {

    public static void main(String[] args) {

        Hotel hotel = new Hotel();
        Scanner sc = new Scanner(System.in);

        // add a few rooms by default so you can test right away
        hotel.addRoom(101, "Single", 1200);
        hotel.addRoom(102, "Double", 2000);
        hotel.addRoom(201, "Suite",  4500);

        System.out.println("\nWelcome to the Hotel Room Allocation System!");

        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();   // consume leftover newline

            switch (choice) {

                case 1:
                    System.out.print("Enter guest name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter phone number: ");
                    String phone = sc.nextLine();
                    hotel.addGuest(name, phone);
                    break;

                case 2:
                    System.out.print("Enter room number: ");
                    int roomNo = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter room type (Single/Double/Suite): ");
                    String type = sc.nextLine();
                    System.out.print("Enter price per night: ");
                    double price = sc.nextDouble();
                    hotel.addRoom(roomNo, type, price);
                    break;

                case 3:
                    System.out.print("Enter guest ID: ");
                    int gId = sc.nextInt();
                    System.out.print("Enter room number: ");
                    int rNo = sc.nextInt();
                    System.out.print("Enter number of nights: ");
                    int nights = sc.nextInt();
                    hotel.allocateRoom(gId, rNo, nights);
                    break;

                case 4:
                    System.out.print("Enter guest ID to checkout: ");
                    int checkoutId = sc.nextInt();
                    hotel.checkoutGuest(checkoutId);
                    break;

                case 5:
                    hotel.displayAllRooms();
                    break;

                case 6:
                    hotel.displayAllBookings();
                    break;

                case 7:
                    hotel.displayAvailableRooms();
                    break;

                case 0:
                    System.out.println("Thank you for using the Hotel System. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        sc.close();
    }

    // prints the menu options
    static void printMenu() {
        System.out.println("\n========= MENU =========");
        System.out.println("1. Add Guest");
        System.out.println("2. Add Room");
        System.out.println("3. Allocate Room to Guest");
        System.out.println("4. Checkout Guest");
        System.out.println("5. Display All Rooms");
        System.out.println("6. Display All Bookings");
        System.out.println("7. Display Available Rooms");
        System.out.println("0. Exit");
        System.out.println("========================");
    }
}
