import java.util.Scanner;

public class Player {

		private String symbol = "";
		private String name = "";
		private int numWins;
		private int numLosses;
		private boolean first;
		private boolean isAComputer = false;
		
		
		//default constructor -- prompts user to select symbol and decide whether to play first
		public Player(){
			Scanner input = new Scanner(System.in);
			String symbol, goFirst;
			boolean start = false;
			//create players
			System.out.println("What is your name?");
			this.name = input.next();
			
			do{
				System.out.println("Are you X's or O's? Enter 'X' or 'O': ");
				symbol = input.next().toUpperCase();
			}while(!(symbol.equalsIgnoreCase("X") || symbol.equalsIgnoreCase("O")));
			
			do{
				System.out.println("Would you like to go first? Enter y/n: ");
				goFirst = input.next();
			}while(!(goFirst.equalsIgnoreCase("y") || goFirst.equalsIgnoreCase("n") || goFirst.equalsIgnoreCase("yes") || goFirst.equalsIgnoreCase("no")));
			
			if(goFirst.equalsIgnoreCase("y") || goFirst.equalsIgnoreCase("yes"))
				start = true;
			if(goFirst.equalsIgnoreCase("n") || goFirst.equalsIgnoreCase("no"))
				start = false;
			
			this.symbol = symbol;
			this.first = start;
			this.isAComputer = false;
			this.numWins = 0;
			this.numLosses = 0;
		}
		
		public Player(String symbol, boolean first){
			this.symbol = symbol;
			this.first = first;
			this.name = "Player 1";
			this.isAComputer = false;
			this.numWins = 0;
			this.numLosses = 0;
		}
			
		public Player(String symbol){
			this.symbol = symbol;
			this.first = false;
			this.name = "Player 1";
			this.isAComputer = false;
			this.numWins = 0;
			this.numLosses = 0;
		}

		//adds 1 to win count
		public void addWin(){
			this.numWins++;
		}
		
		//adds 1 to loss count
		public void addLoss(){
			this.numLosses++;
		}
		
		//returns number of wins
		public int getWinCount(){
			return this.numWins;
		}
		
		//returns number of losses
		public int getLossCount(){
			return this.numLosses;
		}
		
		//to make a move
		public void move(xoBoard board){
			String move = "";
			int row = 0;
			int col = 0;
			boolean goodMove = false;
			Scanner input = new Scanner(System.in);
			
			do{
				do{
					//make sure that coordinate is valid
					System.out.println("Make a move (Enter coordinate xy): ");
					move = input.nextLine();
					if(move.length() != 2)
						System.out.println("Cannot complete move -- Invalid coordinate entry");
				}while(move.length() != 2);
					
				row = Integer.parseInt(Character.toString(move.charAt(0)));
				col = Integer.parseInt(Character.toString(move.charAt(1)));
			
				goodMove = this.add(board, row, col);
			}while(!goodMove);
		}
		
		//makes the best move available
		public void bestMove(xoBoard board, int difficulty){
			String bestMove = board.bestMove(this, difficulty);
			int row = Integer.parseInt(Character.toString(bestMove.charAt(0)));
			int col = Integer.parseInt(Character.toString(bestMove.charAt(1)));
			
			this.add(board, row, col);
		}
		
		//to add move to board
		public boolean add(xoBoard board, int row, int col){
			return board.add(row, col, this.symbol);	
		}
		
		//check if player has won
		public boolean hasWon(xoBoard board){
			return board.won(this.symbol);
		}
		
		public void setSymbol(String symbol){
			this.symbol = symbol;
		}
		
		public void setName(String name){
			this.name = name;
		}
		//sets the player to first
		public void goFirst(){
			this.first = true;
		}
		
		//sets the player to second
		public void goLast(){
			this.first = false;
		}
		
		//returns true if the player is first
		public boolean isFirst(){
			return this.first;
		}
		
		public String getSymbol(){
			return this.symbol;
		}

		public String getName(){
			return this.name;
		}

		public String opposite(){
			String opposite = "";
			
			if(this.getSymbol().equalsIgnoreCase("X"))
				opposite = "O";
			else if(this.getSymbol().equalsIgnoreCase("O"))
				opposite = "X";
			
			return opposite;
		}

		public boolean isABot(){
			return this.isAComputer;
		}

		public void makeBot(){
			this.isAComputer = true;
		}
}
