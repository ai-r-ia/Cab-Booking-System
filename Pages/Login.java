package Pages;

import Data_Classes.DefaultException;
import Data_Classes.User_package.Authentication;
import utils_package.App;
import utils_package.SystemUtils;

import java.io.IOException;
import java.text.ParseException;

public class Login implements Page{
    public static void display() throws DefaultException {
        System.out.println("Please enter Username.");
        String name = SystemUtils.sc.nextLine();
        System.out.println("Please enter Password.");
        String password = SystemUtils.sc.nextLine();
        App.login(name, password);
    }

    public static void run() throws DefaultException, ParseException, IOException {
        int i = 3;
        while(true){
            try{
                display();
                i = 3;
                break;
             }catch (DefaultException  e) {
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