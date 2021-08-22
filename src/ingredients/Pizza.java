package ingredients;
/**
/ File: 517732_Assignment2.java
/ Author: Le Nguyen Thanh Toan
/ Id: 517732
/ Version: 1.0 today’s date
/ Description: Assignment 2
/ This is my own work as defined by the Eynesbury
/ Academic Misconduct policy.
*/
public class Pizza extends Food {
	//declare data for the class
	private PizzaBase base;
	private FoodSet toppings;
	final private FoodSet originalToppings;
	//constructor of Pizza
	public Pizza(String name, double price, FoodSet ingredients, PizzaBase base) {
		//invoke food constructor for name and price 
		super(name, price);
		//initialize the base with the deep copy of base argument
		this.base = base.clone();
		//set the toppings and originalTopping to the deep copy by using clone() method
		this.toppings = ingredients.clone();
		this.originalToppings = ingredients.clone();
	}
	public Pizza(Pizza menuItem, PizzaBase base) {
		//set name and price to the deep copy of menuItem argument
		super(menuItem.getName(), menuItem.getPrice());
		//set toppings and originalToppings to the deep copy of menuItem argument
		this.toppings = menuItem.getToppings().clone();
		this.originalToppings = menuItem.getOriginalToppings().clone();
		//set the base to the deep copy of base argument
		this.base = base.clone();
		//Resets the price field to the current price plus the
		//difference between the price of the argument PizzaBase and the price of the argument menuItem's
		//PizzaBase.
		double pizzaPrice = this.getPrice();
		this.setPrice(pizzaPrice + (base.getPrice() - menuItem.getBase().getPrice()));
	}
	@Override
	public Pizza clone() {
		//create a deep copy of the calling object
		
		String copiedName = new String(this.getName());//create copied name
		double copiedPrice = this.getPrice(); //create copied price
		FoodSet copiedToppings = this.toppings.clone(); //create the copied toppings list 
		PizzaBase copiedPizzaBase = this.base.clone(); //create the copied pizza base
		//create new pizza which is a copy of the calling object
		Pizza copiedPizza = new Pizza(copiedName, copiedPrice, copiedToppings, copiedPizzaBase);
		return copiedPizza;
	}
	public int countAdditionalToppings(FoodSet toppings, FoodSet originalToppings) {
		int count = 0;
		for(int i = 0; i < toppings.count(); i++) {
			boolean contain = false;
			for(int j = 0; j < originalToppings.count(); j++) {
				if(toppings.get(i).equals(originalToppings.get(j))) {
					contain = true;
				}
			}
			if(contain == false) {
				count++;
			}
		}
		return count;
	}
	public double getPrice() {
		double totalPrice = super.getPrice(); //invoke superclass Food getPrice() method
		//create an array for the toppings that are not the same as those in originalToppings
		Food[] additionalToppings = 
				new Food[countAdditionalToppings(this.toppings, this.originalToppings)];
		int index = 0;
		//loop through addtionalToppings to add the additional toppings
		while(index < countAdditionalToppings(this.toppings, this.originalToppings)) {
			//loop through the current toppings and original toppings
			//to find the additional toppings
			for(int i=0; i<this.toppings.count(); i++) {
				boolean contain = false;
				int j = 0;
				while(contain == false && j<this.originalToppings.count()) {
					if(this.toppings.get(i).equals(this.originalToppings.get(j))) {
						contain = true; 
						//contain = true mean the current topping is one of the original topping
						//so that it will be ignore
					}
					j++; 
				}
				if(contain == false) {
					//if contain = false mean that the current topping is not one of the 
					//original toppings, it will be added to the additionalToppings array
					additionalToppings[index] = new Food(this.toppings.get(i));
					index++;
				}
			}
		}
		//loop through additionalToppings array to calculate the price
		for(int i=0; i<additionalToppings.length; i++) {
			if(additionalToppings[i] != null) {
				totalPrice += additionalToppings[i].getPrice();
			}
		}
		return totalPrice; //return the price which is  the result of the superclass's getPrice() 
							//method plus the price of all items in toppings that are not the
								//same as those in the originalToppings
	}
	//return a deep copy of the base field
	public PizzaBase getBase() {
		PizzaBase deepCopy = this.base.clone(); //create the deep copy 
		return deepCopy; //return the deep copy
	}
	//return a deep copy of the toppings field
	public FoodSet getToppings() {
		FoodSet deepCopy = this.toppings.clone(); //create deep copy
		return deepCopy;
	}
	//return a deep copy of the original toppings field
	public FoodSet getOriginalToppings() {
		FoodSet deepCopy = this.originalToppings.clone(); //create deep copy
		return deepCopy;
	}
	public void setBase(PizzaBase base) {
		if(base != null) { //execute only when the argument is not null
			 //calculate difference between the price of the argument PizzaBase 
			//and the price of the old
			 //PizzaBase
			double difference = base.getPrice() - getBase().getPrice();
			this.base = base.clone();//set the base to the deep copy of the argument
			double currentPizzaPrice = super.getPrice();
			setPrice(currentPizzaPrice + difference); //set new price
		}
	}
	public boolean add(Food topping) {
		boolean success = false;
		if(topping.getClass() != PizzaBase.class && topping.getClass() != Pizza.class) {
			//only execute if the topping is not PizzaBase and is not Pizza
			this.toppings.add(topping); //add topping to toppings FoodSet
			success = true; //add successfully
		}
		
		return success; //return the status of topping adding process
	}
	public boolean remove(String topping) {
		boolean success = false;
		//only remove if can find the topping with the same name in the toppings
		if(this.toppings.getPositionSameName(topping) != -1) {
		success = this.toppings.remove(topping); //remove topping by using
														//remove method in FoodSet class
		}
		else {
			System.out.println("Cannot find the topping.");
		}
		return success;
		
	}
	@Override
	public String toString() {
		//print out the pizza based on a format 
		
		String thePizza = this.getName() + ", " + this.base.getSizeString() + ' ' + 
				this.base.getName() + ' ' + this.getFormattedPrice() + ": \n \t";
		//loop through each topping to get each topping
		for(int i=0; i<this.toppings.count(); i++ ) {
			if(i != this.toppings.count() - 1) {
				thePizza = thePizza + this.toppings.get(i).getName() + ", ";
			}
			else {
				thePizza = thePizza + this.toppings.get(i).getName() ;
			}
		}
		return thePizza; //return the result
	}
	
	public static void main(String[] args) {
		Food pizza = new Food("pizza", 5);
		Food salad = new Food("salad", 10);
		Food beefSteak = new Food("beefSteak", 10);
		Food fish = new Food("fish",20);
		Food jambon = new Food("jambon", 25);
		Food chicken = new Food("chicken", 30);
		PizzaBase toan  = new PizzaBase("toan", 200, "large");
		FoodSet toppings = new FoodSet();
		toppings.add(pizza);
		toppings.add(salad);
		toppings.add(beefSteak);
		Pizza newPizza = new Pizza("new pizza", 100, toppings, toan);
		System.out.println(newPizza);
		PizzaBase loan = new PizzaBase("loan", 100, "large");
		newPizza.add(fish);
		System.out.println(newPizza);
		newPizza.setBase(loan);
		System.out.println(newPizza);
	}
}
