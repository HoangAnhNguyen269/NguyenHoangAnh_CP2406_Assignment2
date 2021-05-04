import java.awt.*;

public class Sedan extends Car{ //Sedan is a Car
    Sedan(Road road){
        super(road);
        width = Const.LENGTH_SEDAN;
        height = Const.BREADTH_SEDAN;
    }
    public void paintMeHorizontal(Graphics g){
        g.setColor(Color.CYAN);
        g.fillRect(xPos, yPos, width, height);
    }
    public void paintMeVertical(Graphics g){
        g.setColor(Color.CYAN);
        g.fillRect(yPos, xPos, height, width);
    }
}
