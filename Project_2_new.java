/*
Author: Kevin Porras
*/

import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
public class Project_2_new
{

    // declaration of a global array variable
    static String[][] stockArray={{"VGA-converter", "5"},{"Camera", "5"}, {"HDMI-converter","5"}, {"Point-Laser", "5"}, {"Laptop", "5"}, {"Microphone", "5"}, {"Headphone","5"}};

	public static void main(String[] args) {

	    // initialize the Scanner variable
	    Scanner reader = new Scanner(System.in);
	    String command;
	    String userInput;
	    String[] inputArgs;
	    int rows = 10;
	    int cols = 8;
	    String[][] loanArray = new String[rows][cols];
	    initializeLoanArray(loanArray, rows, cols);

	    while(true){
	        System.out.println("\n**********************\nEnter a command:");
	        userInput = reader.nextLine();
	        if (userInput.toLowerCase().equals("quit")) {
	            break;
	        }
	        inputArgs = userInput.split(" ");
	        command = inputArgs[0];
	        if(command.equals("MakeALoan")){
                makeALoan(inputArgs, loanArray);
	        }
            else if(command.equals("ReturnAnItem")){
                returnAnItem(inputArgs, loanArray);
	        }
            else if(command.equals("DisplayLoans")){
                display2DArray(loanArray, "Loans");
	        }
            else if(command.equals("DisplayItems")) {
                display2DArray(stockArray, "Items");
	        }
            else if(command.equals("ExportLoans")){
                ExportLoans(loanArray);
            }
            else{ 
                System.out.println("wrong command, try again!");
            }


	    }
        reader.close();

	}
	//---------------------MakeAnLoan---------------------------------------------
	public static String[][] makeALoan(String[] inputArgs, String[][] loanArray){
        String availableAmount; //number of items in stock
        String itemId; // item's id like CSC-5 for camera
        String dateOut;
        String rightAssosciation;
        String note;
        int indexItem;
        int indexNextLoan=0;

        Scanner input = new Scanner(System.in);
        String customerId= inputArgs[1];

        if(isCustomerIdValid(customerId) == false){ //Validating customer ID
            System.out.println("Unsuccesful, ID is invalid");
            return loanArray;
        }
        if(isAlphabetic(inputArgs[2])== false){ //Validating Firstname
            System.out.println("Unsuccesful, "+ inputArgs[2]+" is an invaild argument.");
            return loanArray;
        }
        if(isAlphabetic(inputArgs[3])==false){ // Validating Lastname
            System.out.println("Unsuccesful, "+ inputArgs[3]+" is an invaild argument.");
            return loanArray;
        }
        if(isValidItem(inputArgs[4], 0, false) == false){
            System.out.println("Error, that is an invalid item");
            return loanArray;
        }

        indexItem  = indexItemInStock(inputArgs[4]);
        if(indexItem>=0){
            availableAmount = stockArray[indexItem][1];
            itemId = createItemId(inputArgs[4], availableAmount);

            if(availableAmount.equals("0")){
                System.out.print("Unsucess. That Item is out of stock");
                return loanArray;
            }
            else{

            // Prompt the user to enter the date-out
            while(true){
                System.out.println("Enter the date-out (MM-DD-YYYY): ");
                dateOut = input.nextLine();
                System.out.println("Is the date below correct (Y/N)? ");
                rightAssosciation = input.nextLine();
                if(rightAssosciation.equalsIgnoreCase("y")){
                    break;
                }
            }
            // Prompt user for note

            System.out.println("Enter a note, if none enter (none): ");
            note = input.nextLine();
            
            // get index to store the following loan
            indexNextLoan = findNextPosition(loanArray);
            }
            if(indexNextLoan ==-1){
                System.out.println("Unsuccess. The loanArray is full");

            }
            else{
            // storing the info into the 2D array: loanArray
            //Customerâ€™s first name	Customerâ€™s last name	Customerâ€™s id	Item id	Item's name	Date-out	Date-in	  Note
            loanArray[indexNextLoan][0] = inputArgs[2];
            loanArray[indexNextLoan][1] = inputArgs[3];
            loanArray[indexNextLoan][2] = inputArgs[1];
            loanArray[indexNextLoan][3] = itemId;
            loanArray[indexNextLoan][4] = inputArgs[4];
            loanArray[indexNextLoan][5] = dateOut;
            loanArray[indexNextLoan][7] = note;

            int x =  Integer.parseInt(availableAmount);
            x--;
            String temp = Integer.toString(x);
            stockArray[indexItem][1] = temp;

            System.out.println("");
            System.out.println("Success! Your Item Number is: "+ itemId+ " Please write this down as you will you use it to return the Item");

            }


        }
        //System.out.println("success!");
        return loanArray;
    }


	//--------------------return---------------------------------------------

  // Make an loan with the given arguments: customer's id, firstame, lastname, and the item's name.
  //If loan is successfully created, return 0. If the loan is not created sucessfully, return 1.
   public static String[][] returnAnItem(String[] inputArgs, String[][] loanArray){

        String dateIn;
        String rightAssosciation;
        String note;
        String itemName ="";
        String temp = "";
        int x;
        int indexItem;
        int indexCustomer;
        Scanner input = new Scanner(System.in);

        String customerId= inputArgs[1];

        //validate the customer's id
        if(isCustomerIdValid(customerId) == false) {
            System.out.println("Unsuccesful, that is not a valid ID");
            return loanArray;
        }
        //validate item exisit 
        if(isValidItem(inputArgs[2], 0, false) == false){
            System.out.println("Error, that is an invalid item");
            return loanArray;
        }

        indexCustomer = findCustomerId(loanArray, customerId);
        if(loanArray[indexCustomer][3].equals(inputArgs[2])&&loanArray[indexCustomer][2].equals(customerId)){
            // record date  and note
            while(true){
                System.out.print("Enter the date of return (MM-DD-YYYY): ");
                dateIn = input.nextLine();
                System.out.println("Is the date below correct (Y/N)? ");
                rightAssosciation = input.nextLine();
                if(rightAssosciation.equalsIgnoreCase("y")){
                    break;
                }
            }
            if(inputArgs[2].charAt(2)== 'P'){
                itemName = "Point-Laser";
            }
            if(inputArgs[2].charAt(2)== 'V'){
                itemName = "VGA-converter";
            }
            if(inputArgs[2].charAt(2)== 'I'){
                itemName = "HDMI-converter";
            }
            if(inputArgs[2].charAt(2)== 'C'){
                itemName = "Camera";
            }
            if(inputArgs[2].charAt(2)== 'L'){
                itemName = "Laptop";
            }
            if(inputArgs[2].charAt(2)== 'M'){
                itemName = "Microphone";
            }
            if(inputArgs[2].charAt(2)== 'H'){
                itemName = "Headphone";
            }

            System.out.println("Enter a note, if none enter (none): ");
            note = input.nextLine();
            indexItem = indexItemInStock(itemName);
            x = Integer.parseInt(stockArray[indexItem][1]);
            x++;
            temp = Integer.toString(x);
            stockArray[indexItem][1] = temp;
            loanArray[indexCustomer][6] = dateIn;
            loanArray[indexCustomer] [7]= note;
            System.out.println("Success!");
            return loanArray;

        }
        else{
            System.out.println("Error, either customer's id or item's id is wrong.");
            return loanArray;
        }

    }



  public static int findNextPosition(String[][] loanArray){
    for(int index =0; index <loanArray.length; index++){  
        if(loanArray[index][0]== "available"){
            return index;
        }
    }
    return -1; // full loanArray
  }
  public static String createItemId(String itemName, String availableAmount){
    String id = " ";
        String PL= "Point-Laser";
        String VGA = "VGA-converter";
        String Camera = "Camera";
        String Headphones = "Headphone";
        String Microphone = "Microphone";
        String HDMI = "HDMI-converter";
        String Lap = "Laptop";

        if(PL.equals(itemName) == true){
            id = "CSPL-" + availableAmount;
        }
        if(VGA.equals(itemName) == true){
            id = "CSVC-" + availableAmount;
        }
        if(Camera.equals(itemName) == true){
            id = "CSC-" + availableAmount;
        }
        if(Headphones.equals(itemName) == true){
            id = "CSH-" + availableAmount;
        }
        if(Microphone.equals(itemName) == true){
            id = "CSM-" + availableAmount;
        }
        if(HDMI.equals(itemName) == true){
            id = "CSI-" + availableAmount;
        }
        if(Lap.equals(itemName) == true){
            id = "CSL-" + availableAmount;
        }
        return id;
  }

  public static boolean isCustomerIdValid(String str){
    if(!(str.startsWith("80"))){
        return false;
    }
    else{
    return true;
    }
  }
  //verify if the string is composed only of alphabetic characters
  public static boolean isAlphabetic(String str){
    if (str == null){
        return false;
    }
    for (int i = 0; i < str.length(); i++)
    {
        char c = str.charAt(i);
        if (!(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z')) {
            return false;
        }
    }
    return true;
  }

  // Recursive method:
  //Checks if the item's name is valid: composed only of letters or of letters and digits.
  //If the item's name is valid, the method returns true. Otherwise, false.
    public static boolean isValidItem(String str, int index, boolean isValid){
        if(index>=str.length() && !(isValid)){
            System.out.println("Error, "+str+" is an invalid item's id");
            return false;
        }
        if(index == str.length() && (isValid == true)){
            return true;
        }else{
            char c = str.charAt(index);
            if  ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')){
                isValid = true;
                return isValidItem(str, index + 1, isValid);
            }
            else if((c >= '0' && c <= '9')||(c=='-')){ 
                return isValidItem(str, index + 1, isValid); // recrsive call
            }
            else{
                System.out.println("Error, "+str+" is an invalid item's id"); 
                return false;
            }
        }
  } //method

  //Search the item's name into the 2D array and returns the row index
  public static int indexItemInStock(String str){
    for(int i=0; i<stockArray.length; i++){
        if(stockArray[i][0].equals(str)){
            return i;
        }
    }
    return -1; //nothing was found
  }

  public static int findCustomerId(String[][] loanArray, String customerId){
    for(int i=0; i<loanArray.length;i++){
        if(loanArray[i][2].equals(customerId)){
            return i;
        }
    }
    return -1; // not found
  }

  // This method does not return value
  public static void display2DArray(String[][] array, String title){
      System.out.println("-------Display "+title+"-------\n");
      //System.out.println(array.length+", "+array[0].length);
      if(array[0][0].equals("available")){
          System.out.println("The "+title+" is empty");
      }else{
          for(int i =0; i<array.length; i++){
              for(int j=0; j<array[0].length; j++){
                  System.out.printf("%17s",array[i][j]);
              }
              System.out.println();
          }
      }
  }

  // initialize items in stock
   public static String[][] initializeLoanArray(String[][] loanArray, int rows, int cols){
       for(int i =0; i<loanArray.length; i++){
          for(int j=0; j<loanArray[0].length; j++){
              loanArray[i][j] = "available";
          }
      }
      return loanArray;
   }
   public static void ExportLoans(String[][] loanArray) {
        File file = new File("loanArray.txt");
        System.out.println("file created");
        try{
            PrintWriter output = new PrintWriter(file);
            System.out.println("print first row");
            // writing the header in file
            output.println("First name\tLast name\t    ID\t\tItem ID\t\tItem Name\tDate-out\t\tDate-in\t\tNote");
            System.out.println("loop for writing from matrix");
            // writing value from matrix to file
            for(int row_i=0; row_i<loanArray.length; row_i++) {
                for(int col_i=0; col_i<loanArray[0].length; col_i++) {
                    output.print(loanArray[row_i][col_i]+"\t");
                }
                output.println();
            }
            System.out.println("writing done!");
            output.close();
        
    }
    catch(FileNotFoundException e){
        e.printStackTrace();
        System.out.println("File not found");
    }
   }
}//class
	