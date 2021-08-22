package ingredients;

import java.text.DecimalFormat;
/**
/ File: 517732_Assignment2.java
/ Author: Le Nguyen Thanh Toan
/ Id: 517732
/ Version: 1.0 today’s date
/ Description: Assignment 2
/ This is my own work as defined by the Eynesbury
/ Academic Misconduct policy.
*/
public class Food {
	//declare the data for this class
	private String name; //name for the food name
	private double price;  //price for the price of food
	//constructor 
	public Food(String name, double price) throws NullPointerException {
		//the name of food cannot be null, if it null, the food cannot be created
		if(name == null) {
			throw new NullPointerException("The name of food cannot be null");
		}
		else { //if the name of argument food is not null
			this.name = name; //set the name of food to the argument name
			this.price = price; //set the price of food to the argument price
		}
	}
	//another constructor
	public Food(Food other) throws NullPointerException{ //only create food if the input food is valid
		if(other != null) { //the food cannot be null, if the food argument is null
			//new food cannot be created
			String copiedName = new String(other.name); //create the copy
			double copiedPrice = other.price; //create the copy
			this.name = copiedName; //set the name to the copy
			this.price = copiedPrice; //set the price to the copy
		}
		else { //throw error if the argument is null
			System.out.println("Error: You created an invalid food, please create another one.");
			throw new NullPointerException("Food cannot be null!");
		}
	}
	//getter for the price field
	public double getPrice() {
		return this.price; //get the price
	}
	//getter for the name field
	public String getName() {
		return this.name; //get the name
	}
	protected void setPrice(double price) {
		if(price>=0) { //if the price argument is negative, set the price of the food 
			//to the new price
			this.price = price;
		}
	}
	public String getFormattedPrice() {
		//get the price before formatting
		double originalPrice = this.getPrice(); //*******
		//use DecimalFormat class to format the price 
		DecimalFormat df = new DecimalFormat("$##0.00"); //2 decimals places
		//prepended by a dollar sign
		String formattedPrice = new String(df.format(originalPrice));
		//make the formated price look like a double
		//if the output = 32, it will be 32.00 
//		double formatTool = Double.parseDouble(df.format(originalPrice));
//		if((formatTool - (int)originalPrice) == 0 || (formatTool - (int)originalPrice) == 1) {
//			formattedPrice += ".00";
//		}
		return formattedPrice; //return the formatted price
	}
	@Override
	public boolean equals(Object other) {
		boolean isEqual = false; //set the default status as false
		if(other != null && getClass() == other.getClass()) { //compare the class type
			Food anotherFood = (Food) other; //typecast if they are the same class type
			//then compare the attributes 
			if(this.name != null && anotherFood.getName() != null ) { 
				if(this.name.equals(anotherFood.getName()) && this.price == anotherFood.getPrice()) {
					isEqual = true; //set new status for the method
				}
			}
			else if(this.name == null && anotherFood.getName() == null 
					&& this.price == anotherFood.getPrice()) {
				isEqual = true; //set new status 
			}
		}
		return isEqual; //return the static
	}
	@Override
	public String toString() {
		//print the food in format "name $formattedPrice"
		return getName() + ' ' +getFormattedPrice();
	}
	@Override
	public Food clone() {
		//create a deep copy of the object
		String copiedName = new String(this.name); //copy the name
		double copiedPrice = this.price; //copy the price
		Food copiedFood = new Food(copiedName, copiedPrice); //create a food copy which contain 
															//the copy name, and copy price
		return copiedFood; //return a deep copy
	}
	public static void main(String[] args) {
		Food pizza = new Food("pizza", 5);
		Food salad = new Food("salad", 10.567);
		System.out.println(salad);
		Food beefSteak = new Food("beefSteak", 10);
		Food fish = new Food("fish",20);
		Food jambon = new Food("jambon", 25);
		Food chicken = new Food("chicken", 30);
		PizzaBase toan  = new PizzaBase("toan", 100.211, "large");
		FoodSet toppings = new FoodSet();
		toppings.add(pizza);
		toppings.add(salad);
		toppings.add(beefSteak);
		Pizza newPizza = new Pizza("new pizza", 100, toppings, toan);
		System.out.println(newPizza);
		PizzaBase loan = new PizzaBase("loan", 100, "large");
		newPizza.add(fish);
		System.out.println(newPizza.getPrice());
		System.out.println(newPizza.getFormattedPrice());
		System.out.println(newPizza);
		newPizza.setBase(loan);
		System.out.println(newPizza.getPrice());
		System.out.println(newPizza);
	}
}

