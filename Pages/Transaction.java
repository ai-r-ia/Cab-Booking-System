package Pages;

import Data_Classes.Booking;
import Data_Classes.Cab_package.Cab;
import Data_Classes.DefaultException;
import Data_Classes.Route;
import Data_Classes.User_package.Authentication;
import Data_Classes.User_package.User;
import utils_package.App;
import utils_package.Notification;
import utils_package.SystemUtils;

import java.io.IOException;
import java.text.*;
import java.time.LocalTime;
import java.util.*;

import static Pages.BookingPage.updateBookingInFile;

public class Transaction implements Page{

    public static ArrayList<Integer>BookedCabs = new ArrayList<>();
    public static int selectedRouteId;

    public static boolean onTransactionpg = true;
    public static Date selectedDate = null;
    public static LocalTime selectedTime = null;
    public static void display(){
        System.out.println("Please enter a number to access a functionality. It is necessary to update all, but in any order:");
        System.out.println("3--Book a cab.");
        System.out.println("4--View all routes.");
        System.out.println("5--Add a route.");
//        System.out.println("6--Delete a route.");
        System.out.println("7--View all cabs.");
        System.out.println("8--Add a cab.");
//        System.out.println("9--Delete a cab.");
        System.out.println("10--Go Back.");
        System.out.println("11--Exit Application.");
    }

    public static void makeBooking() throws DefaultException, ParseException, IOException {
        selectRoute(SystemUtils.sc);
        System.out.println("Please enter the date of travel in 'DD-MM-YYYY' format");
        execute(0);
        System.out.println("Please enter the time of travel in 'hh:mm:ss' format");
        execute(1);
        System.out.println("Would you like to share a cab? y/n");
        execute(2);
    }

    public static boolean execute(int number) throws ParseException, DefaultException, IOException {

        int count = 0;
        ArrayList<Cab> userCabList = null;
        switch (number) {
            case 0:
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                selectedDate = format.parse(SystemUtils.sc.nextLine());
                break;
            case 1:
                String travelTime = SystemUtils.sc.nextLine();
                selectedTime = LocalTime.parse(travelTime);
                break;
            case 2:
                String ans = SystemUtils.sc.nextLine();
                selectCab(ans,SystemUtils.sc);
                break;
            case 3:
                makeBooking();
                break;
            case 4:
                App.viewAllRoutes();
                break;
            case 5:
                addRoute();
                break;
            case 6:
                deleteRoute();
                break;
            case 7:
                App.viewAllCabs();
                break;
            case 8:
                addCab();
                break;

            case 10: onTransactionpg = false;
            break;
            case 11: System.exit(0);
        }
        return true;
    }
    public static void run() throws DefaultException, ParseException, IOException {
        while(onTransactionpg){
            if(Authentication.isAdmin()) {
                display();
            }else{
                makeBooking();

            }if(!onTransactionpg){
                onTransactionpg = true;
                break;
            }

            int number = SystemUtils.sc.nextInt();
            SystemUtils.sc.nextLine();
            execute(number);
            if(!onTransactionpg){
                onTransactionpg = true;
                break;
            }
        }
    }

    public static int selectRoute(Scanner sc){
        boolean notselected = true;
        while(notselected){
            System.out.println("Please select Route by index of list. Indexing begins at 0.");
            System.out.println(App.AllRoutes);
            if(App.AllRoutes.isEmpty()){
                System.out.println("There are no routes available. Ask Admin to add routes.");
                return 0;
            }
            int index = sc.nextInt();
            sc.nextLine();
            if(App.AllRoutes.containsKey(index)){
                System.out.println("Route found.");
                selectedRouteId = index;
                return index;
            }else{
                System.out.println("Enter valid item index.");
            }
        }
        return 0;
    }

    public static void addBooking(int id, Booking booking) throws IOException, DefaultException, ParseException {
        try{
            App.AllBookings.put(id,booking);
            updateBookingInFile(booking, false);
        }catch (Exception e){
            System.out.println(e);
            makeBooking();
        }

    }

    public static void bookSelectedCab(int route_id, Cab cab, Date date, LocalTime time, Boolean sharing) throws IOException, DefaultException, ParseException {
        int cab_id=0;
        for(Map.Entry<Integer, Cab> map : App.AllCabs.entrySet()){
            if (map.getValue() == cab) cab_id = map.getKey();
        }
        int id = App.AllBookings.size();
        Booking userBooking = new Booking(id, route_id, cab_id, date, time, sharing);
        if(userBooking.getHost() == null) userBooking.setHost(Authentication.user);
        addBooking(userBooking.getId(),userBooking);
    }

    public static void selectCab(String ans, Scanner sc) throws DefaultException, ParseException, IOException {

        boolean sharing = false;
        boolean answered = true;
        while(answered) {
            if(ans.equals("y") || ans.equals("Y")) {sharing = true; answered = false;}
            else if(ans.equals("n") || ans.equals("N")) {sharing = false; answered = false;}
            else {
                System.out.println("Please enter only 'y' or 'n'.");
                ans = sc.nextLine();
            }
        }
        Booking booking;
        int id = App.AllBookings.size();
        int cab_id= 0;
        System.out.println("finding cab....");

        if (sharing) {
            requestSharing();
            for(Map.Entry<Integer, Cab>map: App.AllCabs.entrySet()){
                if(!BookedCabs.contains(map.getKey())){
                    cab_id = map.getKey();
                }
                else cab_id = 0;
            }
            booking = new Booking(id,selectedRouteId, cab_id, selectedDate, selectedTime, true);
            booking.setHost(Authentication.user);
            Authentication.user.addBookings(id);
            booking.addStudentIds(Authentication.user.getBITS_id());
            App.AllBookings.put(id, booking);
            updateBookingInFile(booking, false);
            System.out.println("New booking made.");
            onTransactionpg = false;
        }
        else {
            for(Map.Entry<Integer, Cab>map: App.AllCabs.entrySet()){
                if(!BookedCabs.contains(map.getKey())){
                    cab_id = map.getKey();
                }
                else{cab_id = 0;}
            }
            if(cab_id == Integer.MIN_VALUE){
                System.out.println("No non sharing cabs are available.");
                onTransactionpg = false;
                return;
            }
            booking = new Booking(id,selectedRouteId, cab_id, selectedDate, selectedTime, false);

            booking.setHost(Authentication.user);

            Authentication.user.addBookings(id);
            booking.addStudentIds(Authentication.user.getBITS_id());
            App.AllBookings.put(id, booking);
            updateBookingInFile(booking, false);
            System.out.println("New booking made.");
            onTransactionpg = false;
        }
    }

    public static void requestSharing() throws DefaultException, ParseException, IOException {
        int cap = 0;
        if(App.AllBookings.isEmpty()){
            System.out.println("No cabs to share from, booking new cab.");
           return;

        }
        for(Map.Entry<Integer, Booking>mapBooking: App.AllBookings.entrySet() ){
            int ID = mapBooking.getValue().getCab_id();
            for(Map.Entry<Integer, Cab> map : App.AllCabs.entrySet()){
                if(map.getKey() == ID){
                    cap = map.getValue().getCapacity() - mapBooking.getValue().getStudentIds().size() ;
                    if(cap>0){
                        ArrayList<String> receiversIds = new ArrayList<>();
                        Authentication.user.requestsSent.add(mapBooking.getKey());
                        for(Map.Entry<String, User> mapUser: App.AllUsers.entrySet()){
                            receiversIds.add(mapUser.getValue().getBITS_id());
                            mapUser.getValue().shareRequests.add(Authentication.user.getBITS_id());
                            System.out.println("Share request sent.");
                        }
                        Notification notif = new Notification(Authentication.user.getBITS_id(),mapBooking.getKey(),receiversIds );
                        App.updateNotification(notif, false);
//                        Authentication.user.addBookings(mapBooking.getKey());
//                        mapBooking.getValue().addStudentIds(Authentication.user.getBITS_id());
                    }else{
                        break;
                    }
                }
            }
        }
    }

    public static void addRoute() throws DefaultException, IOException {
        System.out.println("Please enter destination.");
        String destination = SystemUtils.sc.nextLine();
        System.out.println("Please enter origin.");
        String origin = SystemUtils.sc.nextLine();
        System.out.println("Please enter distance in km.");
        int distance = SystemUtils.sc.nextInt();
        System.out.println("Please enter tollCharges.");
        int tollCharges = SystemUtils.sc.nextInt();
        int i = App.AllRoutes.values().size();
        Route route = new Route( i, destination, origin, distance, tollCharges);
        int id = route.getId();
        try{
            App.adminValidator();
            App.AllRoutes.put(id, route);
            App.updateRouteInFile(route,false);
            System.out.println("Route added!");
        }catch (Exception e){
            System.out.println(e);
            display();
        }
    }
    public static void deleteRoute()throws DefaultException, IOException {
        System.out.println("Please enter the route id to be deleted.");
        int id = SystemUtils.sc.nextInt();
        SystemUtils.sc.nextLine();
        if(!App.AllRoutes.isEmpty()) {
            if (!App.AllRoutes.containsKey(id)) {
                try {
                    App.adminValidator();
                } catch (DefaultException e) {
                    System.out.println(e);
                    display();
                }
            }else if(App.AllRoutes.get(id) != null){
                Route route = App.AllRoutes.get(id);
                App.updateRouteInFile(route,true);
                App.AllRoutes.remove(id);
                System.out.println("Route with route id " + id + " deleted");
            }
        }else {
            System.out.println("There are no routes.");
        }
    }

    public static void addCab()throws DefaultException, IOException{
        System.out.println("Please enter License plate number.");
        String license_plate = SystemUtils.sc.nextLine();
        System.out.println("Please enter capacity.");
        int capacity = SystemUtils.sc.nextInt();
        int i = App.AllCabs.values().size();
        Cab cab = new Cab(i, license_plate, capacity);
        try{
            App.adminValidator();
            App.AllCabs.put(i, cab);
            App.updateCabInFile(cab,false);
            System.out.println("Cab added!");
        }catch (Exception e){
            System.out.println(e);
            display();
        }

    }
}
