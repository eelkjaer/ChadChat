package chadchat.UI;

import chadchat.domain.User.User;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
public class Beautifier{
    private final Scanner input = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));

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
        StringBuilder str = new StringBuilder();
        int i = 1;
        str.append("\n##########################\n"); //Start of Menu divider
        str.append("# Please select a option #\n"); //Start of Menu divider
        str.append("##########################\n"); //Start of Menu divider
        for(String s: menuItems){
            i++;
            str.append("\t[").append(i - 1).append("] ").append(s).append("\n");
        }
        str.append("##########################\n"); //End of Menu divider

        return str.toString();
    }
    
    public String generateCmdMenuStr(HashMap<String,String> menuItems){
        StringBuilder str = new StringBuilder();
        str.append("##########################\n"); //Start of Menu divider
        for(Map.Entry<String, String> menu: menuItems.entrySet()){
            str.append("\"!").append(menu.getKey()).append("\" \t").append(menu.getValue()).append(" \n");
        }
        str.append("##########################\n"); //End of Menu divider
        
        return str.toString();
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
    
    public String chatMsg(String msg){
        return msg;
    }
    
    public String chatMsg(User user, String msg){
        return user.getUserName() + " said: " + msg;
    }



}
