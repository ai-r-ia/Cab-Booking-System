package Data_Classes.Cab_package;

public class Driver{

    String driver_name;
    long driver_contact;

    public Driver(String name, long contact){
        this.driver_name = name;
        this.driver_contact = contact;
    }
    public void update(String name, long contact){
        driver_name = name;
        driver_contact = contact;
    }

    public String toString(){
        return this.driver_name +";"+ Long.toString(this.driver_contact);
    }
}