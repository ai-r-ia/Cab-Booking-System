package utils_package;
import Data_Classes.*;
import Data_Classes.User_package.User;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class LoadBookingData extends Thread {
    Thread cabthread;
    ArrayList<Booking> allBookings = new ArrayList<>();

    @Override
    public void run() {
        File csvFile = new File("Data/bookings.csv");
        if (csvFile.isFile()) {
            try {
                BufferedReader csvReader = new BufferedReader(new FileReader("Data/bookings.csv"));
                String row;
                int i = App.AllBookings.size();
                while ((row = csvReader.readLine()) != null) {
                    String[] data = row.split(",");
                    SimpleDateFormat format = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
                    Date date;
                    LocalTime time;
                    try {
                        date = format.parse(data[2]);
                        time = LocalTime.parse(data[3]);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    Booking addingBooking = new Booking(i, Integer.parseInt(data[0]), Integer.parseInt(data[1]), date, time, Boolean.parseBoolean(data[4]));
                    if (data[6].equals("[]")) addingBooking.setStudentIds(new ArrayList<>());
                    else {
                        ArrayList<String> arr = new ArrayList<>();
                        String[] str = data[6].split(";");
                        for (String s : str) {
                            arr.add(s);
                        }
                        addingBooking.setStudentIds(arr);
                    }
                    addingBooking.setStatus(BookingStatus.valueOf(data[5]));
                    addingBooking.setHost(User.parseUser(data[7]));
                    addingBooking.setPaymentStatus(Boolean.parseBoolean(data[8]));
                    if(data[9].equals("false"))
                    App.AllBookings.put(i, addingBooking);
                    i++;
                }
                csvReader.close();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Thread has been interrupted");
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public void start() {
        System.out.println("Booking file data loading....");
        if (cabthread == null) {
            cabthread = new Thread(this);
            cabthread.start();
        }
    }
}




