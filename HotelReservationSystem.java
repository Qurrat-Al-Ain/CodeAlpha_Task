import java.util.*;

class Room {
    String type;
    double price;
    boolean isAvailable;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
        this.isAvailable = true; // All rooms start available
    }

    public void bookRoom() {
        if (isAvailable) {
            isAvailable = false; // Mark room as unavailable
        }
    }

    public void cancelBooking() {
        isAvailable = true; // Mark room as available again
    }
}

class Reservation {
    String guestName;
    Room room;
    String checkInDate;
    String checkOutDate;
    double totalPrice;

    public Reservation(String guestName, Room room, String checkInDate, String checkOutDate) {
        this.guestName = guestName;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = room.price; // For simplicity, we use price as total price
    }

    public void displayBookingDetails() {
        System.out.println("\n📝 --- Booking Summary ---");
        System.out.println("Guest: " + guestName);
        System.out.println("Room Type: " + room.type);
        System.out.println("Check-In Date: " + checkInDate);
        System.out.println("Check-Out Date: " + checkOutDate);
        System.out.println("Total Price: Rs" + totalPrice);
        System.out.println("Thank you for choosing our hotel! We look forward to welcoming you. 😊");
    }
}

class Hotel {
    Map<Integer, Room> rooms = new HashMap<>();
    List<Reservation> reservations = new ArrayList<>();

    public Hotel() {
        // Initializing rooms: Room ID -> Room Object
        rooms.put(101, new Room("Single", 3000));
        rooms.put(102, new Room("Double", 5000));
        rooms.put(103, new Room("Suite", 8000));
        rooms.put(104, new Room("Single", 3000));
        rooms.put(105, new Room("Double", 5000));
    }

    // Search for available rooms by type
    public List<Room> searchAvailableRooms(String type) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms.values()) {
            if (room.type.equalsIgnoreCase(type) && room.isAvailable) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    // Make a reservation for a room
    public Reservation makeReservation(String guestName, Room room, String checkInDate, String checkOutDate) {
        if (room.isAvailable) {
            room.bookRoom();
            Reservation reservation = new Reservation(guestName, room, checkInDate, checkOutDate);
            reservations.add(reservation);
            return reservation;
        }
        return null; // Room not available
    }

    // Simulate payment process (In real world, use an external API)
    public void processPayment(double amount) {
        System.out.println("\n💳 Processing payment of Rs" + amount + "...");
        System.out.println("🟢 Payment successful! Your reservation is confirmed.");
    }
}

public class HotelReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hotel hotel = new Hotel();

        System.out.println("🌟 Welcome to the Hotel Reservation System! 🌟");
        System.out.println("Let’s help you find the perfect room for your stay.\n");

        while (true) {
            System.out.println("Please choose an option:");
            System.out.println("1. Search for available rooms");
            System.out.println("2. Make a reservation");
            System.out.println("3. View my bookings");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1: // Search available rooms
                    System.out.print("Which type of room are you looking for? (Single, Double, Suite): ");
                    String roomType = scanner.nextLine();
                    List<Room> availableRooms = hotel.searchAvailableRooms(roomType);

                    if (availableRooms.isEmpty()) {
                        System.out.println("Oops! No rooms available of this type at the moment. 😕");
                    } else {
                        System.out.println("\n✅ We have the following available rooms:");
                        for (Room room : availableRooms) {
                            System.out.println("Room ID: " + room.hashCode() + " | Type: " + room.type + " | Price: Rs" + room.price);
                        }
                    }
                    break;

                case 2: // Make a reservation
                    System.out.print("Let’s get your reservation details. What’s your name? ");
                    String guestName = scanner.nextLine();

                    System.out.print("What type of room would you like? (Single, Double, Suite): ");
                    String type = scanner.nextLine();

                    availableRooms = hotel.searchAvailableRooms(type);
                    if (availableRooms.isEmpty()) {
                        System.out.println("Oh no! No rooms are available in this category. Please try again later. 🛑");
                    } else {
                        System.out.print("Please select a Room ID to book: ");
                        int roomId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        Room selectedRoom = hotel.rooms.get(roomId);

                        if (selectedRoom != null && selectedRoom.isAvailable) {
                            System.out.print("Great! Please enter your check-in date (YYYY-MM-DD): ");
                            String checkInDate = scanner.nextLine();

                            System.out.print("Now, enter your check-out date (YYYY-MM-DD): ");
                            String checkOutDate = scanner.nextLine();

                            Reservation reservation = hotel.makeReservation(guestName, selectedRoom, checkInDate, checkOutDate);

                            if (reservation != null) {
                                reservation.displayBookingDetails();
                                hotel.processPayment(reservation.totalPrice);
                            } else {
                                System.out.println("Sorry, the room is already booked. Please choose another one. 😞");
                            }
                        } else {
                            System.out.println("Invalid Room ID or the room is already booked. Try again. 🚫");
                        }
                    }
                    break;

                case 3: // View booking details
                    if (hotel.reservations.isEmpty()) {
                        System.out.println("You have no bookings yet. Let's make a reservation first! 😊");
                    } else {
                        System.out.println("\n--- Your Booking Details ---");
                        for (Reservation res : hotel.reservations) {
                            res.displayBookingDetails();
                        }
                    }
                    break;

                case 4: // Exit
                    System.out.println("👋 Thank you for using our Hotel Reservation System. Have a great day!");
                    return;

                default:
                    System.out.println("❌ Invalid option, please try again.");
            }
        }
    }
}
