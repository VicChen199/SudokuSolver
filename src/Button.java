import processing.core.PApplet;
public class Button {
    public int wSize;
    public int hSize;
    public int xPos;
    public int yPos;

    public Button() {
    }

    // Constructor
    public Button(int xPos, int yPos, int wSize, int hSize) {
        this.wSize = wSize;
        this.hSize = hSize;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    // This displays this button
    public void display(PApplet pa){
        if(pa.mousePressed && isOverButton(pa))
            pa.fill(255,0,0);
        else
            pa.fill(255,255,255);

        pa.rect(xPos-(wSize/2), yPos-(hSize/2), wSize, hSize);
    }

    // The mouse position is mouseX and mouseY
    // This returns the coordinates of the mouse on the Processing screen
    public boolean isOverButton(PApplet pa){
        if(pa.mouseX >= xPos - wSize/2 && pa.mouseX<=xPos+wSize/2 &&
                pa.mouseY >= yPos - hSize/2 && pa.mouseY<=yPos+hSize/2)
            return true;
        return false;
    }
}


