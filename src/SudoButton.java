import processing.core.PApplet;

public class SudoButton extends Button{

    // Initializes necessary variables
    public int number;
    public boolean isSafe;
    public int rowIndex; //board position
    public int colIndex; //board position

    public boolean isSolution;

    public boolean isMutable = true;

    public SudoButton() {

    }

    // Constructor
    public SudoButton(int xPos, int yPos, int wSize, int hSize) {
        super(xPos, yPos, wSize, hSize);
        number = 0;
    }

    // colIndex and rowIndex are passed in swapped order because when declared,
    // the xValue are columns and yValues are rows
    public SudoButton(int xPos, int yPos, int wSize, int hSize, int colIndex, int rowIndex) {
        super(xPos, yPos, wSize, hSize);
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        number = 0;
    }

    // This displays the button
    public void display(PApplet pa, int board[][]){
        pa.strokeWeight(1);
        if(pa.mousePressed && isOverButton(pa)) {
            pa.fill(255, 0, 0);
        }
        else
            pa.fill(255,255,255);

        pa.rect(xPos-(wSize/2), yPos-(hSize/2), wSize, hSize);

        // Changes the color of the number depending on if the board is complete or not
        if(number != 0){
            board[rowIndex][colIndex] = number;

            if(isSolution){
                pa.fill(0, 255, 0);
                pa.textSize(hSize);
            }
            else if(isSafe) {
                pa.fill(0, 0, 0);
                pa.textSize(hSize);
                if (isSafe && board[rowIndex][colIndex] == number) {
                    pa.fill(0, 0, 0);
                }
            }
            else if (!isSafe){
                pa.fill(255,0,0);
                pa.textSize(hSize);
            }
            pa.text(number,xPos-wSize/3, yPos+hSize/4);
        }

        if(number == 0){
            board[rowIndex][colIndex] = 0;
        }
    }
}
