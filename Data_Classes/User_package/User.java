package Data_Classes.User_package;
import java.util.*;
public class User {

    protected int id;
    private static int numberOfUsers = 0;
    protected String name;

    protected String password;
    protected String BITS_id;
    protected String email;
    protected long mobileNumber;
    protected ArrayList<Integer> friendsList;
    protected ArrayList<Integer> bookings;

    public ArrayList<String> shareRequests = new ArrayList<>();

    public ArrayList<Integer> requestsSent = new ArrayList<>();


    public User( String name, String BITS_id, String email, long mobileNumber, String password) {

        this.id = ++numberOfUsers;
        this.name = name;
        this.BITS_id = BITS_id;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.password = password;
    }

    public static int getNumberOfUsers() {
        return numberOfUsers;
    }

    public void addFriend(int id){
        friendsList.add(id);
    }

    public void addBookings(int booking_id){
        bookings.add(booking_id);
    }

    private void cancelBooking(int booking_id){
        bookings.remove(booking_id);
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<String> getShareRequests(){
        return this.shareRequests;
    }
    public String getPassword(){return this.password;}

    public String getBITS_id(){
        return this.BITS_id;
    }

    public String getEmail(){
        return this.email;
    }

    public long getMobileNum(){
        return  this.mobileNumber;
    }

    public ArrayList<Integer> getFriends(){
        return this.friendsList;
    }

    public ArrayList<Integer> getBookings(){
        return this.bookings;
    }

    public void setFriendsList(ArrayList<Integer> list){ this.friendsList = list; }

    public void setBookings(ArrayList<Integer> list){ this.bookings = list; }
    public void setPassword(String newPassword){ this.password = newPassword;}
    public String toString(){
        return this.name + ";" + this.BITS_id + ";" + this.email + ";" + this.mobileNumber + ";" + this.password ;
    }
    public static User parseUser(String row){
        String[] data = row.split(";");
//        for(String s: data)
//            System.out.println(s);
        User user = new User(data[0], data[1], data[2], Long.parseLong(data[3]), data[4]);
        return user;
    }
}






