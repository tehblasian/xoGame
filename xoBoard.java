package xoGame;
import java.util.*;
public class xoBoard {

	private String[][] board;
	private int movesLeft = 9;
	
	public xoBoard(){
		String[][] board = {
				{"","",""},
				{"","",""},
				{"","",""}
		};
		
		this.board = board;
	}
	
	//add a symbol to the board
	public boolean add(int row, int col, String symbol){
		if(row >= board.length || col >= board.length){
			System.out.println("Cannot complete move -- Invalid coordinate entry.");
			return false;
		}
		if(this.board[row][col].isEmpty()){
			this.board[row][col] = symbol;
			movesLeft--;
			return true;
		}
		else{
			System.out.println("Cannot complete move -- The space has been played.");
			return false;
		}
	}
	
	//to remove a symbol from the board
	public void remove(int row, int col){
		if(row >= board.length || col >= board.length)
			System.out.println("Cannot complete move -- Invalid coordinate entry.");
		else
			this.board[row][col] = "";
		
		this.movesLeft++;
	}

	
	//check if there is a win
	public boolean won(String symbol){
		boolean won = false;
		
		//check rows
		if(!won)
			for(int row = 0; row < 3; row++)
				if(board[row][0].equalsIgnoreCase(board[row][1]) && board[row][1].equalsIgnoreCase(board[row][2]))
					if(board[row][0].equalsIgnoreCase(symbol)){
						won = true;
						break;
					}
		
		//check columns
		if(!won)
			for(int col = 0; col < 3; col++)
				if(board[0][col].equalsIgnoreCase(board[1][col]) && board[1][col].equalsIgnoreCase(board[2][col]))
					if(board[0][col].equalsIgnoreCase(symbol)){
						won = true;
						break;
					}
		
		//check diagonals
		if(!won)
			if(board[0][0].equalsIgnoreCase(board[1][1]) && board[1][1].equalsIgnoreCase(board[2][2]))
				if(board[0][0].equalsIgnoreCase(symbol))
					won = true;
		if(!won)
			if(board[0][2].equalsIgnoreCase(board[1][1]) && board[1][1].equalsIgnoreCase(board[2][0]))
				if(board[0][2].equalsIgnoreCase(symbol))
					won = true;
		
		return won;
		
		
	}
	
	//returns number of turns left
	public boolean movesLeft(){
		return (this.movesLeft > 0);
	}
	
	//displays the board
	public String toString(){
		String board = "";
		for(int i = 0; i < this.board.length; i++){
			for(int j = 0; j < this.board[0].length; j++){
				if(j == 1 || j == 2)
					board = board + "|";
				board = board + "	" + this.board[i][j] + "	";
			}
		if(i == 0 || i == 1)	
			board = board + "\n-------------------------------------------------\n";
		}
		
		return "\n"+board;
	}

	//checks if a space is empty
	public boolean isEmpty(int row, int col){
		return (this.board[row][col].isEmpty());
	}

	//returns a string array containing all available spaces
	public ArrayList<String> movesAvail(){
		
		ArrayList<String> moves = new ArrayList<String>();
		//String[] moves = new String[this.movesLeft];
		
		for(int row = 0; row < 3; row++)
			for(int col = 0; col < 3; col++)
				if(this.board[row][col].isEmpty()){
					//moves[counter] = row + "" + col;
					moves.add((row + "" + col));
			
				}
		
		return moves;
		}
	
	//returns a string containing all spaces occupied by a player
	public String[] getSquares(Player player){
		int count = 0;
		int length = 0;
		if(player.isFirst())
			length = this.movesLeft/2 + 1;
		else
			length = this.movesLeft/2;
			
		String[] squares = new String[length];
		
		for(int row = 0; row < this.board.length; row++)
			for(int col = 0; col < this.board.length; col++){
				if(this.board[row][col].equalsIgnoreCase(player.getSymbol()))
					squares[count] = row + "" + col;
			}
		
		return squares;
	}
	
	//returns a copy of the game board
	public String[][] copy(){
		String[][] copy = new String[this.board.length][this.board.length];
		
		for(int row = 0; row < this.board.length; row++)
			for(int col = 0; col < this.board.length; col++){
				copy[row][col] = this.board[row][col];
			}
		
		return copy;
	}

	//returns a copy of the board object
	public xoBoard(xoBoard copy){
		this.movesLeft = copy.movesLeft;
		String[][] clone = {
				{"","",""},
				{"","",""},
				{"","",""}
		};
		for(int row = 0; row < 3; row++)
			for(int col = 0; col < 3; col++)
				clone[row][col] = copy.board[row][col];
		
		this.board = clone;
	}
	
	//checks if the game is over
	public boolean gameOver(){
		boolean over = false;
		if(this.won("X"))
			over = true;
		else if(this.won("O"))
			over = true;
		else if(!this.movesLeft())
			over = true;
		
		return over;
	}
    //////////////////
	//METHODS FOR AI//
	//////////////////
	private int minimax(xoBoard board, Player computer, int alpha, int beta, boolean isCom){
		
		//variable declarations
		int row = 0;
		int col = 0;
		int best = -1;
		//return the score when a terminal state is reached
		int score = score(board, computer);
		
		if(score == 10)
			return score;
		if(score == -10)
			return score;
		if(board.movesLeft() == false)
			return 0;
		
		//get all the moves available to computer
		ArrayList<String> movesAvail = board.movesAvail();
		
		if(isCom){
			int bestScore = Integer.MIN_VALUE;
			for(int i = 0; i < movesAvail.size(); i++){
				//get move
				row = Integer.parseInt(Character.toString(movesAvail.get(i).charAt(0)));
				col = Integer.parseInt(Character.toString(movesAvail.get(i).charAt(1)));		
				//make move
				board.add(row, col, computer.getSymbol());
				//recursively call minimax to get max value
				bestScore = Math.max(bestScore, minimax(board, computer, alpha, beta, !isCom));
				//undo move
				board.remove(row, col);
				//alpha beta pruning
				alpha = Math.max(alpha, bestScore);
				if(beta <= alpha)
					break;
			}
		best = bestScore;
		}
		else if(!isCom){
			int bestScore = Integer.MAX_VALUE;
			for(int i = 0; i < movesAvail.size(); i++){
				//get move
				row = Integer.parseInt(Character.toString(movesAvail.get(i).charAt(0)));
				col = Integer.parseInt(Character.toString(movesAvail.get(i).charAt(1)));	
				//make move
				board.add(row, col, computer.opposite());
				//recursively call minimax to get min value
				bestScore = Math.min(bestScore, minimax(board, computer, alpha, beta, !isCom));
				//undo move
				board.remove(row, col);
				//alpha beta pruning
				beta = Math.min(beta, bestScore);
				if(beta <= alpha)
					break;
			}	
		best = bestScore;
		}
		
		return best;
		
		
	
		
}//end minimax method
	
	//returns the score of the terminal state
	public int score(xoBoard board, Player computer){
		int score = 0;
	
			if(computer.hasWon(board))
				score = 10;
			else if(board.won(computer.opposite()))
				score = -10;
		
			else if(board.won("X") == false && board.won("O") == false)
				score = 0;
		
			return score;
	}

	//returns the best move for the computer
	public String bestMove(Player computer, int difficulty){
		String move = "";
		String bestMove = "";
		int bestScore = -1;
		int bestRow = -1;
		int bestCol = -1;
		int row = 0;
		int col = 0;
		
		//to store moves available to computer
		ArrayList<String> moveBank = new ArrayList<String>();
		
		//create a test board to operate on
		xoBoard test = new xoBoard(this);
		//get list of moves available on the board
		ArrayList<String> movesAvail = test.movesAvail();
		
		//get moves
		for(int i = 0; i < movesAvail.size(); i++){
					//get move
					row = Integer.parseInt(Character.toString(movesAvail.get(i).charAt(0)));
					col = Integer.parseInt(Character.toString(movesAvail.get(i).charAt(1)));
					//make move
					test.add(row, col, computer.getSymbol());
					//evaluate move
					int moveScore = minimax(test, computer, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
					//add move to list of available moves
					moveBank.add((row + "" + col));
					//undo move
					test.remove(row, col);
					//update best move, store in moveBank, from best to worst
					if(moveScore > bestScore){
						bestRow = row;
						bestCol = col;
						bestScore = moveScore;
						bestMove = bestRow + "" + bestCol;
						moveBank.add(0, bestMove);
					}
				}
		
		//if difficulty is set to max (5)
		if(difficulty == 5)
			move = bestMove;
		
		//if difficulty is set to hard (4)
		else if(difficulty == 4){
			if(Math.random() * 100 <= 85) //40% chance of picking the best available move
				move = moveBank.get(0);
			else{
				if(moveBank.size() >= 2)
					move = moveBank.get(1);
				else
					move = moveBank.get(0);
			}
		}
		//if difficulty is set to intermediate (3)
		else if(difficulty == 3){
			if(Math.random() * 100 <= 60) //40% chance of picking the best available move
				move = moveBank.get(0);
			else{
				if(moveBank.size() >= 2)
					move = moveBank.get(1);
				else
					move = moveBank.get(0);
			}
		}
		//if difficulty is set to novice (2)
		else if(difficulty == 2){
			if(Math.random() * 100 <= 40) //40% chance of picking the best available move
				move = moveBank.get(0);
			else{
				if(moveBank.size() >= 2)
					move = moveBank.get(1);
				else
					move = moveBank.get(0);
			}
		}
		
		//if difficulty is set to easy
		else if(difficulty == 1){
			int index = (int)(Math.random() * movesAvail.size());
			move = movesAvail.get(index);
		}
		
		return move;
		
	}
	
	

}//end xoBoard
