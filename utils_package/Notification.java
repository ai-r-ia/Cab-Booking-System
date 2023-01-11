package utils_package;

import java.util.ArrayList;

public class Notification {
    public  String sourceId;
    public  int bookingId;
    public  ArrayList<String> receiversId;

    public Notification(String sourceId, int bookingId, ArrayList<String> receiversId){
        this.sourceId = sourceId;
        this.bookingId = bookingId;
        this.receiversId = receiversId;
    }

}
