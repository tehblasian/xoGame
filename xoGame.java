import java.util.Scanner;
public class xoGame {

	
	public static void main(String[] args){
		xoGame.run();
	}

	public static void run(){
		//variable declarations
		String playAgain = "";
		String goFirst = "";
		String single = "";
		int difficulty = 5;
		
		//scanner for input
		Scanner input = new Scanner(System.in);
		
		//welcome message
		System.out.println("	Classic Tic-Tac-Toe");
		System.out.println("	-------------------");
		
		//create players
		Player P1 = new Player();
		Player P2 = new Player("X");
		
		if(P1.getSymbol().equalsIgnoreCase("X"))
			P2.setSymbol("O");
		
		do{
		//ask if player wants to play vs human or bot
		System.out.println("Would you like to play against a bot? Enter y/n: ");
		single = input.next();
		}while(!(single.equalsIgnoreCase("y") || single.equalsIgnoreCase("n") || single.equalsIgnoreCase("yes") || single.equalsIgnoreCase("no")));
		
		if(single.equalsIgnoreCase("y") || single.equalsIgnoreCase("yes")){
			P2.setName("Tic-Tac-Troll");
			P2.makeBot();
			//alternate message
			System.out.println("	Impossible Tic-Tac-Toe");
			System.out.println("	----------------------");
		}
		else{
			System.out.println("What is Player 2's name?");
			P2.setName(input.next());
		}
		
		if(!(P1.isFirst()))
			P2.goFirst();  
		
		//create board
		xoBoard board = new xoBoard();
		
		//play
		do{
			//display number of wins for each player
			System.out.println("WINS -- " + P1.getName() + ": " + P1.getWinCount() + "\n\t " + P2.getName() + ": " + P2.getWinCount());
			//display the board once only if human goes first
			if(P1.isFirst() || !P2.isABot())
				System.out.println(board);
							
			do{
			
			if(P1.isFirst()){
				//player 1 moves
				System.out.println();
				System.out.println(P1.getName() + "'s turn: ");
				P1.move(board);
			
				//print updated board
				System.out.println(board);
				
				//check if P1 won
				if(P1.hasWon(board)){
					P1.addWin(); //add win to p1's win count
					P2.addLoss(); //add loss to p2's loss count
					System.out.println();
					System.out.println(P1.getName() + " has won the game!");
					break;
				}
				
				//check if there's a draw
				if(!board.movesLeft()){
					if(P2.isABot()){
						P2.addWin(); //a draw will be considered as a win for the computer
						P1.addLoss();
					}	
					System.out.println();
					System.out.println("We have a draw!");
					break;
				}
		
				//player 2 moves
				System.out.println();
				System.out.println(P2.getName() + "'s turn: ");
				if(!P2.isABot())
					P2.move(board);
				else
					P2.bestMove(board, difficulty);
			
				//print updated board
				System.out.println(board);
			
				//check if p2 won
				if(P2.hasWon(board)){
					P2.addWin(); //add win to p2's win count
					P1.addLoss(); //add loss to p1's loss count
					if(P2.isABot())
						System.out.println("\nThe computer has won the game!");
					else
						System.out.println("\n" + P2.getName() + " has won the game!");
					break;
				}
			}
			else if(P2.isFirst()){
				//player 2 moves
				System.out.println();
				System.out.println(P2.getName() + "'s turn: ");
				if(!P2.isABot())
					P2.move(board);
				else
					P2.bestMove(board, difficulty);
			
				//print updated board
				System.out.println(board);
				
				//check if p2 won
				if(P2.hasWon(board)){
					P2.addWin(); //add win to p2's win count
					P1.addLoss(); //add loss to p1's loss count
					if(P2.isABot())
						System.out.println("\nThe computer has won the game!");
					else
						System.out.println("\n" + P2.getName() + " has won the game!");
					break;
				}
				
				//check if there's a draw
				if(!board.movesLeft()){
					if(P2.isABot()){
						P2.addWin(); //a draw will be considered as a win for the computer
						P1.addLoss();
					}	
					System.out.println();
					System.out.println("We have a draw!");
					break;
				}
				
				//player 1 moves
				System.out.println();
				System.out.println(P1.getName() + "'s turn: ");
				P1.move(board);
			
				//print updated board
				System.out.println(board);
				
				//check if P1 won
				if(P1.hasWon(board)){
					P1.addWin(); //add win to p1's win count
					P2.addLoss(); //add loss to p2's loss count
					System.out.println();
					System.out.println(P1.getName() + " has won the game!");
					break;
				}
			}
			
		
			}while(board.movesLeft());
			
		//consume new line
		input.nextLine();
		
		//ask player to play again
		System.out.println("\nWould you like to play again? Enter y/n: ");
		playAgain = input.next();
		
		if(playAgain.equalsIgnoreCase("y") || playAgain.equalsIgnoreCase("yes"))
			board = new xoBoard();
		else if(playAgain.equalsIgnoreCase("n") || playAgain.equalsIgnoreCase("no"))
			break;
		
		//allow player to decide who goes first in the next round
		if(P1.isFirst()){
			System.out.println("Would you like to let your opponent go first? Enter y/n: ");
			goFirst = input.next();
			if(goFirst.equalsIgnoreCase("y") || goFirst.equalsIgnoreCase("yes")){
				P1.goLast();
				P2.goFirst();
			}
		}
		
		else if(P2.isFirst()){
			System.out.println("Would you like to go first? Enter y/n: ");
			goFirst = input.next();
			if(goFirst.equalsIgnoreCase("y") || goFirst.equalsIgnoreCase("yes")){
				P1.goFirst();
				P2.goLast();
			}
		}
		
		
	    }while(playAgain.equalsIgnoreCase("y") || playAgain.equalsIgnoreCase("yes"));
		
		System.out.println("Thanks for playing!");
		input.close();
			
	}//end xoGame

}
