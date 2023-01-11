package utils_package;

import Data_Classes.*;
import Data_Classes.Cab_package.*;
import Data_Classes.User_package.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

public class App{
    public Scanner scanner;

    public static HashMap<Integer, Route> AllRoutes = new HashMap<Integer, Route>();
    public static HashMap<Integer, Booking> AllBookings = new HashMap<Integer, Booking>();
    public static HashMap<String, User> AllUsers = new HashMap<String, User>();
    public static HashMap<Integer, Cab> AllCabs = new HashMap<Integer, Cab>();
    public static HashMap<Integer, Cab> AllCabsInUse = new HashMap<Integer, Cab>();
    public static HashMap<Integer, Cab> AllCabsAvailable = new HashMap<Integer, Cab>();
    public static ArrayList<Notification> AllNotifications = new ArrayList<>();

    public static void adminValidator() throws DefaultException {
        if(Authentication.user instanceof Admin) {
            return;
        }else {
            throw new DefaultException("Error: Unauthorized user.");
        }
    }

    public static void addAccount(String BITS_id, User user, Boolean admin)throws IOException{
        AllUsers.put(BITS_id, user);
        updateUserInFile(user,admin);
    }

    public static void viewAllBookings()throws DefaultException{
        adminValidator();
        if( !App.AllBookings.isEmpty())
            for(Map.Entry<Integer, Booking>map: App.AllBookings.entrySet())
                System.out.println(map.getValue());
//        System.out.println(App.AllBookings);
        else System.out.println("There are no bookings.");
    }
    public static void viewAllRoutes()throws DefaultException{
        adminValidator();
        if( !App.AllRoutes.isEmpty())
            for(Map.Entry<Integer, Route>map: App.AllRoutes.entrySet())
            System.out.println(map.getValue().toString());
        else System.out.println("There are no routes.");
    }
    public static void viewAllCabs()throws DefaultException{
        adminValidator();
//        System.out.println(App.AllCabs);
        if( !App.AllCabs.isEmpty())
            for(Map.Entry<Integer, Cab>map: App.AllCabs.entrySet())
                System.out.println(map.getValue().toString());
        else System.out.println("There are no cabs.");
    }

    public static Driver defaultDriver = new Driver("Driver1", 12345678);
    public static void updateCabInFile(Cab cab, boolean deleted) throws IOException {
        List<String> cabData = new ArrayList<>();
        cabData.add(Integer.toString(cab.getCostPerKm()));
        cabData.add(cab.getLicense_plate());
        if(cab.getRouteIds() != null){
            List<String> routeIds = new ArrayList<>();
            for(Integer id : cab.getRouteIds()) {
                routeIds.add(id.toString());
            }
            cabData.add(String.join(";", routeIds));
        }
        if(cab.getDriver() != null)
        cabData.add(cab.getDriver().toString());
        else cabData.add(defaultDriver.toString());
        cabData.add(Boolean.toString(deleted));

        FileWriter csvWriter = new FileWriter("Data/cabs.csv", true);
        csvWriter.append(String.join(",", cabData));
        csvWriter.append("\n");

        csvWriter.flush();
        csvWriter.close();
    }

    public static void updateNotification(Notification notification, boolean deleted) throws IOException {
        List<String> data = new ArrayList<>();
        data.add(notification.sourceId);
        data.add(String.valueOf(notification.bookingId));
        List<String> receivers = new ArrayList<>();
        if(notification.receiversId != null){
            for(String id : notification.receiversId) {
                receivers.add(id.toString());
            }
            data.add(String.join(";", receivers));
        }else data.add(new ArrayList<Integer>().toString());
        data.add(Boolean.toString(deleted));

        FileWriter csvWriter = new FileWriter("Data/notifs.csv", true);
        csvWriter.append(String.join(",", data));
        csvWriter.append("\n");
        System.out.println(data);
        csvWriter.flush();
        csvWriter.close();
    }

    public static void updateUserInFile(User user, boolean deleted) throws IOException {
        List<String> data = new ArrayList<>();
        data.add(user.getName());
        data.add(user.getBITS_id());
        data.add(user.getEmail());
        data.add(Long.toString(user.getMobileNum()));
        data.add(user.getPassword());
        List<String> friends = new ArrayList<>();
        if(user.getFriends() != null){
            for(Integer id : user.getFriends()) {
                friends.add(id.toString());
            }
            data.add(String.join(";", friends));
        }else data.add(new ArrayList<Integer>().toString());
        if(user.getBookings() != null){
            List<String> bookings = new ArrayList<>();
            for(Integer id : user.getBookings()) {
                bookings.add(id.toString());
            }
            data.add(String.join(";", bookings));
        }else data.add(new ArrayList<Integer>().toString());
        data.add(Boolean.toString(deleted));

        FileWriter csvWriter = new FileWriter("Data/users.csv", true);
        csvWriter.append(String.join(",", data));
        csvWriter.append("\n");
//        System.out.println(data);
        csvWriter.flush();
        csvWriter.close();
    }
    public static void updateRouteInFile(Route route, boolean deleted) throws IOException {
        List<String> data = new ArrayList<>();
        data.add(route.getDestination());
        data.add(route.getOrigin());
        data.add(Integer.toString(route.getDistance()));
        data.add(Integer.toString(route.getTollCharges()));
        data.add(Boolean.toString(deleted));

        FileWriter csvWriter = new FileWriter("Data/routes.csv", true);
        csvWriter.append(String.join(",", data));
        csvWriter.append("\n");

        csvWriter.flush();
        csvWriter.close();
    }

    public static void register(String name, String BITS_id, String email, long mobileNumber, String password, boolean adminLogin) throws IOException {
        Authentication.user =  adminLogin? new Admin(name, BITS_id, email, mobileNumber,password): new User(name, BITS_id, email, mobileNumber, password);
        App.addAccount(BITS_id, Authentication.user, adminLogin);
    }
    public static void login(String name, String password)throws DefaultException {
        for (Map.Entry<String, User> map : App.AllUsers.entrySet()) {
            if (map.getValue().getName().equals(name)) {
                String id = map.getValue().getBITS_id();
                if(App.AllUsers.get(id).getPassword().equals(password)){
                    Authentication.isLoggedin = true;
                    Authentication.user = App.AllUsers.get(id);
                    System.out.println("Logged in!");
                    return;
                }else throw new DefaultException("Incorrect password.");
            }
        }System.out.println("User does not exist. Please register or try again.");
    }


}

