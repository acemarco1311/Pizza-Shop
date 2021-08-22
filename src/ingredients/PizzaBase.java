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
public class PizzaBase extends Food{ //inherit from food class
	//declare data for the class 
	private String sizeStr;
	private int diameter;
	public static int convertToDiameter(String size) {
		int result = 0; //default value for the size
		if(size.equals("large")) {
			result = 14;
		}
		else if(size.equals("medium")) {
			result = 12;
		}
		else if(size.equals("small")) {
			result = 10;
		}
		return result;  //will return 0 if the size is not large, medium or small
	}
	//constructor for the PizzaBase 
	public PizzaBase(String name, double price, String size) {
		super(name, price); //invoke base class constructor Food(name, price)
		this.sizeStr = new String(size); //construct the sizeStr based on the argument
		this.diameter = convertToDiameter(size); //construct the diameter based on the result of
													//convertToDiameter with 'size' as the argument
	}
	//constructor for a PizzaBase from another PizzaBase
	public PizzaBase(PizzaBase other){
		super(other.clone().getName(), other.clone().getPrice()); //?????
		//invoke the constructor the give the name and price of 'other' to constructor
		//copy name and price of 'other'
		this.sizeStr = new String(other.getSizeString()); //copy the sizeStr of other
		this.diameter = other.getDiameter(); 
	}
	public PizzaBase(PizzaBase other, String size) {
		super(other.clone().getName(), other.clone().getPrice()); //copy name and price of 'other'
		this.sizeStr = new String(size);  //set the sizeStr
		this.diameter = convertToDiameter(size); //set diameter
		//set the price of this PizzaBase
		//to the other PizzaBase's cost per inch multiplied by this PizzaBase's 
		//square inches
		//calculate the new price = the cost per inch of other PizzaBase * this PizzaBase square inches
		double newPrice = other.getCostPerSquareInch() * this.getSquareInches(); 
		setPrice(newPrice);	//set the price
	}
	//getter for diameter field
	public int getDiameter() {
		return this.diameter;
	}
	//getter for SizeStr field
	public String getSizeString() {
		return this.sizeStr;
	}
	//get the square inches based on a given formula
	public double getSquareInches() {
		double result = 0; //declare a variable for the result 
		result = Math.PI * (Math.pow((this.diameter / 2), 2)); //calculate based on the formula
		return result; 
	}
	public double getCostPerSquareInch() {
		double cost = getPrice() / getSquareInches(); //the price divided by the square inches
		return cost;
	}
	public void setSize(String size) {
		//save the current value of CostPerSquareInch which will be replaced later
		double oldCostPerSquareInch = getCostPerSquareInch();  //the costPerSquare before setSize
		if(size.equals("small") || size.equals("medium") || size.equals("large")) {
			this.sizeStr = new String(size); //set new size
			this.diameter = convertToDiameter(size); //set new diameter based on new size
		}
		else {
			System.out.println("Invalid size: The size should be small, medium or large.");
		}
		//getSquareInches now is the new value based on the new size, 
		//the oldCostPerSquareInch is the old value of cost per square of the old size
		double newPrice = oldCostPerSquareInch * getSquareInches();
		setPrice(newPrice); //set the price to new value based on new size
	}
	@Override
	public PizzaBase clone() {
		//create deep copy of each attribute
		String copiedName = new String(getName());
		double copiedPrice = getPrice();
		String copiedSize = new String(this.sizeStr);
		//then construct the copy object with copied attributes
		PizzaBase deepCopy = new PizzaBase(copiedName, copiedPrice, copiedSize);
		return deepCopy;
	}
	@Override
	public boolean equals(Object other) {
		boolean isEqual = false;
		//declare default value for return statement
		if(other != null && getClass() == other.getClass()) {
			//if the argument object is not null and is the same class to the PizzaBase
			//typecast to compare attributes
			PizzaBase anotherPizza = (PizzaBase) other;
			//compare each attribute
			if(getName() != null && this.sizeStr != null && anotherPizza.getName() != null &&
					anotherPizza.sizeStr != null) {
				if(getName().equals(anotherPizza.getName()) &&
						getPrice() == anotherPizza.getPrice() &&
						this.sizeStr.equals(anotherPizza.sizeStr)) {
					//situation 1: name = name, price = price, size = size
					isEqual = true;
				}
			}
			else if(getName() == null && anotherPizza.getName() == null &&
					this.sizeStr != null && anotherPizza.sizeStr != null) {
				if(this.sizeStr.equals(anotherPizza.sizeStr) && getPrice() == anotherPizza.getPrice()) {
					//situation 2: null = null, price = price, size = size
					isEqual = true;
				}
			}
			else if(this.sizeStr == null && anotherPizza.sizeStr == null &&
					getName() != null && anotherPizza.getName() != null) {
				if(getName().equals(anotherPizza.getName()) && getPrice() == anotherPizza.getPrice()) {
					//situation 3: name = name, price = price, null = null
					isEqual = true;
				}
			}
			else if(this.sizeStr == null && anotherPizza.sizeStr == null && 
					getName() == null && anotherPizza.getName() == null	) {
				if(getPrice() == anotherPizza.getPrice()) {
					//situation 3: null = null, price = price, null = null
					isEqual = true;
				}
			}
		}
		return isEqual; //return the result 
	}
	@Override
	public String toString() {
		//print out the PizzaBase based on a form:  "size name pizza base"
		return this.sizeStr + ' ' + getName() + ' ' + "pizza base";
	}
	public static void main(String[] args) {
		System.out.println(PizzaBase.convertToDiameter("small"));
		System.out.println(PizzaBase.convertToDiameter("adfadfad"));
		PizzaBase firstPizzaBase = new PizzaBase("fish", 34.34, "large");
		PizzaBase secondPizzaBase = new PizzaBase("beef", 342.43, "small");
		PizzaBase thirdPizzaBase = new PizzaBase(firstPizzaBase);
		PizzaBase fourthPizzaBase = new PizzaBase(firstPizzaBase, "small");
		System.out.println(firstPizzaBase.getDiameter());
		System.out.println(firstPizzaBase.getSizeString());
		System.out.println(firstPizzaBase.getSquareInches());
		System.out.println(firstPizzaBase.getCostPerSquareInch());
		firstPizzaBase.setSize("medium");
		System.out.println(firstPizzaBase.getSizeString());
		System.out.println(firstPizzaBase.equals(thirdPizzaBase));
		System.out.println(firstPizzaBase);
		System.out.println(secondPizzaBase);
		System.out.println(thirdPizzaBase);
		System.out.println(fourthPizzaBase);
	}
}
