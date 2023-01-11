package Data_Classes;


import Data_Classes.Cab_package.*;
import Data_Classes.User_package.*;
import utils_package.App;

import java.time.LocalTime;
import java.util.*;

public class Booking {
    private int id;
    private static int numberOfBookings = 0;
    private int route_id;
    private int cab_id;
    private Date date;
    private LocalTime time;
    private ArrayList<String> studentIds = new ArrayList<>();
    private User host;
    private boolean paymentDone = false;
    boolean sharing = false;

    public BookingStatus status;
    public Booking(int id, int route_id, int cab_id, Date date, LocalTime time, Boolean sharing){
        this.id = ++numberOfBookings;
        this.route_id = route_id;
        this.cab_id = cab_id;
        this.date = date;
        this.time = time;
        if(sharing) this.status = BookingStatus.PARTIALLY_BOOKED;
        else this.status = BookingStatus.EMPTY;
    }

    public void setTime(LocalTime t) {
        time=t;
    }
    public void setDate(Date d) {
        date=d;
    }

    public int getId(){
        return this.id;
    }

    public int getRoute_id(){
        return  this.route_id;
    }
    public int getCab_id(){
        return this.cab_id;
    }

    public Date getDate(){
        return this.date;
    }
    public  LocalTime getTime(){
        return this.time;
    }
    public ArrayList<String> getStudentIds(){
        return this.studentIds;
    }
    public void addStudentIds(String id){
        this.studentIds.add(id);
    }

    public void setSharing(boolean flag){
        this.sharing = flag;
    }
    public boolean isSharing(){
        return this.sharing;
    }

    public BookingStatus getStatus(){
        return this.status;
    }

    public void setStatus(BookingStatus status){
        this.status = status;
    }
    public boolean getPaymentStatus(){
        return this.paymentDone;
    }

    public int calculateCost(int route_id,int cab_id) {
        Route selectedRoute = App.AllRoutes.get(route_id);
        Cab selectedCab = App.AllCabs.get(cab_id);
        return(((selectedRoute).getDistance())*(selectedCab.getCostPerKm()))+selectedRoute.getTollCharges();
    }
    public void setHost(User h) {
        host=h;
    }
    public User getHost() {
        return Authentication.user;
    }

    public void setStudentIds(ArrayList<String> ids){
        this.studentIds = ids;
    }
    public void setPaymentStatus(Boolean bool){
        this.paymentDone = bool;
    }
    public String toString(){
        return "ID: "+this.id +" status: "+ this.status +" route id: "+ this.route_id + " cab id: " + this.cab_id + " date: "+ this.date.toString()+ " Is sharing? "+ this.sharing
                + " time: " +this.time.toString() + " student IDs: " + this.studentIds.toString() + " host: " + this.host.toString() + " payment status: " +
                String.valueOf(this.paymentDone);
    }
}
