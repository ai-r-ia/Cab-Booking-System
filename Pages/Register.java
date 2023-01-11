package Pages;

import utils_package.App;
import utils_package.SystemUtils;

import java.io.IOException;
import java.util.InputMismatchException;

public class Register implements Page{

    public static void display() throws IOException {
        System.out.println("Please enter Username.");
        String name2 = SystemUtils.sc.nextLine();
        System.out.println("Please enter Password.");
        String password2 = SystemUtils.sc.nextLine();
        System.out.println("Please enter BITS ID.");
        String BITS_ID = SystemUtils.sc.nextLine();
        if(App.AllUsers.containsKey(BITS_ID)) {System.out.println("This BITS ID is already registered."); display();}
        System.out.println("Please enter email address.");
        String email = SystemUtils.sc.nextLine();
        System.out.println("Please enter mobile number.");
        long mobileNum = 0;
        try{
            mobileNum = SystemUtils.sc.nextLong();

        }catch (InputMismatchException e){
            System.out.println(e);
            SystemUtils.sc.nextLine();
            display();

        }
        SystemUtils.sc.nextLine();
        System.out.println("Do you want to register as an Admin?true/false");
        boolean asAdmin = SystemUtils.sc.nextBoolean();
        App.register(name2, BITS_ID, email, mobileNum, password2, asAdmin);
    }
    public static void run() throws Exception {
        int i = 3;
        while(true){
            try {
                display();
                i = 3;
                break;
            }catch(IOException e){
                System.out.println(e);
                System.out.println("Enter 0 to go back, 1 to try again.");
                i = SystemUtils.sc.nextInt();
                if (i == 1) continue;
                else if (i == 0) break;
            }
        }
        if(i==3){
            BookingPage.run();
        }

    }
}
