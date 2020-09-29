package chadchat.UI;

import chadchat.entries.Server;

import java.io.PrintWriter;
import java.util.Scanner;

public class Menu extends Server {
    
    private final Beautifier beauty = new Beautifier();
    
    public Menu(Scanner in, PrintWriter out) {
        super(in, out);
    }
    
    public void showMenu() {
        String[] menuItems = new String[]{"Login","Register"}; //Array med menupunkter.
        out.print(beauty.generateMenuStr(menuItems)); //Printer menuen.
        out.flush();

        try {
            out.print("> ");
            out.flush();
            int input = Integer.parseInt(in.nextLine());
            switch (input) {
                case 0:
                    super.running = false;
                    out.println("Goodbye! \uD83D\uDE00");
                    out.flush();
                    super.quit();
                case 1:
                    out.println(menuItems[input-1] + " Selected");
                    out.flush();
                    //TODO: User login
                    break;
                case 2:
                    out.println(menuItems[input-1] + " Selected");
                    out.flush();
                    createUser();
                    break;
                default:
                    out.println(menuItems[input-1] + " Selected");
                    out.flush();
                    showMenu();

            }
        } catch (Exception e) {
            out.println(e.getMessage());
            out.flush();

        }
    }

    private void createUser() {
        out.print("Enter your name: ");
        out.flush();
        String userName = in.nextLine();

        out.println("Welcome, " + userName);

    }
}

