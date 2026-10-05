import java.io.*;
import java.util.*;

class Room {
    int roomNumber;
    String category;
    double price;
    int capacity;
    boolean available = true;

    Room(int roomNumber, String category, double price, int capacity) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.capacity = capacity;
    }
}

class Reservation {
    int bookingId;
    String guestName;
    int guests;
    int days;
    Room room;
    double totalAmount;
    String status = "Booked";
    String paymentStatus = "Pending";

    Reservation(int bookingId, String guestName, int guests,
                int days, Room room) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.guests = guests;
        this.days = days;
        this.room = room;
        this.totalAmount = room.price * days;
    }
}

public class HotelReservationSystem {

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> bookings = new ArrayList<>();
    static int nextBookingId = 1001;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        rooms.add(new Room(101, "Standard", 100, 2));
        rooms.add(new Room(102, "Standard", 100, 2));
        rooms.add(new Room(201, "Deluxe", 150, 3));
        rooms.add(new Room(202, "Deluxe", 150, 3));
        rooms.add(new Room(301, "Suite", 250, 4));

        loadData();

        while (true) {

            System.out.println("\n=== Hotel Reservation System ===");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Search Rooms");
            System.out.println("3. Book Room");
            System.out.println("4. Cancel Reservation");
            System.out.println("5. View Booking Details");
            System.out.println("6. Simulate Payment");
            System.out.println("7. Exit");

            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            // 1. View Available Rooms
            if (choice == 1) {

                System.out.println("\n========== Available Rooms ==========");

                System.out.println(
                    "+----------+------------+------------+------------+"
                );
                System.out.println(
                    "| Room No. | Category   | Max Guests | Price/Day  |"
                );
                System.out.println(
                    "+----------+------------+------------+------------+"
                );

                for (Room r : rooms) {
                    if (r.available) {
                        System.out.printf(
                            "| %-8d | %-10s | %-10d | $%-9.2f |%n",
                            r.roomNumber,
                            r.category,
                            r.capacity,
                            r.price
                        );
                    }
                }

                System.out.println(
                    "+----------+------------+------------+------------+"
                );

            // 2. Search Rooms
            } else if (choice == 2) {

                System.out.print("Enter category: ");
                String category = sc.nextLine();

                boolean found = false;

                System.out.println("\n========== Search Results ==========");

                System.out.println(
                    "+----------+------------+------------+------------+"
                );
                System.out.println(
                    "| Room No. | Category   | Max Guests | Price/Day  |"
                );
                System.out.println(
                    "+----------+------------+------------+------------+"
                );

                for (Room r : rooms) {

                    if (r.available &&
                        r.category.equalsIgnoreCase(category)) {

                        System.out.printf(
                            "| %-8d | %-10s | %-10d | $%-9.2f |%n",
                            r.roomNumber,
                            r.category,
                            r.capacity,
                            r.price
                        );

                        found = true;
                    }
                }

                System.out.println(
                    "+----------+------------+------------+------------+"
                );

                if (!found) {
                    System.out.println("No rooms found.");
                }

            // 3. Book Room
            } else if (choice == 3) {

                System.out.print("Enter room number: ");
                int roomNumber = sc.nextInt();
                sc.nextLine();

                Room selected = null;

                for (Room r : rooms) {
                    if (r.roomNumber == roomNumber && r.available) {
                        selected = r;
                        break;
                    }
                }

                if (selected == null) {
                    System.out.println("Room not available.");
                    continue;
                }

                System.out.print("Enter guest name: ");
                String name = sc.nextLine();

                System.out.print("Enter number of guests: ");
                int guests = sc.nextInt();

                if (guests > selected.capacity) {
                    System.out.println(
                        "Sorry, this room can accommodate maximum "
                        + selected.capacity + " guests."
                    );
                    continue;
                }

                System.out.print("Enter number of days: ");
                int days = sc.nextInt();

                double total = selected.price * days;

                Reservation b = new Reservation(
                    nextBookingId,
                    name,
                    guests,
                    days,
                    selected
                );

                bookings.add(b);
                selected.available = false;

                System.out.printf(
                    "Total Amount: $%.2f%n",
                    total
                );

                System.out.println("Room booked successfully!");
                System.out.println("Booking ID: " + nextBookingId);

                System.out.print(
                    "Do you want to make payment now? (yes/no): "
                );

                String pay = sc.next();

                if (pay.equalsIgnoreCase("yes")) {

                    b.paymentStatus = "Paid";

                    System.out.printf(
                        "Payment of $%.2f processed successfully.%n",
                        total
                    );

                    System.out.println("Booking confirmed!");

                } else {
                    System.out.println("Payment pending.");
                }

                saveData();
                nextBookingId++;

            // 4. Cancel Reservation
            } else if (choice == 4) {

                System.out.print("Enter booking ID: ");
                int id = sc.nextInt();

                boolean found = false;

                for (Reservation b : bookings) {

                    if (b.bookingId == id &&
                        b.status.equals("Booked")) {

                        b.status = "Cancelled";
                        b.room.available = true;

                        saveData();

                        found = true;

                        System.out.println(
                            "Reservation cancelled successfully."
                        );

                        break;
                    }
                }

                if (!found) {
                    System.out.println("Booking not found.");
                }

            // 5. View Booking Details
            } else if (choice == 5) {

                if (bookings.isEmpty()) {

                    System.out.println("No bookings found.");

                } else {

                    System.out.println(
                        "\n========== Booking Details =========="
                    );

                    System.out.println(
                        "+----------+----------------+--------+-------+----------+------------+------------+----------+"
                    );

                    System.out.println(
                        "| Booking  | Guest Name     | Guests | Days  | Room No. | Total      | Payment    | Status   |"
                    );

                    System.out.println(
                        "+----------+----------------+--------+-------+----------+------------+------------+----------+"
                    );

                    for (Reservation b : bookings) {

                        System.out.printf(
                            "| %-8d | %-14s | %-6d | %-5d | %-8d | $%-9.2f | %-10s | %-8s |%n",
                            b.bookingId,
                            b.guestName,
                            b.guests,
                            b.days,
                            b.room.roomNumber,
                            b.totalAmount,
                            b.paymentStatus,
                            b.status
                        );
                    }

                    System.out.println(
                        "+----------+----------------+--------+-------+----------+------------+------------+----------+"
                    );
                }

            // 6. Simulate Payment
            } else if (choice == 6) {

                System.out.print("Enter booking ID: ");
                int id = sc.nextInt();

                boolean found = false;

                for (Reservation b : bookings) {

                    if (b.bookingId == id &&
                        b.status.equals("Booked")) {

                        found = true;

                        if (b.paymentStatus.equals("Paid")) {

                            System.out.println(
                                "Payment already completed."
                            );

                        } else {

                            b.paymentStatus = "Paid";

                            System.out.printf(
                                "Payment of $%.2f processed successfully.%n",
                                b.totalAmount
                            );

                            saveData();
                        }

                        break;
                    }
                }

                if (!found) {
                    System.out.println("Booking not found.");
                }

            // 7. Exit
            } else if (choice == 7) {

                System.out.println(
                    "Exiting Hotel Reservation System..."
                );

                sc.close();
                return;

            } else {

                System.out.println("Invalid option.");
            }
        }
    }

    // Save data into file
    public static void saveData() {

        try {

            FileWriter writer =
                new FileWriter("hotel_data.txt");

            for (Room r : rooms) {

                writer.write(
                    "ROOM|" +
                    r.roomNumber +
                    "|" +
                    r.available +
                    "\n"
                );
            }

            for (Reservation b : bookings) {

                writer.write(
                    "BOOKING|" +
                    b.bookingId + "|" +
                    b.guestName + "|" +
                    b.guests + "|" +
                    b.days + "|" +
                    b.room.roomNumber + "|" +
                    b.totalAmount + "|" +
                    b.status + "|" +
                    b.paymentStatus +
                    "\n"
                );
            }

            writer.close();

        } catch (IOException e) {

            System.out.println("Error saving data.");
        }
    }

    // Load saved data
    public static void loadData() {

        File file = new File("hotel_data.txt");

        if (!file.exists()) {
            return;
        }

        try {

            Scanner fileScanner =
                new Scanner(file);

            while (fileScanner.hasNextLine()) {

                String line =
                    fileScanner.nextLine();

                String[] data =
                    line.split("\\|");

                if (data[0].equals("ROOM")) {

                    int roomNumber =
                        Integer.parseInt(data[1]);

                    boolean available =
                        Boolean.parseBoolean(data[2]);

                    for (Room r : rooms) {

                        if (r.roomNumber == roomNumber) {

                            r.available = available;
                            break;
                        }
                    }

                } else if (data[0].equals("BOOKING")) {

                    int bookingId =
                        Integer.parseInt(data[1]);

                    String guestName = data[2];

                    int guests =
                        Integer.parseInt(data[3]);

                    int days =
                        Integer.parseInt(data[4]);

                    int roomNumber =
                        Integer.parseInt(data[5]);

                    double totalAmount =
                        Double.parseDouble(data[6]);

                    String status = data[7];

                    String paymentStatus = data[8];

                    Room bookedRoom = null;

                    for (Room r : rooms) {

                        if (r.roomNumber == roomNumber) {

                            bookedRoom = r;
                            break;
                        }
                    }

                    if (bookedRoom != null) {

                        Reservation b =
                            new Reservation(
                                bookingId,
                                guestName,
                                guests,
                                days,
                                bookedRoom
                            );

                        b.totalAmount = totalAmount;
                        b.status = status;
                        b.paymentStatus = paymentStatus;

                        bookings.add(b);

                        if (bookingId >= nextBookingId) {

                            nextBookingId =
                                bookingId + 1;
                        }
                    }
                }
            }

            fileScanner.close();

        } catch (Exception e) {

            System.out.println(
                "Error loading saved data."
            );
        }
    }
}