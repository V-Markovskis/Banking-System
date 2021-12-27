package banking.menu;

import java.util.Scanner;

public class LoggedInMenu {
    private static final Scanner sc = new Scanner(System.in);

    private boolean render() {
        boolean isLoggedIn = true;

        while (isLoggedIn) {
            System.out.println("1. Create an account\n2. Log into account\n0. Exit");
            String input = sc.next();

            switch (input) {
                case "0":
                    isLoggedIn = false;
                    break;
                case "1":
//                    createAnAccount();
                    break;
                case "2":
//                    isLoggedIn = login();
                    break;
                default:
                    System.out.println("\nIncorrect command!\n");
            }
        }

        System.out.println("\nBye!");
        return true;
    }
}
