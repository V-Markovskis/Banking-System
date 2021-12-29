package banking.menu;


import java.util.Scanner;

// Все меню созданы в отдельные классы. Так их легче понять что каждое из них делает и тестируются они тоже проще.
// Все классы меню малозависимы друг от друга, но пользуются общим сервисом - CardService
public class StartMenu {
    private static final Scanner sc = new Scanner(System.in);

    public void render() {
        String input;
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("1. Create an account\n2. Log into account\n0. Exit");
            input = sc.next();

            switch (input) {
                case "0":
                    isRunning = false;
                    break;
                case "1":
                    isRunning = new CreateAccountMenu().render();
                    break;
                case "2":
                    isRunning = new LoginMenu().render();
                    break;
                default:
                    System.out.println("\nIncorrect command!\n");
            }
        }

        System.out.println("\nBye!");
    }
}
