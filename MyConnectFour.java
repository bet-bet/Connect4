public class MyConnectFour {
    private int userInputInt;
    Board board = new Board();
    private boolean gameWonState = false;
    private boolean validInput = false;

    private int currentPlayer = 1;

    public MyConnectFour(){
    }

    public void playGame(String userInput){
        handleUserInput(userInput);
        // if valid input not given, wait until valid input is given to continue game
        if(!this.validInput) {
            return;
        }
        this.validInput = false;

        char player1Char = 'r';
        char player2Char = 'y';

        //player 1 move - place counter and check for win condition
            if(currentPlayer == 1){
                this.board.placeCounter(this.currentPlayer, this.userInputInt);
                int player1LongestCounter = this.board.returnLongestCounterStreak(player1Char);
                if(player1LongestCounter >= 4){
                    this.board.printBoard();
                    System.out.println("You Have Won!!!");
                    this.gameWonState = true;
                    return;
                }
            // if not won, update player and print board
            currentPlayer = 2;
            this.board.printBoard();
            return;
        }

        // player 2 - place counter and check for win condition
        this.board.placeCounter(this.currentPlayer, this.userInputInt);
        int player2LongestCounter = this.board.returnLongestCounterStreak(player2Char);
        System.out.println(player2LongestCounter);
        if(player2LongestCounter >= 4){
            this.board.printBoard();
            System.out.println("You Have Won!!!");
            this.gameWonState = true;
        }
        // if not won, update player and print board
        currentPlayer = 1;
        this.board.printBoard();
    }

    public boolean gameWon(){
        return gameWonState;
    }

    // handle user input by converting input string to int
    // also handles invalid input
    private void handleUserInput(String input){
        this.validInput = false;
        this.userInputInt = -1;

        // try to convert user input string to int
        try{
            this.userInputInt = Integer.parseInt(input);
        }catch(NumberFormatException ignored){
        }

        //if user input is in a valid range
        if(this.userInputInt >=1 && this.userInputInt <= this.board.getBoardWidth()){
            // get current max row filled at given column.
            // if column is saturated move is not valid, ask user for a different input.
            int currentMaxColAtIndex = this.board.getCurrentColIndexes()[userInputInt-1];
            if(currentMaxColAtIndex < this.board.getBoardHeight()-1) {
                this.validInput = true;
            }
            else{
                System.out.println("This column is full, please choose another move.");
                this.board.printBoard();
            }
        }
        // if user input is char or out of range ask user for different move.
        else{
            System.out.println("Please enter a number between 1 and 7 to make a move.");
            this.board.printBoard();
        }
    }

    public void printOpeningGameBoard(){
        this.board.printBoard();
    }
}
