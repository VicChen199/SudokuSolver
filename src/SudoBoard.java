import processing.core.PApplet;
public class SudoBoard extends PApplet {

    public int nCount = 9;  // This is many boxes there are per row and column
    public int boardSize;

    // Initializes the necessary arrays and variables for the user input
    int [][] sudoArray;
    int [][] solutionArray;
    int [][] initialArray;
    public int xPos;
    public int yPos;
    SudoButton [][] sudoButtonArray;

    public int given;

    // Constructors
    public SudoBoard() {
    }

    public SudoBoard(int[][] sudoArray) {
        this.sudoArray = sudoArray;
        given = 60;
    }

    // Initializes both the display board and the corresponding 2-D array board
    // The 2-D array board is for solving the board
    public SudoBoard(int boardSize) {
        xPos = 100;
        yPos = 100;
        this.boardSize = boardSize;
        int buttonSize = boardSize/9;
        sudoButtonArray = new SudoButton[9][9];
        sudoArray = new int[nCount][nCount];
        solutionArray = new int[nCount][nCount];
        initialArray = new int[nCount][nCount];
        given = 60;

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                sudoButtonArray[c][r] = new SudoButton(xPos + buttonSize * r,yPos + buttonSize * c, buttonSize, buttonSize, r, c);
            }
        }

        reset();
        displayBoard();
    }

    // isSafe(r,c,n) - checks if number n is safe at arr[r][c]
    // Checks the row, column, the 3x3 region spot [r][c] are occupied
    public boolean isSafe(int row, int col, int n){

        // This checks the row
        for(int i=0; i<9; i++){
            if(sudoArray[row][i] == n){
                return false;
            }
        }
        // This checks the column
        for(int i=0; i<9; i++){
            if(sudoArray[i][col] == n){
                return false;
            }
        }
        // This checks the 3x3 region
        int startR = 3*(row / 3); // These are the starting row and column of 3x3 region
        int startC = 3*(col / 3);
        for(int r = startR; r< startR+3; r++){
            for (int c = startC; c < startC+3; c++) {
                if(sudoArray[r][c] == n){
                    return false;
                }
            }
        }
        return true;
    }

    // This displays the board to the console (for debugging)
    public void displayBoard(){
        System.out.println("\nNew Board\n");
        for (int r = 0; r < nCount; r++) {
            for (int c = 0; c < nCount; c++) {
                if(sudoArray[r][c] == 0)
                    System.out.print("-");
                else
                    System.out.print(sudoArray[r][c]);
                if(c == 2 || c == 5)
                    System.out.print("|");
            }
            System.out.println();
            if(r == 2 || r == 5)
                System.out.println("-----------");
        }
    }

    // This method solves the board
    // This uses recursion until it finds a valid board
    public boolean sudoSolve(int row, int col) {
        if (row == 8 && col == 9) {
            return true;
        }
        else {
            if (col == 9) {
                col = 0;
                row++;
            }

            // Check if sudoArray[row][col] is occupied with a number
            if (sudoArray[row][col] > 0) {
                return sudoSolve(row, col + 1);
            }
            for (int num = 1; num <= 9; num++) {
                if (isSafe(row, col, num)) {
                    sudoArray[row][col] = num;
                    if (sudoSolve(row, col + 1)) {
                        return true;
                    }
                }
                sudoArray[row][col] = 0; // Backtracks if the number is invalid
            }
            return false;
        }
    }

    // Displays the board in processing
    public void display(PApplet pa){

        // Checks if the user has put in the correct solution
        boolean isValid = true;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if(sudoArray[r][c] != solutionArray[r][c])
                    isValid = false;
            }
        }
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                sudoButtonArray[r][c].isSolution = isValid;
            }
        }

        // Checks if the board is safe
        for(int r = 0; r<9; r++){
            for(int c = 0; c<9; c++){
                sudoButtonArray[r][c].isSafe= true;
                if(!isSafe(r,c, sudoButtonArray[r][c].number)){
                    sudoButtonArray[r][c].isSafe= false;
                }
            }
        }

        // Displays each button on the board
        for(SudoButton [] row: sudoButtonArray){
            for(SudoButton thissudoButton: row){
                thissudoButton.display(pa, sudoArray);
            }
        }

        // Draws the bolded lines
        for (int x = 0; x < 4; x++) {
            int x1, x2, y1, y2;
            x1 = xPos + (x * boardSize / 3) - (boardSize/9/2 + 3);
            y1 = yPos - (boardSize/9/2 + 3);
            x2 = xPos + (x * boardSize / 3) - (boardSize/9/2 + 3);
            y2 = yPos - (boardSize/9/2 + 3) + boardSize;
            pa.strokeWeight(6);
            pa.line(x1, y1, x2, y2);
        }
        for (int y = 0; y < 4; y++) {
            int x1, x2, y1, y2;
            x1 = xPos - (boardSize/9/2 + 3);
            y1 = yPos + (y * boardSize / 3) - (boardSize/9/2 + 3);
            x2 = xPos - (boardSize/9/2 + 3) + boardSize;
            y2 = yPos + (y * boardSize / 3) - (boardSize/9/2 + 3);
            pa.strokeWeight(6);
            pa.line(x1, y1, x2, y2);
        }

        displayBoard();
        System.out.println();
    }

    // This resets the board when it is cleared
    public void reset(){
        for (int r = 0; r < nCount; r++) {
            for (int c = 0; c < nCount; c++) {
                sudoButtonArray[r][c].number = 0;
                sudoArray[r][c] = 0;
            }
        }
    }

    // Resets the processing board back into its original display before user changes
    public void resetPuzzle(){
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                sudoButtonArray[r][c].number = initialArray[r][c];
                sudoArray[r][c] = initialArray[r][c];
            }
        }
    }

    // Generates an array with the solution to the randomly generated puzzle
    public void solutionArr(){
        sudoSolve(0,0);
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                solutionArray[r][c] = sudoArray[r][c];
                sudoArray[r][c] = 0;
            }
        }
    }

    // Generates a new puzzle
    public void makeNewPuzzle(){

        // Generates a clean board with no values
        reset();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                initialArray[r][c] = 0;
                sudoButtonArray[r][c].isMutable = true;
            }
        }

        // Generates random values inside the board and finds the solution to this new puzzle
        int col = 0;
        for (int i = 0; i < 3; i++) {
            for (int r = i; r < 9; r+=3) {
                sudoArray[r][col] = (int)(Math.random() * 9);
                col++;
            }
        }

        solutionArr();
        setNewInitial();
    }

    // Displays the solution to the user through the processing window
    public void displaySolution(){
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                sudoButtonArray[r][c].number = solutionArray[r][c];
            }
        }
    }

    // Generates which values will be revealed to the user
    // as well as setting other variables necessary in the rest of the project
    public void setNewInitial(){
        boolean isValid;
        boolean [][] isNotRepeated = new boolean[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                isNotRepeated[x][y] = false;
                initialArray[x][y] = 0;
                sudoButtonArray[x][y].number = 0;
                sudoButtonArray[x][y].isMutable = true;
            }
        }
        for (int i = 0; i < given; i++) {
            int r, c;
            do{
                isValid = true;
                r = (int)(Math.random() * 9);
                c = (int)(Math.random() * 9);
                if(isNotRepeated[r][c])
                    isValid = false;
            }while(!isValid);
            isNotRepeated[r][c] = true;
            initialArray[r][c] = solutionArray[r][c];
            sudoButtonArray[r][c].number = solutionArray[r][c];
            sudoButtonArray[r][c].isMutable = false;
        }
    }
}