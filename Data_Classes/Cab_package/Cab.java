package Data_Classes.Cab_package;

import java.util.ArrayList;

public class Cab{
        protected int id;
        private static int numberOfCabs = 0;
        private int costPerKm;
        protected String license_plate;
        private int capacity;
        private ArrayList<Integer> route_ids;
        private Driver driver;

        public Cab(int id,String license_plate, int capacity){
                this.id = id;
                this.license_plate = license_plate;
                this.capacity = capacity;
                this.route_ids = new ArrayList<>();
        }

        public int getCostPerKm(){
                return this.costPerKm;
        }

        public String getLicense_plate(){return this.license_plate;}
        public int getCapacity(){
                return this.capacity;
        }
        public ArrayList<Integer> getRouteIds(){
                return this.route_ids;
        }
        public Driver getDriver(){
                return  this.driver;
        }

        public void setRoute_ids(ArrayList<Integer> ids){ this.route_ids = ids;}
        public void setDriver(Driver driver){
                this.driver = driver;
        }
        public void addBookingRoute(int id){
                this.route_ids.add(id);
        }
        public void removeBookingRoute(int id){
                this.route_ids.remove(id);
        }

        public void updateCapacity(int newCapacity){
                this.capacity = newCapacity;
        }

        public String toString(){
                return "Id: "+this.id + " license number: " + this.license_plate+ " capacity: "+this.capacity + " cost per km: "+ this.costPerKm + " Route ids: "+this.route_ids.toString() +  " Driver: "+this.driver;
        }
        }


