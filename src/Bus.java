import java.awt.*;

public class Bus extends Car {
    Bus(Road road){
        super(road);
        width = Const.LENGTH_BUS;
        height = Const.BREADTH_BUS;
    }
    public void paintMeHorizontal(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(xPos, yPos, width, height);
    }
    public void paintMeVertical(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(yPos, xPos, height, width);
    }}
