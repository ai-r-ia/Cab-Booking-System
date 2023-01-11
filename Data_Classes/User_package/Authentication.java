package Data_Classes.User_package;

import Data_Classes.DefaultException;

import java.io.IOException;
import java.util.Map;

public class Authentication{
    public static User user;
    public static boolean isLoggedin = false;
    public static boolean isAdmin() { return user instanceof Admin;};


}