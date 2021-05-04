import java.awt.*;

public class Motorbike extends Car{
    Motorbike(Road road){
        super(road);
        width = Const.LENGTH_MOTORBIKE;
        height = Const.BREADTH_MOTORBIKE;
    }
    public void paintMeHorizontal(Graphics g){
        g.setColor(Color.BLUE);
        g.fillRect(xPos, yPos, width, height);
    }
    public void paintMeVertical(Graphics g){
        g.setColor(Color.BLUE);
        g.fillRect(yPos, xPos, height, width);
    }
}
