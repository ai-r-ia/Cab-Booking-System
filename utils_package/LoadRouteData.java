package utils_package;
import Data_Classes.*;

import java.io.*;
import java.util.Map;

public class LoadRouteData extends Thread {
    Thread thread;

    @Override
    public void run() {
        File csvFile = new File("Data/routes.csv");
        if (csvFile.isFile()) {
            try {
                BufferedReader csvReader = new BufferedReader(new FileReader("Data/routes.csv"));
                String row;
                int i = App.AllRoutes.values().size();
                while ((row = csvReader.readLine()) != null) {
                    String[] data = row.split(",");
                    Route addingRoute = new Route(i, data[0],data[1], Integer.parseInt(data[2]),Integer.parseInt(data[3]));
                    if(data[4].equals("false")){
                        App.AllRoutes.put(i, addingRoute);
                    }
                    else if(data[4].equals("true"))
                    {
                        //                    System.out.println("removing deleted");
                        App.AllRoutes.remove(addingRoute);
//                        System.out.println("first remove");
                        for(Map.Entry<Integer, Route>map: App.AllRoutes.entrySet()){
                            if(map.getValue() == addingRoute){
                                App.AllRoutes.remove(map.getKey());
//                                System.out.println("deleted");
                            }
                        }

                    }
//                    for(Map.Entry<Integer, Route>map: App.AllRoutes.entrySet())
//                        System.out.println(map.getValue().toString());
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
        System.out.println("Routes file data loading....");
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }
}

