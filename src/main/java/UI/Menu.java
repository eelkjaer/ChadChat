package UI;

import java.util.Scanner;

public class Menu {
    private final Beautifier beauty = new Beautifier();
    private void menu() {
        String[] menuItems = new String[]{"Login","Register"}; //Array med menupunkter.
        beauty.printMessage(beauty.generateMenuStr(menuItems),false); //Printer menuen.

        try {
            int input = beauty.getIntInput();
            switch (input) {
                case 1:
                    beauty.printMessage(menuItems[input-1] + " Selected", true);
                    //TODO: User login
                    break;
                case 2:
                    beauty.printMessage(menuItems[input-1] + " Selected", true);
                    createUser();
                    break;
                default:
                    beauty.printMessage("Select a valid menu item!", true);
                    menu();

            }
        } catch (Exception e) {
            beauty.printMessage(e.getMessage(), true);

        }
    }

    private void createUser() {
        beauty.printMessage("Enter your name: ", false);
        String userName = beauty.getStrInput();

        beauty.printMessage("Welcome " + userName, true);

    }
}

//@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
class Beautifier{
    private final Scanner input = new Scanner(System.in);

    /**
     * @param str String to be printed in console.
     * @param linebreak Print as new line or not.
     */
    public void printMessage(String str, boolean linebreak){
        if(linebreak){
            System.out.println(str);
        } else {
            System.out.print(str);
        }
    }

    /**
     * @param menuItems Menu items to be printed. String Array required.
     * @return Nicely formatted string ready to print.
     */
    public String generateMenuStr(String[] menuItems){
        String str = "";
        int i = 1;
        str += "#####################\n"; //Start of Menu divider
        for(String s: menuItems){
            i++;
            str += "\t[" + i + "] " + s + "\n";
        }
        str += "#####################\n"; //End of Menu divider

        return str;
    }

    /**
     * @return String from console input.
     */
    public String getStrInput(){
        String str = input.nextLine();
        if(str.isEmpty() || str.isBlank()){
            printMessage("Dit input er tomt!", true);
            getStrInput();
        }
        return str;
    }

    /**
     * @return Integer from console input.
     */
    public int getIntInput(){
        String str;
        while(!input.hasNextInt()){ //True if input is not a valid integer.
            printMessage("No valid integer provided!", true);
            input.next(); //Try again.
        }
        str = input.nextLine();
        if(str.isEmpty()){ //If input is empty/blank/null
            printMessage("\nShouldn't be empty! Try again: ", true);
            str = input.nextLine(); //Try again.
        }

        return Integer.parseInt(str.trim());
    }

    /**
     * @return Double from console input.
     */
    public double getDoubleInput(){
        String str;
        while(!input.hasNextDouble()){ //True if input is not a valid double.
            printMessage("No valid double provided!",true);
            input.next(); //Try again.
        }
        str = input.nextLine();
        if(str.isEmpty()){ //If input is empty/blank/null
            printMessage("\nShouldn't be empty! Try again: ", true);
            str = input.nextLine(); //Try again.
        }

        return Double.parseDouble(str.trim());
    }



}

