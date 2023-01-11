package utils_package;



import Data_Classes.User_package.Admin;
import Data_Classes.User_package.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoadUserData extends Thread {
    Thread userthread;

    @Override
    public void run() {
        File csvFile = new File("Data/users.csv");
        if (csvFile.isFile()) {
            try {
                BufferedReader csvReader = new BufferedReader(new FileReader("Data/users.csv"));
                String row;
                int i = 0;
                while ((row = csvReader.readLine()) != null) {
                    String[] data = row.split(",");
                    User addingUser;
                    if(data[7].equals("false")){
                        addingUser = new User(data[0], data[1], data[2], Long.parseLong(data[3]), data[4]);
                    }else {
                        addingUser = new Admin(data[0], data[1], data[2], Long.parseLong(data[3]), data[4]);
                    }
                    if (data[5].equals("[]")) addingUser.setFriendsList(new ArrayList<>());
                    else {
                        ArrayList<Integer> arr = new ArrayList<>();
                        String[] str = data[5].split(";");
                        for (String s : str) {
                            arr.add(Integer.parseInt(s));
                        }
                        addingUser.setFriendsList(arr);
                    }
                    if (data[6].equals("[]")) addingUser.setBookings(new ArrayList<>());
                    else {
                        ArrayList<Integer> arr = new ArrayList<>();
                        String[] str = data[5].split(";");
                        for (String s : str) {
                            arr.add(Integer.parseInt(s));
                        }
                        addingUser.setBookings(arr);
                    }
                    App.AllUsers.put(data[1], addingUser);
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
    }
    public void start() {
        System.out.println("User file data loading....");
        if (userthread == null) {
            userthread = new Thread(this);
            userthread.start();
        }
    }
}


