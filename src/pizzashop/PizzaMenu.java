package pizzashop;

import ingredients.Food;
import ingredients.FoodSet;
import ingredients.Pizza;
import ingredients.PizzaBase;
/**
/ File: 517732_Assignment2.java
/ Author: Le Nguyen Thanh Toan
/ Id: 517732
/ Version: 1.0 today’s date
/ Description: Assignment 2
/ This is my own work as defined by the Eynesbury
/ Academic Misconduct policy.
*/
public class PizzaMenu extends FoodSet{
	//constructor for the pizzaMenu
	public PizzaMenu() {
		super(); //call the super constructor
	}
	@Override
	public boolean add(Food pizza) {
		boolean success = false; //default status of the method
		if(pizza instanceof Pizza == true) { //only add Pizza 
			super.add(pizza); //add the pizza
			success = true; //change the status
		}
		return success;
	}
	@Override
	public Pizza get(String name) {
		if(super.get(name) instanceof Pizza){ //only get the Pizza with the same name of the argument
			return (Pizza)super.get(name); //typecast from Food to Pizza
			}
		return null; //if the food with the name is not a pizza or it cannot be found, return null
	}
	@Override 
	public Pizza get(int index) {
		if(super.get(index) instanceof Pizza) { //only get the Food at specified position, if it is Pizza
			return (Pizza)super.get(index); //typecast from Food to Pizza
		}
		return null;//return null if the food at the position is not Pizza 
	}
	@Override 
	public String toString() {
		//print out the menu
		String result = "Menu: \n";
		//loop through each pizza
		for(int i = 0; i<this.count(); i++) {
			result = result + get(i).getName() + ", " + get(i).getBase().getSizeString() + ' ' + 
					get(i).getBase().getName() + ' ' + get(i).getFormattedPrice() + ": \n \t";
			//loop through each topping on the Pizza
			for(int j=0; j<get(i).getToppings().count(); j++ ) {
				if(j != get(i).getToppings().count() - 1) {
					result = result + get(i).getToppings().get(j).getName() + ", ";
				}
				else {
					result = result + get(i).getToppings().get(j ).getName() ;
				}
			}
			result += "\n";
		}
		return result;
	}

	public static void main(String[] args) {
		PizzaMenu menu = new PizzaMenu();
		Food pizza = new Food("pizza", 5);
		Food salad = new Food("salad", 10);
		Food beefSteak = new Food("beefSteak", 15);
		Food fish = new Food("fish",20);
		Food jambon = new Food("jambon", 25);
		Food chicken = new Food("chicken", 30);
		FoodSet toppings = new FoodSet();
		toppings.add(pizza);
		toppings.add(salad);
		toppings.add(beefSteak);
		PizzaBase toan  = new PizzaBase("toan", 200, "large");
		Pizza newPizza = new Pizza("fish", 23, toppings, toan);
		PizzaBase loan = new PizzaBase("loan",100, "large");
		Pizza anotherPizza = new Pizza("beef", 32, toppings, loan);
		menu.add(newPizza);
		menu.add(anotherPizza);
		System.out.println(menu);
	}



}
