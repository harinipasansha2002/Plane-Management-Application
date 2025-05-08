import java.util.InputMismatchException;
import java.util.Scanner;

public class PlaneManagement {
    public static void main(String[] args) {
        //Initialize the array to store plane seats
        String[][] plane_seats = new String[4][];
        plane_seats[0] = new String[14];
        plane_seats[1] = new String[12];
        plane_seats[2] = new String[12];
        plane_seats[3] = new String[14];

        //Initialize all the seats are available
        for (int row_num = 0; row_num < plane_seats.length; row_num++) {
            for (int column_num = 0; column_num < plane_seats[row_num].length; column_num++) {
                plane_seats[row_num][column_num] = "0";
            }
        }
        //Initialize array to store tickets
        Ticket[] tickets = new Ticket[52];

        int option;
        Scanner input = new Scanner(System.in);

        //Main menu loop
        do {
            try {
                //Display menu options
                System.out.println("\n    WELCOME TO THE PLANE MANAGEMENT APPLICATION   ");
                System.out.println("\n**************************************************");
                System.out.println("*                  MENU OPTIONS                  *");
                System.out.println("**************************************************");
                System.out.println("     1) Buy a seat");
                System.out.println("     2) Cancel a seat");
                System.out.println("     3) Find first available seat");
                System.out.println("     4) Show seating plan");
                System.out.println("     5) Print tickets information and total sales");
                System.out.println("     6) Search ticket");
                System.out.println("     0) Quit");
                System.out.println("**************************************************");

                System.out.println("\nPlease select an option: ");
                option = input.nextInt();

                //Check validation of the user input
                if (option < 0 || option > 6) {
                    System.out.println("Please select a correct option.");
                }

                //Switch statement to perform actions based on user's choice
                switch (option) {
                    case 0:
                        System.out.println("Exiting the Plane Management application.");
                        break;
                    case 1:
                        buy_seat(plane_seats, tickets);
                        break;
                    case 2:
                        cancel_seat(plane_seats, tickets);
                        break;
                    case 3:
                        find_first_available(plane_seats);
                        break;
                    case 4:
                        show_seating_plan(plane_seats);
                        break;
                    case 5:
                        print_tickets_info(tickets);
                        break;
                    case 6:
                        search_ticket(plane_seats, tickets);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                input.next();
                option = -1;
            }
        } while (option != 0); //Continue loop until user selects to quit
    }

    //Method to buy a seat
    private static void buy_seat(String[][] plane_seats, Ticket[] tickets) {
        try {
            System.out.println("\n-----Book your seat-----");
            //Asks the user to enter seat details
            System.out.println("\nPlease enter a row letter(A-D) of the seat:");
            Scanner s = new Scanner(System.in);
            String row_letter = s.nextLine().toUpperCase();
            int row_index = Character.toUpperCase(row_letter.charAt(0)) - 'A';
            if (!(row_letter.equals("A") || row_letter.equals("B") || row_letter.equals("C") || row_letter.equals("D"))) {
                System.out.println("Please enter A,B,C,D as a row letter.");
                buy_seat(plane_seats, tickets);
            }
            else {
                System.out.println("Please enter a seat number(1-14) of the seat:");
                int seat_index = s.nextInt();
                int seat_num = seat_index - 1;
                System.out.println(row_letter + seat_index);
                //Asks the user to enter personal information
                System.out.println("Please enter your name:");

                while(true) {
                    Scanner input = new Scanner(System.in);
                    String name = input.nextLine().trim();
                    if (!name.matches("[a-zA-Z]+")) {
                        System.out.println("Invalid name format. Please enter your name:");
                        continue;
                    }
                    System.out.println("Please enter your surname:");
                    while (true) {
                        String surname = input.nextLine().trim();
                        if (!surname.matches("[a-zA-Z]+")) {
                            System.out.println("Invalid surname format. Please enter your surname:");
                            continue;
                        }
                        System.out.println("Please enter your email:");
                        while (true) {
                            String email = input.next();
                            if (!email.contains("@")) {
                                System.out.println("Invalid email format. Please enter your email:");
                                continue;
                            } else if (!email.contains(".")) {
                                System.out.println("Invalid email format. Please enter your email:");
                                continue;
                            }
                            Person passenger = new Person(name, surname, email);

                            //Calculate price based on seat index
                            int price;
                            if (seat_index > 0 && seat_index <= 5) {
                                price = 200;
                            } else if (seat_index > 5 && seat_index <= 9) {
                                price = 150;
                            } else {
                                price = 180;
                            }
                            Ticket ticket = new Ticket(row_letter, seat_index, price, passenger);

                            //Check validation of the seat selection
                            if (row_index < 0 || row_index >= plane_seats.length || seat_num < 0 || seat_num >= plane_seats[row_index].length) {
                                System.out.println("\nPlease enter a valid seat.");
                                buy_seat(plane_seats, tickets);
                            }

                            //Check the seat is available or not
                            if (plane_seats[row_index][seat_num].equals("0")) {
                                System.out.println("\nSuccessfully booked your seat. Your seat is " + row_letter + seat_index + ".");
                                plane_seats[row_index][seat_num] = "X"; //Mark seat as booked

                                //Store ticket information
                                switch (row_letter) {
                                    case "A" -> tickets[seat_num] = ticket;
                                    case "B" -> tickets[seat_num + 14] = ticket;
                                    case "C" -> tickets[seat_num + 26] = ticket;
                                    case "D" -> tickets[seat_num + 38] = ticket;
                                }
                                ticket.save(); //Save ticket information to file
                            } else {
                                System.out.println("\nSorry, the seat " + row_letter + seat_index + " is already booked. Please book another seat.");
                            }
                            break;
                        }
                        break;
                    }
                    break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException | InputMismatchException e) {
            System.out.println("\nPlease enter a valid seat.");
        }
    }

    //Method to cancel a seat
    private static void cancel_seat(String[][] plane_seats, Ticket[] tickets) {
        try {
            System.out.println("\n-----Cancel your seat-----");

            //Asks the user to enter seat details for cancellation
            Scanner input = new Scanner(System.in);
            System.out.println("Please enter a row letter (A-D) of the cancel seat:");
            Scanner s = new Scanner(System.in);
            String row_letter = s.nextLine().toUpperCase();
            int row_index = Character.toUpperCase(row_letter.charAt(0)) - 'A';
            System.out.println("Please enter a seat number (1-14) of the cancel seat:");
            int seat_index = input.nextInt();
            int seat_num = seat_index - 1;
            System.out.println(row_letter + seat_index);

            //Check validation of the seat selection
            if (row_index < 0 || row_index >= plane_seats.length || seat_index < 0 || seat_index >= plane_seats[row_index].length) {
                System.out.println("Please enter a valid seat.");
                cancel_seat(plane_seats, tickets);
            }

            //Check the seat is already canceled or not
            if (plane_seats[row_index][seat_num].equals("0")) {
                System.out.println("Seat " + row_letter + seat_index + " is available. Please select another seat.");
            } else if (plane_seats[row_index][seat_num].equals("X")) {
                plane_seats[row_index][seat_num] = "0";
                System.out.println("Successfully cancel " + row_letter + seat_index + " seat.");

                //Remove ticket information
                switch (row_letter) {
                    case "A" -> tickets[seat_num] = null;
                    case "B" -> tickets[seat_num + 14] = null;
                    case "C" -> tickets[seat_num + 26] = null;
                    case "D" -> tickets[seat_num + 38] = null;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please enter a valid seat.");
            cancel_seat(plane_seats, tickets);
        }
    }

    //Method to find first available seat
    private static void find_first_available(String[][] plane_seats) {
        System.out.println("\n-----Find first available seat-----");

        //Check the first available plane seat
        for (int i = 0; i < plane_seats.length; i++) {
            for (int j = 0; j < plane_seats[i].length; j++) {
                if (plane_seats[i][j].equals("0")) {
                    char row = (char) ('A' + i); //Calculate the row letter based on the current row index
                    System.out.println("The first available seat is " + row + (j + 1) + ".");
                    return;
                }
            }
        }
        System.out.println("Sorry, no available seats.");
    }

    //Method to show seating plan
    private static void show_seating_plan(String[][] plane_seats) {
        System.out.println("\n-----Seating Plan-----");

        //Display the seating plan
        for (int row_num = 0; row_num < plane_seats.length; row_num++) {
            for (int column_num = 0; column_num < plane_seats[row_num].length; column_num++) {
                if (plane_seats[row_num][column_num].equals("1")) {
                    System.out.print(plane_seats[row_num][column_num] = "X" + "\t"); //If the seat is booked, mark it as "X" and display
                    plane_seats[row_num][column_num].equals("1");
                } else {
                    System.out.print(plane_seats[row_num][column_num] + "\t");
                }
            }
            System.out.println();
        }
    }

    //Method to print tickets information and total sales
    private static void print_tickets_info(Ticket[] tickets) {
        System.out.println("\n-----Plane Tickets Information-----");

        //Calculate total sales of the tickets
        int total = 0;
        for (Ticket ticket : tickets) {
            if (ticket != null) { //Check if the current ticket is not null
                ticket.print_ticket(); //Display the details of the ticket
                total += ticket.getPrice(); //Add the ticket price to the total sales
            }
        }
        System.out.println("\nTotal ticket price: €" + total);
    }

    //Method to search ticket
    private static void search_ticket(String[][] plane_seats, Ticket[] tickets) {
        try {
            System.out.println("\n-----Search Ticket-----");

            //Asks the user to enter seat details
            Scanner input = new Scanner(System.in);
            System.out.println("Please enter a row letter (A-D) of the seat:");
            String row_letter = input.next().toUpperCase();
            int row_index = Character.toUpperCase(row_letter.charAt(0)) - 'A';
            System.out.println("Please enter a seat number (1-14) of the seat:");
            int seat_index = input.nextInt();
            int seat_num = seat_index - 1;

            //Check the seat is available both in the plane seats array and in the tickets array
            if (plane_seats[row_index][seat_num].equals("0") && tickets[seat_num] == null) {
                System.out.println("This seat is available.");
            } else {
                for (Ticket ticket : tickets) {
                    if (ticket != null) {
                        if (ticket.getRow().equals(row_letter) && ticket.getSeat() == seat_index) {

                            //Assign the ticket to the corresponding index in the tickets array based on the row letter
                            switch (row_letter) {
                                case "A" -> tickets[seat_index] = ticket;
                                case "B" -> tickets[seat_index + 14] = ticket;
                                case "C" -> tickets[seat_index + 26] = ticket;
                                case "D" -> tickets[seat_index + 38] = ticket;
                            }
                            ticket.print_ticket(); //Display the details of the ticket
                            break;
                        }
                    }
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Please enter a valid seat.");
            search_ticket(plane_seats, tickets);
        }
    }
}