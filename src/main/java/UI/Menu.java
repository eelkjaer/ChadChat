package UI;


import java.util.Scanner;



public class Menu {

    Scanner scan = new Scanner(System.in);
    Scanner nameScan = new Scanner(System.in);

    private void menu() {
        System.out.println(" [1] to login\n " + "[2] to create new user. ");
        try {
            int input = scan.nextInt();
            switch (input) {
                case 1:
                    System.out.println("Login");
                    //login(User user)
                    break;
                case 2:
                    createUser();
                    break;
                default:
                    System.out.println("Press 1 or 2.");
                    menu();

            }
        } catch (Exception e) {
            System.out.println("Press 1 or 2. ");

        }
    }

    private void createUser() {
        System.out.println("Enter your name: ");
        String userName = nameScan.nextLine();

        System.out.println("Welcome: " + userName);


    }


}

