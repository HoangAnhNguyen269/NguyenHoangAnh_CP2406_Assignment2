import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Road extends JPanel {
    private TrafficLight light;
    private int numOfSegments; //length of road
    private final int roadWidth = 50;
    final int roadYPos;
    final int endRoadYPos;
    final int roadXPos;
    final int endRoadXPos;
    private Color lightColor = Color.green;
    private String orientation;
    String trafficDirection;
    public ArrayList<Car> carsOnRoad;


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, 1200, 1200);
        ArrayList<Road> roads = Const.getCurrentCity().getRoads();
        ArrayList<Car> cars = Const.getCurrentCity().getCars();
        for (int z = 0; z < roads.size(); z++) {
            Road r = roads.get(z);
            r.paintRoad(g);
            if (r.getOrientation().equals("vertical")) {
                for (int c = 0; c < cars.size(); c++) {
                    Car car = cars.get(c);
                    if (car.getRoadCarIsOn().equals(r)) {
                        car.paintMeVertical(g);
                    }
                }
                if (r.getTrafficLight() != null) {
                    r.paintLight(g);
                }
            } else {
                for (int c = 0; c < cars.size(); c++) {
                    Car car = cars.get(c);
                    if (car.getRoadCarIsOn().equals(r)) {
                        car.paintMeHorizontal(g);
                    }
                }
                if (r.getTrafficLight() != null) {
                    r.paintLight(g);
                }
            }
        }
    }

    // paints traffic light
    public void paintLight(Graphics g) {
        g.setColor(lightColor);
        if (getOrientation().equals("horizontal")) {
            if (getTrafficDirection().equals("east")) {
                g.fillRect(roadXPos + numOfSegments * 25 - 10, roadYPos - 20, 10, 20);
                g.setColor(Color.black);
                g.drawRect(roadXPos + numOfSegments * 25 - 10, roadYPos - 20, 10, 20);
            } else {
                g.fillRect(roadXPos, roadYPos - 20, 10, 20);
                g.setColor(Color.black);
                g.drawRect(roadXPos, roadYPos - 20, 10, 20);
            }
        } else {
            if (getTrafficDirection().equals("south")) {
                g.fillRect(roadYPos - 20, roadXPos + numOfSegments * 25 - 10, 20, 10);
                g.setColor(Color.black);
                g.drawRect(roadYPos - 20, roadXPos + numOfSegments * 25 - 10, 20, 10);
            } else {
                g.fillRect(roadYPos - 20, roadXPos, 20, 10);
                g.setColor(Color.black);
                g.drawRect(roadYPos - 20, roadXPos, 20, 10);
            }
        }
    }
    public void addACarOnRoad(Car car){
        if(this.carsOnRoad == null){
            this.carsOnRoad = new ArrayList<Car>();
            this.carsOnRoad.add(car);
        }
        else{
            this.carsOnRoad.add(car);
        }

    }
    public void removeACarOnRoad(Car car){
        this.carsOnRoad.remove(car);
    }
    public void paintRoad(Graphics g) {
        if (orientation.equals("horizontal")) {
            g.setColor(Color.black);
            g.fillRect(roadXPos, roadYPos, numOfSegments * 25, roadWidth);
            g.setColor(Color.WHITE);
            for (int j = 0; j < numOfSegments * 25; j = j + 50) { // line being drawn
                g.fillRect(roadXPos + j, roadYPos + roadWidth / 2, 30, 2);
            }
        } else {
            g.setColor(Color.black);
            g.fillRect(roadYPos, roadXPos, roadWidth, numOfSegments * 25);
            g.setColor(Color.WHITE);
            for (int j = 0; j < numOfSegments * 25; j = j + 50) { // line being drawn
                g.fillRect(roadYPos + roadWidth / 2, roadXPos + j, 2, 30);
            }
        }
    }

    public Road() {
        this.numOfSegments = 12;
        this.orientation = "horizontal";
        this.roadXPos = 100;
        this.roadYPos = 270;
        this.endRoadXPos = 100 + numOfSegments * 50;
        this.endRoadYPos = 270 + numOfSegments * 50;
        this.trafficDirection = "east";
        this.light = new TrafficLight();
        this.carsOnRoad = new ArrayList<Car>();
    }

    Road(int numOfSegments, String orientation, int xPos, int yPos, String direction) {
        super();
        this.numOfSegments = numOfSegments * 2;
        this.orientation = orientation;
        this.roadXPos = xPos;
        this.roadYPos = yPos;
        this.endRoadXPos = xPos + numOfSegments * 50;
        this.endRoadYPos = yPos + numOfSegments * 50;
        this.trafficDirection = direction;
    }

    Road(int numOfSegments, String orientation, int xPos, int yPos, String direction, TrafficLight light) {
        super();
        this.numOfSegments = numOfSegments * 2;
        this.orientation = orientation;
        this.light = light;
        this.roadXPos = xPos;
        this.roadYPos = yPos;
        this.endRoadXPos = xPos + numOfSegments * 50;
        this.endRoadYPos = yPos + numOfSegments * 50;
        this.trafficDirection = direction;

    }

    public String getOrientation() {
        return orientation;
    }

    public TrafficLight getTrafficLight() {
        return light;
    }

    public int getRoadLength() {
        return numOfSegments;
    }

    public int getRoadYPos() {
        return roadYPos;
    }

    public int getRoadXPos() {
        return roadXPos;
    }

    public int getEndRoadYPos() {
        return endRoadYPos;
    }

    public int getEndRoadXPos() {
        return endRoadXPos;
    }

    public String getTrafficDirection() {
        return trafficDirection;
    }

    public void setLightColor(Color c) {
        lightColor = c;
    }

    public Color getLightColor() {
        return lightColor;
    }

    @Override
    public String toString() {
        return "Road:" + numOfSegments / 2 + "," + orientation + "," + roadXPos + "," + roadYPos + "," + trafficDirection + "," + (light == null ? "0" : "1");
    }


}

