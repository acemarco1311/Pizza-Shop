package pizzashop;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;


import ingredients.Food;
import ingredients.FoodSet;
import ingredients.Pizza;
import ingredients.PizzaBase;
/**
/ File: 517732_Assignment2.java
/ Author: Le Nguyen Thanh Toan
/ Id: 517732
/ Version: 1.0 today�s date
/ Description: Assignment 2
/ This is my own work as defined by the Eynesbury
/ Academic Misconduct policy.
*/
public class PizzaShop {
	//declare data for the class
	private FoodSet ingredients;
	private PizzaMenu menu;
	private Scanner keyboard;
	//constructor
	public PizzaShop() {
		this.ingredients = new FoodSet();//initializa the ingredients to empty FoodSet
		this.menu = new PizzaMenu(); //menu to an empty menu
		this.keyboard = new Scanner(System.in);
		loadIngredients();
		loadMenu();
	}
	private void loadIngredients() {
		try {
			File ingredients = new File("files/ingredients.txt"); //open the ingredients.txt
			Scanner readIngredients = new Scanner(ingredients); //create Scanner to read
			while(readIngredients.hasNextLine()) { //read each line of the file
				String line = readIngredients.nextLine(); //store each line to a variable 
				if(!(line.substring(0, 5).equals("base:"))) {
					//determine if it is a base or a food
					//if it not a base, store as a food
					//get the price = the character behind dollar sign the end of the line
					double price = Double.parseDouble(line.substring(line.indexOf("$")+1, line.length()));
					Food newFood = new Food(line.substring(0, line.indexOf("$")-1),
							price);
					//create a food
					this.ingredients.add(newFood); //add new food to ingredients
				}
				else { //if it is a base
					//get the Price
					double price = Double.parseDouble(line.substring(line.indexOf("$")+1, line.length()));
					//the name will start from character 6 to the dollar sign
					PizzaBase newPizzaBase = new PizzaBase(line.substring(6, line.indexOf("$")-1),
							price, "large"); //create PizzaBase
					this.ingredients.add(newPizzaBase); //add the base
				}
			}
			readIngredients.close(); //close the Scanner 
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Cannot find the file!");
		}
	}
	
	public int IngredientStartPosition(String line) {
		/*
		 * This method will determine the position that the ingredient will start
		 * parameter is a line which is taken from menu.txt so that this method can only
		 * be applied with lines from menu.txt
		 */
		int start = line.indexOf("$");
		int result = -1;
		int i = start;
		while(result == -1 && i<line.length()) {
			if(line.charAt(i) == ' ') {
				result = i;
			}
			i++;
		}
		return result;
	}
	
	public int countIngredient(String line) {
		/*
		 * This method will determine how many ingredient in a Pizza 
		 * This method take a line which is taken from menu.txt
		 * So that this method can only be applied to lines from menu.txt
		 */
		int count = 0;
		for(int i=0; i<line.length(); i++) {
			if(line.charAt(i) == ',') {
				count++;
			}
		}
		return count+1;
	}
	public int getCommaPosition(String line, int number) {
		/*
		 * This method will return a position of a specific comma in a line from menu.txt
		 * It will be a line from menu.txt and the comma that you wanna get (the first, the second, etc)
		 */
		int i = 0;
		int count = 0;
		int position = -1;
		while(i < line.length() && position == -1) {
			if(line.charAt(i) == ',') {
				count++;
			}
			if(count == number) {
				position = i;
			}
			i++;
		}
		return position;
	}
	private void loadMenu() {
		try {
			File menu = new File("files/menu.txt"); //open the file
			Scanner readMenu = new Scanner(menu); //create the reader
			while (readMenu.hasNextLine()) { //read each line
				String line = new String(readMenu.nextLine()); //store the line into a variable
				//the name of Pizza from the start to the dollar sign
				String name = new String(line.substring(0, line.indexOf("$")-1)); 
				//the price will be from dollar sign to the position that the ingredient start
				double price = Double.parseDouble(line.substring(line.indexOf("$") + 1, 
						IngredientStartPosition(line)));
				FoodSet toppings = new FoodSet();
				int startPosition = IngredientStartPosition(line)+1;
				//loop and add each topping
				for(int i=0; i<countIngredient(line)-1; i++) {
					toppings.add(this.ingredients.get(line.substring(startPosition,
							getCommaPosition(line, i+1))));
					startPosition = getCommaPosition(line, i+1) + 2;
				}
				toppings.add(this.ingredients.get(line.substring(startPosition, line.length())));
				//the default PizzaBase will be thin crust base from ingredient with large size
				PizzaBase base = new PizzaBase((PizzaBase)this.ingredients.get("thin crust"), "large");
				Pizza newPizza = new Pizza(name, price, toppings, base);
				this.menu.add(newPizza); //add new Pizza
			}
			readMenu.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("Cannot find the file!");
			e.printStackTrace();
		}
	}

	public Pizza choosePizza() {
		System.out.println(this.menu); //print out the menu
		System.out.println("What pizza do you like?"); //prompt user
		keyboard.nextLine();
		String pizzaName = keyboard.nextLine(); //take pizza name from the user
		if(this.menu.get(pizzaName) != null) { //try to get the pizza with the same name
			return this.menu.get(pizzaName).clone(); //if found it, return a copy of that 
		}
		return null; //if cannot find the Pizza with that name, return null
	}
	public Pizza changeBase(Pizza pizza) {
		System.out.println("Base: ");
		for(int i = 0; i<this.ingredients.count(); i++) { //loop through ingredients
			if(this.ingredients.get(i) instanceof PizzaBase) { //only get the base 
				System.out.println(this.ingredients.get(i).getName()); //only get the name of the base
			}
		}
		System.out.println("What base would you like: ");
		String baseName = new String(keyboard.nextLine()); //take the base name from user
		try {
			// get a copy of this item from the ingredients FoodSet
			PizzaBase newBase = (PizzaBase)this.ingredients.get(baseName).clone();
			//, set its size to the old PizzaBase's size
			newBase.setSize(pizza.getBase().getSizeString());
			pizza.setBase(newBase); //set the argument Pizza's base to that PizzaBase
		}
		catch(ClassCastException e) {
			System.out.println(baseName + " is not a pizza base.");
		}
		catch(NullPointerException e) {
			System.out.println(baseName + " is not a pizza base");
		}
		return pizza;
	}

	public Pizza changeSize(Pizza pizza) {
		//Prompt for user
		System.out.println("What size pizza would you like (small/medium/large): ");
		//take input from the user
		String name = keyboard.nextLine();
		//check if the input is valid, if the input is not valid, take input again
		while(!name.equals("small") && !name.equals("medium") &&
				!name.equals("large")) {
			System.out.println("Must be one of small, medium, or large");
			System.out.println("What size pizza would you like (small/medium/large): ");
			name = keyboard.nextLine();
		}
		PizzaBase newBase = new PizzaBase(pizza.getBase(), name);
		//set the size of the argument Pizza to the input
		//the size of Pizza can only be changed by giving the Pizza a new PizzaBase with
		//new size
		pizza.setBase(newBase);
		//return the pizza
		return pizza;
	}

	public Pizza addTopping(Pizza pizza) {
		if(pizza != null) {
			//Print out all the toppings in the list
			System.out.println("Toppings: ");
			for(int i=0; i<this.ingredients.count(); i++) {
				if(!(this.ingredients.get(i) instanceof PizzaBase)) {
					System.out.println(this.ingredients.get(i));
				}
			}
			System.out.println("What topping would you like to add: ");
			//take input from user 
			String newTopping = new String(keyboard.nextLine());
			try {
				if(!(this.ingredients.get(newTopping) instanceof PizzaBase) && 
						this.ingredients.getPositionSameName(newTopping) != -1) {
					//if the input is not PizzaBase and the input can be found
					pizza.add(this.ingredients.get(newTopping));
					//add the ingredient with the same name of the input
				}
				else {
					//if cannot find any ingredient with the same name of input 
					//or the ingredient with the same name of the input is the PizzaBase 
					//Print out the error message
					System.out.println("Could not find " + newTopping);
				}
			}
			catch(NullPointerException e) {
				System.out.println("Could not find " + newTopping);
			}
		}
		else {
			System.out.println("Invalid pizza input");
		}
		return pizza; //return the pizza after adding new toppings
	}

	public Pizza removeTopping(Pizza pizza) {
		if(pizza != null) {
			//only execute when the input pizza is not null
			System.out.println("Toppings: ");
			for(int i=0; i<pizza.getToppings().count(); i++) {
				System.out.println(pizza.getToppings().get(i).getName());
			}
			System.out.println("What topping would you like to remove: ");
			String removeTopping = new String(keyboard.nextLine());
			try {
				if(!(this.ingredients.get(removeTopping) instanceof PizzaBase) && 
						this.ingredients.getPositionSameName(removeTopping) != -1) {
					//if the input is not PizzaBase and the input can be found
					pizza.remove(removeTopping);
					//remove the ingredient with the same name of the input
				}
				else {
					//if cannot find any ingredient with the same name of input 
					//or the ingredient with the same name of the input is the PizzaBase 
					//Print out the error message
					System.out.println("Could not find " + removeTopping);
				}
			}
			catch(NullPointerException e) {
				System.out.println("Could not find " + removeTopping);
			}
		}
		else {
			System.out.println("Invalid pizza input!");
		}
		return pizza;
	}
	public void orderPizza(Pizza pizza) {
		System.out.println("What is your name?");
		String name = new String(keyboard.nextLine()); //get the user name
		while(name.equals("")) { //enter again if it is empty string
			System.out.println("Please enter your name: ");
			name = new String(keyboard.nextLine());
		}
		PrintWriter pw = null;
		try
		{
			String filename= "files\\receipts\\" + name + ".txt";
			pw = new PrintWriter(new FileWriter(filename,true)); //the true will append the new data
			//get the pizza
			String thePizza = pizza.getName() + ", " + pizza.getBase().getSizeString() + ' ' + 
					pizza.getBase().getName() + ' ' + pizza.getFormattedPrice() + ": \n \t";
			for(int i=0; i<pizza.getToppings().count(); i++ ) {
				if(i != pizza.getToppings().count() - 1) {
					thePizza = thePizza + pizza.getToppings().get(i).getName() + ", ";
				}
				else {
					thePizza = thePizza + pizza.getToppings().get(i).getName() + '.';
				}
			}
			pw.write(thePizza);//appends the pizza to the file
			pw.write("\n");
			pw.write("Enjoy your meal " + name +"! :) \n");
		}
		catch(IOException ioe)
		{
			System.err.println("IOException: " + ioe.getMessage());
		}
		finally{ //use this to close the writer, use finally so that the writer will be closed
			//in any situation
			if(pw != null) {
				pw.close();
			}
		}
	}

	public void commandPrompt() {
		System.out.println("Welcome to the pizza shop!");
		boolean exit = false;
		while(exit == false) {
			System.out.println("1. Order Pizza");
			System.out.println("2. Exit");
			System.out.println("How may I help you: ");
			int orderDecision = -1;
			String command = "";
			boolean complete = false;
			while(complete == false){
				while(keyboard.hasNextInt() == false) {
					System.out.println("Invalid choice: ");
					keyboard.nextLine();
					
				} //if the decision is not an integer, allow user enter again
				orderDecision = Integer.parseInt(keyboard.next());
				if(orderDecision == 1 || orderDecision == 2){
					complete = true;
				}
				else{
					System.out.println("Enter again: ");
					command = keyboard.nextLine();
				}
				
				// while(orderDecision != 1 && orderDecision != 2){
				// 	System.out.println("Please select either 1 or 2: ");
	
				// }
			}
			
			

			// while(orderDecision != 1 && orderDecision != 2) {
			// 	System.out.println("Please select either 1 or 2: ");
			// 	keyboard.nextLine();
			// 	orderDecision = Integer.parseInt(keyboard.nextLine());
			// }
			if(orderDecision == 1) {
				Pizza userPizza = choosePizza();
				if(userPizza == null){
					System.out.println("We do not make that kind of pizza.");
				}
				else {
					System.out.println("Your pizza: ");
					System.out.println(userPizza);
					System.out.println("1. Change Size");
					System.out.println("2. Change Pizza Base");
					System.out.println("3. Add Topping");
					System.out.println("4. Remove Topping");
					System.out.println("5. Order");
					System.out.println("6. Cancel");
					System.out.println("What would you like to do: ");
					while(keyboard.hasNextInt() == false) {
						System.out.println("Invalid choice: ");
						keyboard.nextLine();
					} //allow user enter again if user does not enter an integer
					int additionalDecision = Integer.parseInt(keyboard.nextLine());
					while(additionalDecision != 5 && additionalDecision != 6) {
						
						if(additionalDecision == 1) {
							changeSize(userPizza);
						}
						else if(additionalDecision == 2) {
							changeBase(userPizza);
						}
						else if(additionalDecision == 3) {
							addTopping(userPizza);
						}
						else if(additionalDecision == 4) {
							removeTopping(userPizza);
						}
						else{
							System.out.println("Please only enter 1-6");
						}
						System.out.println("Your pizza: ");
						System.out.println(userPizza);
						System.out.println("1. Change Size");
						System.out.println("2. Change Pizza Base");
						System.out.println("3. Add Topping");
						System.out.println("4. Remove Topping");
						System.out.println("5. Order");
						System.out.println("6. Cancel");
						if(additionalDecision < 1 || additionalDecision > 6) {
							System.out.println("Please enter your decision again: ");
						}
						else {
							System.out.println("What would you like to do next: ");
						}
						while(keyboard.hasNextInt() == false) {
							System.out.println("Invalid choice: ");
							keyboard.nextLine();
						}
						additionalDecision = Integer.parseInt(keyboard.nextLine());
					}
					if(additionalDecision == 5) {
						orderPizza(userPizza);
					}
					else if(additionalDecision == 6) {
						System.out.println("Goobye! ");
					}

				}
			}
			else if(orderDecision == 2) {
				System.out.println("Have a good day! :)");
				exit = true;
			}
		}
	}
	public static void main(String[] args) {
		PizzaShop toan = new PizzaShop();
		toan.commandPrompt();
	}
}
