package user_app;

import gui.LoginScreen;
import gui.MainScreenUser;

public class Main {

    public static void main(String[] args) {

        //MainScreenUser mainScreen = new MainScreenUser();
        //mainScreen.setVisible(true);
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.setVisible(true);
        System.out.println("Hello, UserApp!");
    }
}
