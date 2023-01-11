package Pages;

import Data_Classes.Booking;
import Data_Classes.Cab_package.Cab;
import Data_Classes.DefaultException;
import Data_Classes.User_package.Authentication;
import com.sun.tools.javac.Main;
import utils_package.App;
import utils_package.Notification;
import utils_package.SystemUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BookingPage implements Page{
    public static void display(){
        System.out.println("Please select a number:");
        System.out.println("0--View my bookings.");
        System.out.println("1--Add a booking.");
        if(Authentication.isAdmin()) System.out.println("   Access routes and cabs data.");
        System.out.println("2--Delete a booking.");
        System.out.println("3--Go Back.");
        System.out.println("4--Exit Application.");
        System.out.println("7--Check Notifications.");
        if(Authentication.isAdmin()) {
            System.out.println("5--View a user's bookings.");
            System.out.println("6--View all user bookings.");
        }
    }

    public static boolean execute(int a) throws DefaultException, ParseException, IOException {
        switch(a){
            case 0:
                viewCurrentUserBooking();
                break;
            case 1:
                Transaction.run();
                break;
            case 2:
                deleteBooking();
                break;
            case 3:
                return false;
            case 4:
                System.exit(0);
            case 5:
                viewUserBookings();
                break;
            case 6:
                App.viewAllBookings();
                break;
            case 7:
                checkNotifications();
                break;
        }
        return true;
    }



    public static void run() throws DefaultException, ParseException, IOException {
        boolean inBookingPage = true;
        while(inBookingPage){
            display();
            int ans = SystemUtils.sc.nextInt();
            SystemUtils.sc.nextLine();
            inBookingPage =  execute(ans);
        }
    }

    public static void checkNotifications() throws IOException {
        for(Notification notifs: App.AllNotifications){
            if(notifs.sourceId == Authentication.user.getBITS_id()){
                System.out.println("Your pending sharing requests:");
                for(Integer i : Authentication.user.requestsSent){
                    System.out.println("booking id:"+ i);
                }checkNotifications();
            }else if(notifs.receiversId.contains(Authentication.user.getBITS_id())){
                System.out.println("Sharing requests awaiting your response:");
                for(String id: Authentication.user.shareRequests){
                    System.out.println("Enter 0 to accept, 1 to reject.");
                    System.out.println(" Request from BITS id: "+id);
                    int ans = SystemUtils.sc.nextInt();
                    SystemUtils.sc.nextLine();
                    if(ans == 0){
                        App.AllBookings.get(notifs.bookingId).addStudentIds(id);
                        App.AllUsers.get(id).addBookings(notifs.bookingId);
                        App.AllUsers.get(id).requestsSent.remove(notifs.bookingId);
                    }if(ans ==1){
                        Booking booked = App.AllBookings.get(notifs.bookingId);
                        Booking booking = new Booking(App.AllBookings.size(), booked.getRoute_id(), booked.getCab_id(), booked.getDate(), booked.getTime(),true);
                        App.AllUsers.get(notifs.sourceId).addBookings(booking.getId());
                        updateBookingInFile(booking,false);
                        App.AllNotifications.remove(notifs);
                        App.updateNotification(notifs, true);
                    }updateBookingInFile(App.AllBookings.get(notifs.bookingId), false);
                }

            }display();
        }
        System.out.println("You have no notifications.");
    }
    public static void viewCurrentUserBooking(){
        ArrayList<Integer> bookings;
        if(Authentication.user.getBookings() != null) {
            bookings = Authentication.user.getBookings();
            for (Integer id : bookings) {
                System.out.println("ID: "+ id + " Booking: " +App.AllBookings.get(id));
            }
        }else {
            System.out.println("You have no bookings.");
        }
    }
    public static void updateBookingInFile(Booking booking, boolean deleted) throws IOException {
        List<String> data = new ArrayList<>();
        data.add(Integer.toString(booking.getRoute_id()));
        data.add(Integer.toString(booking.getCab_id()));
        data.add(booking.getDate().toString());
        data.add(booking.getTime().toString());
        data.add(Boolean.toString(booking.isSharing()));
        data.add(booking.getStatus().name());
        if(booking.getStudentIds() !=null){
            List<String> studentIds = new ArrayList<>();
            for(String id : booking.getStudentIds()) {
                studentIds.add(id);
            }
            data.add(String.join(";", studentIds));
        }else data.add((new ArrayList<Integer>()).toString());
        data.add(booking.getHost().toString());                    // friendsList and bookings of user aren't present in booking data file
        data.add(Boolean.toString(booking.getPaymentStatus()));
        data.add(Boolean.toString(deleted));

        FileWriter csvWriter = new FileWriter("Data/bookings.csv", true);
        csvWriter.append(String.join(",", data));
        csvWriter.append("\n");

        csvWriter.flush();
        csvWriter.close();
    }
    public static void deleteBooking() throws DefaultException, IOException {
        System.out.println("Please enter the booking id to be deleted.");
        int id = SystemUtils.sc.nextInt();
        SystemUtils.sc.nextLine();
        if(!Authentication.user.getBookings().isEmpty()) {
            if (!Authentication.user.getBookings().contains(App.AllBookings.get(id))) {
                try {
                    App.adminValidator();
                } catch (DefaultException e) {
                    System.out.println(e);
                    display();
                }
            } else {
                Authentication.user.getBookings().remove(App.AllBookings.get(id));
            }
            updateBookingInFile(App.AllBookings.get(id), true);
            App.AllBookings.remove(id);
        }else {
            System.out.println("You have no bookings.");
        }

    }

    public static void viewUserBookings() throws DefaultException, ParseException, IOException {
        System.out.println("Please enter user's BITS ID.");
        String id = SystemUtils.sc.nextLine();
        if(App.AllUsers.get(id) == null){
            System.out.println("User doesn't exist");
            run();
            try{
                App.adminValidator();
                if(App.AllUsers.get(id).getBookings() != null || !App.AllUsers.get(id).getBookings().isEmpty() )
                    System.out.println(App.AllUsers.get(id).getBookings());
                else System.out.println("The user has no bookings.");
            }catch (Exception e){
                System.out.println(e);
                run();
            }
        }


    }

}
