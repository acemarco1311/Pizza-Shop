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
public class FoodSet {
	//declare the data for the class
	private Food[] foods;
	private int count;
	//constructor which uses default value
	public FoodSet() {
		this.foods = new Food[1];
		this.count = 0;
	}
	//constructor which is a copy from another object
	public FoodSet(FoodSet other) throws NullPointerException{
		if(other == null) {
			throw new NullPointerException("FoodSet cannot be null");
		}
		else {
			//construct the count based on the count of the argument
			//get the length of the argument's array
			int arrayLength = other.foods.length;
			//construct the array 
			this.foods = new Food[arrayLength];
			this.count = other.count; //copy the count
			//copy each item from the argument's array to this array
			for(int i=0; i<arrayLength; i++) {
				if(other.foods[i] != null) {
					this.foods[i] = other.foods[i].clone(); //get item from the deep copy of other
				}
				else {
					this.foods[i] = null;
				}
			}
		}
	}
	@Override
	public FoodSet clone() {
		//because the constructor FoodSet(FoodSet other) can create
		//deep copies so that in this method
		//I can use it to create a deep copy of this FoodSet
		FoodSet copiedFoodSet = new FoodSet();
		int arrayLength = this.foods.length;
		//construct the array 
		copiedFoodSet.foods = new Food[arrayLength];
		copiedFoodSet.count = this.count;
		//copy each item from the argument's array to this array
		for(int i=0; i<arrayLength; i++) {
			if(this.foods[i] != null) {
				copiedFoodSet.foods[i] = this.foods[i].clone();
			}
			else {
				copiedFoodSet.foods[i] = null;
			}
		}
		return copiedFoodSet;
	}

	public boolean contains(Food food) throws NullPointerException{
		boolean gotIt = false; //represent the result for the return statement
		if(food == null) {
			throw new NullPointerException("This method cannot be used for looking for null");
		}
		else {
			int i = 0; //create the index for the while loop
			while(gotIt == false && i < this.foods.length) {
				//the while loop will stop when it got the item that equals the argument
				// or when the while loop reached the end of the Food array
				if(this.foods[i] != null) {
					if(this.foods[i].getName().equals(food.getName()) && 
							this.foods[i].getPrice() == food.getPrice()) {
						gotIt = true;
					}
				}
				i++;
			}
		}
		return gotIt;
	}
	private void increaseLength() {
		int newLength = this.foods.length * 2; //store the new length in a variable
		Food[] temporaryArray = new Food[newLength]; //create a temporary for current array
		//which contains all the current items
		for(int i=0; i<this.foods.length; i++) {
			if(this.foods[i] != null) {
				temporaryArray[i] = this.foods[i];
			}
		}
		this.foods = new Food[newLength]; //reset the original food array for the new length 	
		for(int i=0; i<newLength; i++) { //put the items back to the array
			if(temporaryArray[i] != null) {
				this.foods[i] = temporaryArray[i];
			}
		}
	}
	public boolean containsNull() {
		//check if the Food array contain any null value or not
		boolean gotNull = false;
		int i = 0;
		while(gotNull == false && i<this.foods.length) {
			if(this.foods[i] == null) {
				gotNull = true;
			}
			i++;
		}
		return gotNull;
	}
	public int findFirstNull() {
		//get the position of the first null value in the array 
		int position = -1;
		int i = 0;
		while(position == -1 && i<this.foods.length){
			if(this.foods[i] == null) {
				position = i;
			}
			i++;
		}
		return position;
	}
	public boolean add(Food food) throws NullPointerException{
		/*
		 * This method will add the food argument to this.foods[], 
		 * users are not allowed to add the argument which is null
		 */
		boolean success = false;
		if(food != null && contains(food) == false) { //only add if the argument is not null
			//and the array does not contain the same food 
			if(containsNull() == false) { //if the array is full
				//double the length 
				int addPosition = this.foods.length; //store the position that will be added 
				//by the argument
				increaseLength();
				this.foods[addPosition] = food.clone();
			}	
			//because the question is not clear about what is the "next available position"
			//so I assume that if the array is not full, the argument will be added to 
			//the position of the first null
			else {
				this.foods[findFirstNull()] = food.clone();
			}
			this.count++; //increment the count 
			success = true; //set the result value
		}
		else if(food == null) { 
			System.out.println("You are not allowed to add null object!");
			throw new NullPointerException();
		}
		else if(contains(food) == true) { //won't add if there is a same name food as the argument
			System.out.println("The food is already there.");
		}
		return success;
	}	

	public Food get(int index) {
		if(index >= 0 && index < this.count) { //check validation of the index  
			return this.foods[index] ; //if index is valid, return the item at index position
		}
		return null; //if the index is not valid, return null
	}
	public int getPositionSameName(String name) {
		/*
		this method will return the position of the item that has the same name with 
		the argument
		return -1 if there is no item in the array that has the same name with the argument 
		 */
		int targetPosition = -1; 
		int i = 0;
		while(targetPosition == -1 && i < this.foods.length) {
			if(this.foods[i] != null) {
				if(this.foods[i].getName().equals(name)) {
					targetPosition = i;
				}
			}
			i++;
		}
		return targetPosition;
	}

	public Food get(String name) {
		if(getPositionSameName(name) == -1) { //if cannot get the target in the array, return null
			return null;
		}
		return this.foods[getPositionSameName(name)]; //return the target when get the target
	}

	public boolean remove(String name) {
		boolean removeSuccess = false;
		int removePosition = getPositionSameName(name); 
		//getPositionSameName(name) will get the position of the food in the array with
		//the same name of the argument
		if(removePosition != -1) { //only remove if can find the position of food with the argument
			//name
			for(int i=removePosition; i<this.foods.length-1; i++) {
				if(this.foods[i+1] != null) {
					this.foods[i] = this.foods[i+1];
				}
				else {
					this.foods[i] = null;
				}
			}
			this.foods[this.foods.length-1] = null;
			removeSuccess = true;
			this.count -= 1;
		}
		return removeSuccess;
	}
	//getter for the count
	public int count() {
		return this.count; //return the count variable
	}
	@Override
	public String toString() {
		//print out based on a form "name1, name2, name3"
		String result = "" ;
		for(int i=0; i<this.foods.length; i++) {
			if(this.foods[i] != null) {
				if(this.foods[i].getName() != null) {
					if(i != this.foods.length-1) {
						result += this.foods[i].getName() + ", ";
					}
					else {
						result += this.foods[i].getName() ;
					}
				}
				else if(this.foods[i].getName() == null && i != this.foods.length-1) {
					result += "null, ";
				}	
				else if(this.foods[i].getName() == null && i == this.foods.length-1) {
					result += "null";
				}
			}
			else if(this.foods[i] == null && i == this.foods.length-1) {
				result += "null ";
			}
			else if(this.foods[i] == null && i != this.foods.length-1) {
				result += "null, ";
			}
		}
		return result;
	}
	public static void main(String[] args) {
		Food toan = new Food("tona", 34.3);
		Food loan = new Food("loan", 23.4);
		FoodSet newFoodSet = new FoodSet();
		newFoodSet.add(toan);
		newFoodSet.add(loan);
		FoodSet another = new FoodSet(newFoodSet);
		System.out.println(newFoodSet.count);
		System.out.println(another.count);
		FoodSet copy = newFoodSet.clone();
		System.out.println(copy.count);
		System.out.println(newFoodSet);
		System.out.println(another);
		System.out.println(copy);
	}
}
