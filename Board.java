import java.util.Arrays;

public class Board {
    private char[][] board;
    private final int boardHeight;
    private final int boardWidth;
    private final int maxRowIndex;
    private final int maxColIndex;
    private int[] currentColIndexes;

    public Board() {
        this.board = new char[6][7];
        this.currentColIndexes = new int[7];
        // pre-fill currentColIndexes with index out of range
        Arrays.fill(this.currentColIndexes, -1);
        this.boardHeight = board.length;
        this.boardWidth = board[0].length;
        this.maxColIndex = boardHeight - 1;
        this.maxRowIndex = board[0].length - 1;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public int[] getCurrentColIndexes() {
        return currentColIndexes;
    }

    // print board with different chars for different player moves
    public void printBoard() {
        for (int i = 0; i <= maxColIndex; i++) {
            for (int j = 0; j < boardWidth; j++) {
                if (board[maxColIndex - i][j] == 'r') {
                    System.out.print("| r ");
                } else if (board[maxColIndex - i][j] == 'y') {
                    System.out.print("| y ");
                } else {
                    System.out.print("|   ");
                }
            }
            System.out.println("|");
        }
        System.out.println("  1   2   3   4   5   6   7");
    }

    // find next empty index of column
    public void findNextEmptySpace(int position) {
        int boardPosition = position - 1;
        currentColIndexes[boardPosition] += 1;
    }

    // place counter dependent on player
    public void placeCounter(int player, int position) {
        findNextEmptySpace(position);
        int boardPosition = position - 1;
        int colIndex = currentColIndexes[boardPosition];

        if (player == 1) {
            board[colIndex][boardPosition] = 'r';
            return;
        }

        board[colIndex][boardPosition] = 'y';
    }

    // evaluate longest counter streak for horizontal, vertical and diagonal axis
    public int returnLongestCounterStreak(char playerChar) {
        int longestRow = getLongestRowCount(playerChar);
        int longestCol = getLongestColCount(playerChar);
        int longestDiag = getLongestDiagCount(playerChar);

        int longestCount = Math.max(longestRow,longestCol);
        longestCount = Math.max(longestCount, longestDiag);
        return longestCount;
    }

    // get longest streak for row
    private int getLongestRowCount(char playerChar) {
        int longestRowCount = 0;
        int rowCount = 0;
        // loop through each row in grid
        // update LongestRowCount if new longest streak found
        for (int i = 0; i <= maxColIndex; i++) {
            for (int j = 0; j <= maxRowIndex; j++) {
                if (this.board[i][j] == playerChar) {
                    rowCount += 1;
                }
                else{
                if (rowCount > longestRowCount) {
                    longestRowCount = rowCount;
                }
                rowCount = 0;
            }
            }
        }
        return longestRowCount;
    }

    private int getLongestColCount(char playerChar) {
        int longestColCount = 0;
        int colCount = 0;
        // loop through each column on grid
        // update longestColCount if new longest found
        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < maxColIndex; j++) {
                if (this.board[j][i] == playerChar) {
                    colCount += 1;
                }
                else {
                    if (colCount > longestColCount) {
                        longestColCount = colCount;
                    }
                    colCount = 0;
                }
            }
        }
        return longestColCount;
    }


    private int getLongestDiagCount(char playerChar) {
        int longestDiagCount = 0;

        // checking bottom left to top right diagonal
        // checking 0,0 to 0,7 start positions
        for (int i = 0; i <= boardWidth; i++) {
            int longestForwardDiagCount = getLongestDiagonalFromPosition(playerChar, 0, i);
            if (longestForwardDiagCount > longestDiagCount) {
                longestDiagCount = longestForwardDiagCount;
            }
        }
        // checking 0,0 to 6,0 start positions
        for (int i = 0; i <= boardHeight; i++) {
            int longestForwardDiagCount = getLongestDiagonalFromPosition(playerChar, i, 0);
            if (longestForwardDiagCount > longestDiagCount) {
                longestDiagCount = longestForwardDiagCount;
            }
        }

        // checking top left to bottom right diagonal
        // checking 0,6 to 0,0 starting positions
        for (int i = 0; i <= boardHeight; i++) {
            int longestReverseDiagCount = getLongestDiagonalFromPositionRL(playerChar, 0, i);
            if (longestReverseDiagCount > longestDiagCount) {
                longestDiagCount = longestReverseDiagCount;
            }
        }
        // checking 0,6 to 7,6 starting positions
        for (int i = 0; i <= boardWidth; i++) {
            int longestReverseDiagCount = getLongestDiagonalFromPositionRL(playerChar, i, boardHeight-1);
            if (longestReverseDiagCount > longestDiagCount) {
                longestDiagCount = longestReverseDiagCount;
            }
        }
            return longestDiagCount;
    }

        //
        private int getLongestDiagonalFromPosition (char playerChar, int startRow, int startCol){
            int count = 0;
            int longestCount = 0;
            for (int i = 0; i < boardWidth; i++) {
                // out of bounds error handling
                if(startRow + i >= boardHeight){
                    break;
                }
                if(startCol + i >= boardWidth){
                    break;
                }

                // for one row over and one column up
                // if player token found, update count
                // if not player token, update longestCount and reset count
                if (board[startRow + i][startCol + i] == playerChar) {
                    count += 1;
                } else {
                    if (count > longestCount) {
                        longestCount = count;
                    }
                    count = 0;
                }
            }
            if (count > longestCount) {
                longestCount = count;
            }
            return longestCount;
        }


    private int getLongestDiagonalFromPositionRL(char playerChar, int startRow, int startCol){
        int count = 0;
        int longestCount = 0;
        for(int i=0; i< boardWidth; i++){
            // out of bounds error handling
            if(startRow + i >= boardHeight){
                break;
            }
            if(startCol - i < 0){
                break;
            }

            // for one row over and one column down
            // if player token found, update count
            // if not player token, update longestCount and reset count
            if(board[startRow+i][startCol-i] == playerChar){
                count += 1;
            }
            else {
                if (count > longestCount) {
                    longestCount = count;
                }
                count = 0;
            }
        }
        if (count > longestCount) {
            longestCount = count;
        }
        return longestCount;
    }
}