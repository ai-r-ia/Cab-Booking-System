package utils_package;


import Data_Classes.Cab_package.Cab;
import Data_Classes.Cab_package.Driver;

import java.io.*;
import java.util.ArrayList;

public class LoadCabData extends Thread {
    Thread cabthread;

    @Override
    public void run() {
        File csvFile = new File("Data/cabs.csv");
        if (csvFile.isFile()) {
            try{
            BufferedReader csvReader = new BufferedReader(new FileReader("Data/cabs.csv"));
            String row;
            int i =App.AllCabs.values().size();
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                Cab addingCab = new Cab(i,data[0],Integer.parseInt(data[1]));
                if (data[2].equals("[]")) addingCab.setRoute_ids(new ArrayList<>());
                else {
                    ArrayList<Integer> arr = new ArrayList<>();
                    String[] str = data[2].split(";");
                    for (String s : str) {
                        arr.add(Integer.parseInt(s.trim()));
                    }
                    addingCab.setRoute_ids(arr);
                }
                if (data[3].equals("[]")) addingCab.setRoute_ids(new ArrayList<>());
                else {
                    ArrayList<Integer> arr = new ArrayList<>();
                    String[] str = data[3].split(";");
                    String name = str[0];
                    long contact =Long.parseLong(str[1]);
                    addingCab.setDriver(new Driver(name, contact));
                }

                App.AllCabs.put(i, addingCab);
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
        System.out.println("Cab file data loading....");
        if (cabthread == null) {
            cabthread = new Thread(this);
            cabthread.start();
        }
    }
}


//    public static void loadCabDataFromFile() throws IOException {
//        File csvFile = new File("cabs.csv");
//        if (csvFile.isFile()) {
//            BufferedReader csvReader = new BufferedReader(new FileReader("cabs.csv"));
//            String row;
//            int i =0;
//            while ((row = csvReader.readLine()) != null) {
//                String[] data = row.split(",");
//                Cab addingCab = new Cab(data[0],Integer.parseInt(data[1]),Boolean.parseBoolean(data[2]));
//                AllCabs.put(i, addingCab);
//                i++;
//            }
//            csvReader.close();
//        }
//        //routeids CabStatus, Driver.
//    }