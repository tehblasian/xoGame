package xoGame;
// ---------------------------------
// Assignment 4 - Part 1
// Written by: Jeremiah-David Wreh 40028325
// For COMP 248 Section P – Fall 2016
// ---------------------------------

//The purpose of this code is to define a class called 'Menu', that models menus used in text-based menu-driven programs.
import java.util.Scanner;
public class Menu {
	
	private String opening;
	private String closing;
	private String top;
	private String bottom;
	private String[] list;
	private static String minimal = "\n?-> ";
	
	//default constructor
	public Menu(){
		this.opening = null;
		this.closing = null;
		this.top = null;
		this.bottom = null;
		this.list = null;
	}//end default constructor
	
	//normal constructor
	public Menu(String[] options){
		this.opening = null;
		this.closing = null;
		this.top = "Choose an option:";
		this.bottom = "Enter an option number:";
		
		//sets length of array of options to the # of items in options[]
		this.list = new String[options.length];
		
		//copies the list of items in options[] to the array list[]
		for(int i = 0; i < options.length; i++)
			this.list[i] = options[i];	

	}//end normal constructor
	
	//determines whether the option list is empty
	public boolean isEmpty(){
		return(this.list == null || this.list.length == 0);
	}//end isEmpty method
	
	//returns length of the option list
	public int length(){
		if(this.list == null)
			return 0;
		return this.list.length;
	}//end length method
	
	//toString method
	public String toString(){
		String menu;
		String options = "\n";
		
		//loop to build option list
		if(this.list != null)
			for(int j = 0; j < this.list.length; j++)
				options = options + "    (" + (j+1) + ") " + this.list[j] + "\n";
		
		//menu = (this.opening == null? this.top:this.opening + "\n" + this.top) + options + (this.closing == null? minimal:this.closing + "\n" + minimal) + this.bottom;
		menu = (this.opening == null? "" :this.opening) + this.top + options + (this.closing == null? "":this.closing) + minimal + this.bottom + " ";
		
		return menu.replaceAll("(?m)^\\s", "");
	}//end toString method

	//displays the menu, receives input from keyboard, checks if input is valid and returns it
	public int readOptionNumber(){
		Scanner keyIn = new Scanner(System.in);
		int selection;
		
		//create menu object
		Menu menu;
		
		//pick correct constructor
		if(this.list != null)
			menu = new Menu(this.list);
		else
			menu = new Menu();
		
		//set correct parameters
		menu.setTopMessage(this.opening);
		menu.setBottomMessage(this.closing);
		menu.setTopPrompt(this.top);
		menu.setBottomPrompt(this.bottom);
				
		//display menu
		System.out.print(menu);
		
		//ask for input
		selection = keyIn.nextInt();
		
		//formatting
		System.out.println();
		
		//check if list is null
		if(this.list == null || this.list.length == 0)
			return selection;
		
		while(selection < 1 || selection > this.list.length){
				System.out.print(menu);
				selection = keyIn.nextInt();	
		}
		
		return selection;	
	}//end readOptionNumber
	
	//returns opening message
	public String getTopMessage(){
		return this.opening;
	}

	//to modify top message
	public void setTopMessage(String opening){
		if(opening == null)
			this.opening = "";
		else
			this.opening = opening;	
	}
	
	//returns closing message
	public String getBottomMessage(){
		return this.closing;
	}
	
	//to modify closing message
	public void setBottomMessage(String closing){
		if(closing == null)
			this.closing = "";
		else
			this.closing = closing;
	}
	
	//returns top prompt
	public String getTopPrompt(){
		return this.top;
	}

	//to modify top prompt
	public void setTopPrompt(String top){
		if(top == null)
			this.top = "";
		else
			this.top = "\n" + top;
	}
	
	//returns bottom prompt
	public String getBottomPrompt(){
		return this.bottom;
	}
	
	//to modify bottom prompt
	public void setBottomPrompt(String bottom){
		if(bottom == null)
			this.bottom = "";
		else
			this.bottom = bottom;
	}	
}//end class
