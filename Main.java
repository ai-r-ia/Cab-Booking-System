import Data_Classes.Booking;
import Pages.*;
import utils_package.*;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Map;

import static java.time.temporal.ChronoUnit.MINUTES;

public class Main implements Page {
    public static void display(){
        System.out.println("Please select a number: ");
        System.out.println("0--Login");
        System.out.println("1--Register");
        System.out.println("2--Exit");
    }
    public static boolean execute(int a) throws Exception {
        switch (a){
            case 0:
                Login.run();
                break;
            case 1:
                Register.run();
                break;
            case 2:
                return false;
        }
        return true;
    }
    public  static void run() throws Exception {
        boolean systemOn = true;
        while (systemOn){
            display();
            int auth = SystemUtils.sc.nextInt();
            SystemUtils.sc.nextLine();
            systemOn = execute(auth);
        }
    }
    public static void main(String[] args) throws Exception {
        LoadUserData userThread = new LoadUserData();
        userThread.start();
        LoadRouteData routeThread = new LoadRouteData();
        routeThread.start();
        LoadCabData cabThread = new LoadCabData();
        cabThread.start();
        LoadBookingData bookingThread = new LoadBookingData();
        bookingThread.start();
        LoadNotifications notifThread = new LoadNotifications();
        notifThread.start();

        userThread.join();
        routeThread.join();
        cabThread.join();
        bookingThread.join();
        notifThread.join();

        checkNotifs();
        run();
    }

    public static void checkNotifs() throws IOException {
        for(Notification notifs : App.AllNotifications){
            for(Map.Entry<Integer, Booking> map: App.AllBookings.entrySet()){
                if(notifs.bookingId == map.getKey()){
                    LocalTime bookingTime = map.getValue().getTime();
                    long diff = MINUTES.between(LocalTime.now(), bookingTime);
                    if (diff < 0) {
                        diff += 1440;
                    }
                    if(diff>30){
                        App.AllNotifications.remove(notifs);
                        App.updateNotification(notifs,true);
                        Booking booked = App.AllBookings.get(notifs.bookingId);
                        Booking booking = new Booking(App.AllBookings.size(), booked.getRoute_id(), booked.getCab_id(), booked.getDate(), booked.getTime(),true);
                        App.AllUsers.get(notifs.sourceId).addBookings(booking.getId());
                        BookingPage.updateBookingInFile(booking,false);
                    }
                }
            }
        }
    }
}
