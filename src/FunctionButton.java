import processing.core.PApplet;
public class FunctionButton extends Button{
    public String text;

    public FunctionButton() {
    }
    // Constructor
    public FunctionButton(int xPos, int yPos, int wSize, int hSize, String text) {
        super(xPos, yPos, wSize, hSize);
        this.text = text;
    }

    // This displays this button
    public void display(PApplet pa){
        if(pa.mousePressed && isOverButton(pa))
            pa.fill(255,0,0);
        else
            pa.fill(255,255,255);

        pa.rect(xPos-(wSize/2), yPos-(hSize/2), wSize, hSize);
        pa.textSize(hSize-26);
        pa.fill(0,0,0);
        pa.text(text,xPos-50,yPos+20);
    }
}


