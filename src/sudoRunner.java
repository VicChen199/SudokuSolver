/*
Sudoku Solver

This program allows you to play a game of sudoku.
To enter a number into a box, click that box that number of times.
You can reset the board, solve the board, or generate a new puzzle
using the buttons on the right-hand corner
 */

import processing.core.PApplet;

import java.util.function.Function;

public class sudoRunner extends PApplet{

    // Initialized the sudoku board and the buttons
    SudoBoard sudoGrid;
    FunctionButton reset = new FunctionButton(850, 100, 125, 75, "Reset");
    FunctionButton solution = new FunctionButton(850, 200, 125, 75, " Ans");
    FunctionButton newPuzzle = new FunctionButton(850, 300, 125, 75, " New");

    public static void main(String [] args){
        PApplet.main("sudoRunner", args);
    }

    public void setup(){
    }

    // This initializes the display itself with the proper size
    public void settings() {
        size(1000, 1000);
        sudoGrid = new SudoBoard(700);
        sudoGrid.makeNewPuzzle();
    }

    // This displays the board, its buttons, and the values at each spot on the board
    public void draw(){
        sudoGrid.display(this);
        reset.display(this);
        newPuzzle.display(this);
        solution.display(this);
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                sudoGrid.sudoArray[r][c] = sudoGrid.sudoButtonArray[r][c].number;
            }
        }
    }

    public void mouseReleased(){

        // Adds 1 to the value that is clicked on by the user
        for(SudoButton[] row: sudoGrid.sudoButtonArray){
            for(SudoButton thisButton: row){
                if(thisButton.isOverButton(this) && thisButton.isMutable){
                    if(thisButton.number + 1 > 9)
                        thisButton.number = 0;
                    else
                        thisButton.number++;
                }
            }
        }

        // Starts the necessary processes corresponding to the button clicked
        if(reset.isOverButton(this)){
            sudoGrid.resetPuzzle();
        }
        if(newPuzzle.isOverButton(this)){
            sudoGrid.makeNewPuzzle();
        }
        if(solution.isOverButton(this)){
            sudoGrid.displaySolution();
        }
    }
}