package Data_Classes.Cab_package;

enum CarType{
    SUV,
    SEDAN,
    MINI,
    LUXURY;
}
public class Car extends Cab{
    CarType carType;
    Car(int id, String license_plate, int capacity){
        super(id, license_plate, capacity);
    }
}
