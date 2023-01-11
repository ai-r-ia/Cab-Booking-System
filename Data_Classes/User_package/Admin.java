package Data_Classes.User_package;

public class Admin extends User{
    protected int adminId;
    private static int numberOfAdmins=0;

    public Admin(String name, String BITS_id, String email, long mobileNumber, String password) {
        super(name, BITS_id, email, mobileNumber, password);
        this.id = ++numberOfAdmins;

    }
}


