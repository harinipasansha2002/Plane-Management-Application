import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Ticket {
    //Ticket attributes
    private String row; //row letter
    private int seat; //seat number
    private int price; //Ticket price
    private Person person; //Person associated with the ticket

    //Constructor to initialize Ticket object
    public Ticket(String row, int seat, int price, Person person){
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    //Getter and setter methods for Ticket attributes
    public String getRow(){
        return this.row;
    }

    public void setRow(String row){
        this.row = row;
    }

    public int getSeat(){
        return this.seat;
    }

    public void setSeat(int seat){
        this.seat = seat;
    }

    public int getPrice(){
        return this.price;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public Person getPerson(){
        return this.person;
    }

    public void setPerson(Person person){
        this.person = person;
    }

    //Method to print ticket information
    public void print_ticket(){
        person.print_person(); //Print person information associated with the ticket

        //Print ticket details
        System.out.println(".....Ticket Information.....");
        System.out.println("Row Letter: " + row);
        System.out.println("Seat Number: " + seat);
        System.out.println("Ticket Price €: " + price);
    }

    //Method to save ticket details to a file
    public void save(){
        String filename = row + seat + ".txt"; //File name based on row letter and seat number

        try {
            //Write ticket details to a file
            PrintWriter file = new PrintWriter(new FileWriter(filename));
            file.write("Plane Ticket Details");
            file.write("\nName: " + person.getName());
            file.write("\nSurname: " + person.getSurname());
            file.write("\nEmail: " + person.getEmail());
            file.write("\nSeat: " + getRow() + getSeat());
            file.write("\nPrice: €" + getPrice());
            file.close();
        }catch (IOException e){
            System.err.println("An error occurred.");
        }
    }
}