import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) {
        MyConnectFour game = new MyConnectFour();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //print welcome message and starting board
        welcomeMessage();
        game.printOpeningGameBoard();

        // keep taking input while game is not won
        try {
            while(! game.gameWon()) {
                String userInput = reader.readLine();
                if(userInput == null){
                    System.exit(0);
                }
                //Otherwise, continue playing game
                game.playGame(userInput);
            }
        }
        catch(IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
    private static void welcomeMessage(){
        System.out.println("Welcome to Connect 4");
        System.out.println("There are 2 players Red and Yellow.");
        System.out.println("Player 1 is Red, Player 2 is Yellow");
        System.out.println("To play the game type in the number of the column you want to drop you counter in");
        System.out.println("A player wins by connecting 4 counters in a row - vertically, horizontally or diagonally");
        System.out.println();
    }
}

