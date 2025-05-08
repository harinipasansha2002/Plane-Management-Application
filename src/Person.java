public class Person {
    //Person attributes
    private String name; //Person's name
    private String surname; //Person's surname
    private String email; //Person's email

    //Constructor to initialize Person object
    public Person(String name, String surname, String email){
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    //Getter and setter methods for Person attributes
    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getSurname(){
        return this.surname;
    }

    public void setSurname(String surname){
        this.surname = surname;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    //Method to print person information
    public void print_person(){
        //Print person information
        System.out.println("\n.....Passenger Information.....");
        System.out.println("Name: " + getName());
        System.out.println("Surname: " + getSurname());
        System.out.println("Email: " + getEmail());
    }
}