package utils_package;


import Data_Classes.Cab_package.Cab;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class LoadNotifications extends Thread {
    Thread thread;

    @Override
    public void run() {
        File csvFile = new File("Data/notifs.csv");
        if (csvFile.isFile()) {
            try{
                BufferedReader csvReader = new BufferedReader(new FileReader("Data/notifs.csv"));
                String row;
                int i =App.AllCabs.values().size();
                while ((row = csvReader.readLine()) != null) {
                    String[] data = row.split(",");
                    ArrayList<String> receivers;
                    if (data[2].equals("[]")) receivers = (new ArrayList<>());
                    else {
                        ArrayList<String> arr = new ArrayList<>();
                        String[] str = data[2].split(";");
                        for (String s : str) {
                            arr.add((s.trim()));
                        }
                        receivers =  arr;
                    }
                    Notification addingNotif = new Notification(data[0],Integer.parseInt(data[1]),receivers );
                    App.AllNotifications.add(addingNotif);
                    i++;
                }
                csvReader.close();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Thread has been interrupted");
                }
            }
            catch(FileNotFoundException e){
                throw new RuntimeException(e);
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }
        //routeids CabStatus, Driver.
    }
    public void start() {
        System.out.println("Notifications file data loading....");
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }
}