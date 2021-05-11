import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Car {
    private Road road; // road that the car is on
    protected int yPos; // current position on map
    protected int xPos; // current position on map
    protected int  height;
    protected int width;
    public int speed;
    public void paintMeHorizontal(Graphics g){
    }
    public void paintMeVertical(Graphics g){
    }
    Car(Road road){
        this.road = road;
        yPos = getRoadCarIsOn().roadYPos;
        xPos = getRoadCarIsOn().roadXPos;
        this.road.addACarOnRoad(this);
        this.speed =1;
    }

    public Road getRoadCarIsOn(){
        return road;
    }

    public int getCarXPosition(){ return xPos; }
    public void setCarXPosition(int x){
        xPos = x;
    }
    public int getCarYPosition(){ return yPos; }
    public void setCarYPosition(int y){
        yPos = y;
    }
    public int getCarWidth(){return width;}

    private void setCurrentRoad(Road road){
        this.road.removeACarOnRoad(this);
        this.road = road;
        this.road.addACarOnRoad(this);
    }
    private boolean checkIfAtEndOfRoad(){
        if(getRoadCarIsOn().getTrafficDirection().equals("east") || getRoadCarIsOn().getTrafficDirection().equals("south")){
            return (xPos+width >= getRoadCarIsOn().getEndRoadXPos());
        }
        else if(getRoadCarIsOn().getTrafficDirection().equals("west") || getRoadCarIsOn().getTrafficDirection().equals("north")){
            return (xPos <= road.getRoadXPos());
        }
        else
            return true;
    }
    public boolean collision(int x, Car car){
        String direction = getRoadCarIsOn().getTrafficDirection();
        ArrayList<Car> cars = Const.getCurrentCity().getCars();
        for (int i = 0; i < cars.size(); i++) {
            Car c = cars.get(i);
            if (c.getRoadCarIsOn() == getRoadCarIsOn() && car.getCarYPosition() == c.getCarYPosition()) {
                int otherCarXPosition = c.getCarXPosition();
                int otherCarWidth = c.getCarWidth();
                if (!car.equals(c)) { // if not checking current car
                    if (x < otherCarXPosition + otherCarWidth && //left side is left  of cars right side
                            x + otherCarWidth > otherCarXPosition && (direction.equals("east") || direction.equals("south"))) { // right side right of his left side
                        return true;
                    }
                    else if (x < otherCarXPosition + otherCarWidth * 2 - 15 && x + car.getCarWidth() > otherCarXPosition &&
                            (direction.equals("west") || direction.equals("north"))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean canMoveForward(){
        String direction = getRoadCarIsOn().getTrafficDirection();
        if(xPos+width >= getRoadCarIsOn().getRoadLength()*25-25+getRoadCarIsOn().getRoadXPos() && (direction.equals("east") || direction.equals("south"))
                || xPos <= getRoadCarIsOn().getRoadXPos()+25 && ( direction.equals("west") || direction.equals("north") )) {
            if (getRoadCarIsOn().getTrafficLight() == null) {
                return true;
            }
            else {
                TrafficLight light = getRoadCarIsOn().getTrafficLight();
                return light.getCurrentColor().equals("green");
            }
        }
        return true;
    }
    private int getIndexOfCurrentRoad(){
        return Const.getCurrentCity().getRoads().indexOf(road);
    }
    /*private Road nextRoad(){
        int otherRoadXPos;
        int otherRoadYPos;
        int otherRoadEndXPos;
        int otherRoadEndYPos;
        int currentRoadXPos;
        int currentRoadYPos;
        int currentRoadEndXPos;
        int currentRoadEndYPos;
        Road currentRoad = Const.getCurrentCity().getRoads().get(getIndexOfCurrentRoad());
        ArrayList<Road> otherRoads = new ArrayList<Road>();
        ArrayList<Road> connectedRoads = new ArrayList<Road>();
        int limit = 50;
        otherRoads =Const.getCurrentCity().getRoads();
        otherRoads.remove(currentRoad);
        if(currentRoad.getOrientation().equals("vertical")){
            currentRoadXPos = currentRoad.getRoadYPos();
            currentRoadYPos = currentRoad.getRoadXPos();
            currentRoadEndXPos = currentRoad.getEndRoadYPos();
            currentRoadEndYPos = currentRoad.getEndRoadXPos();
        }
        else{
            currentRoadXPos = currentRoad.getRoadXPos();
            currentRoadYPos = currentRoad.getRoadYPos();
            currentRoadEndXPos = currentRoad.getEndRoadXPos();
            currentRoadEndYPos = currentRoad.getEndRoadYPos();
        }
        if(otherRoads!= null){
            for (int i = 0; i < otherRoads.size(); i++) {
                Road r = otherRoads.get(i);
                if(r.getOrientation().equals("horizontal")){
                    otherRoadXPos = r.getRoadXPos();
                    otherRoadYPos = r.getRoadYPos();
                    otherRoadEndXPos = r.getEndRoadXPos();
                    otherRoadEndYPos = r.getEndRoadYPos();
                }
                else{
                    otherRoadXPos = r.getRoadYPos();
                    otherRoadYPos = r.getRoadXPos();
                    otherRoadEndXPos = r.getEndRoadYPos();
                    otherRoadEndYPos = r.getEndRoadXPos();
                }
                if(currentRoad.getTrafficDirection().equals("east") && otherRoadXPos > currentRoadEndXPos-limit &&otherRoadXPos < currentRoadEndXPos+limit) {
                    connectedRoads.add(r);
                }
                else if(currentRoad.getTrafficDirection().equals("west") && otherRoadEndXPos < currentRoadXPos+limit && otherRoadEndXPos > currentRoadXPos-limit){
                    connectedRoads.add(r);
                }
                else if(currentRoad.getTrafficDirection().equals("north") && otherRoadEndYPos < currentRoadYPos+limit && otherRoadEndYPos > currentRoadYPos-limit) {
                    connectedRoads.add(r);
                }
                else if(currentRoad.getTrafficDirection().equals("south") && otherRoadYPos > currentRoadEndYPos-limit && otherRoadYPos < currentRoadEndYPos+limit){
                    connectedRoads.add(r);
                }

            }
            if(connectedRoads.size() !=0){
                Random random_method= new Random();
                int choice = random_method.nextInt(connectedRoads.size());
                return connectedRoads.get(choice);
            }
            else{return null;}
        }else{return null;}




    }*/
    private Road nextRoad(){
        int otherRoadXPos;
        int otherRoadYPos;
        int otherRoadEndXPos;
        int otherRoadEndYPos;
        int currentRoadXPos;
        int currentRoadYPos;
        int currentRoadEndXPos;
        int currentRoadEndYPos;
        Road currentRoad = Const.getCurrentCity().getRoads().get(getIndexOfCurrentRoad());
        Road nextRoad = Const.getCurrentCity().getRoads().get(0);
        ArrayList<Integer> xPositions = new ArrayList<Integer>();
        ArrayList<Integer> yPositions = new ArrayList<Integer>();
        if(currentRoad.getOrientation().equals("vertical")){
            currentRoadXPos = currentRoad.getRoadYPos();
            currentRoadYPos = currentRoad.getRoadXPos();
            currentRoadEndXPos = currentRoad.getEndRoadYPos();
            currentRoadEndYPos = currentRoad.getEndRoadXPos();
        }
        else{
            currentRoadXPos = currentRoad.getRoadXPos();
            currentRoadYPos = currentRoad.getRoadYPos();
            currentRoadEndXPos = currentRoad.getEndRoadXPos();
            currentRoadEndYPos = currentRoad.getEndRoadYPos();
        }
        ArrayList<Road> roads = Const.getCurrentCity().getRoads();
        for (int i = 0; i < roads.size(); i++) {
            Road r = roads.get(i);
            if(r != currentRoad) {

                if(r.getOrientation().equals("horizontal")){
                    otherRoadXPos = r.getRoadXPos();
                    otherRoadYPos = r.getRoadYPos();
                    otherRoadEndXPos = r.getEndRoadXPos();
                    otherRoadEndYPos = r.getEndRoadYPos();
                }
                else{
                    otherRoadXPos = r.getRoadYPos();
                    otherRoadYPos = r.getRoadXPos();
                    otherRoadEndXPos = r.getEndRoadYPos();
                    otherRoadEndYPos = r.getEndRoadXPos();
                }
                if(currentRoad.getTrafficDirection().equals("east") && otherRoadXPos > currentRoadEndXPos) {
                    xPositions.add(otherRoadXPos);
                }
                else if(currentRoad.getTrafficDirection().equals("west") && otherRoadEndXPos < currentRoadXPos ){
                    xPositions.add(otherRoadEndXPos);
                }
                else if(currentRoad.getTrafficDirection().equals("north") && otherRoadEndYPos < currentRoadYPos) {
                    yPositions.add(otherRoadEndYPos);
                }
                else if(currentRoad.getTrafficDirection().equals("south") && otherRoadYPos > currentRoadEndYPos){
                    yPositions.add(otherRoadYPos);
                }
//                else if(currentRoad.getTrafficDirection().equals("north") && otherRoadYPos > currentRoadEndYPos) {
//                    yPositions.add(otherRoadEndYPos);
//                }
//                else if(currentRoad.getTrafficDirection().equals("south") && otherRoadEndYPos < currentRoadYPos){
//                    yPositions.add(otherRoadYPos);
//                }
            }
        }
        int num;
        int num2;
        num = getCarXPosition(); //trying to find road with x position closest to this x position
        num2 = getCarYPosition(); // trying to find road with y position closest to this y position
        ArrayList<Integer> index = new ArrayList<Integer>();
        //int index2 =0;
        ArrayList<Integer> index2 = new ArrayList<Integer>();
        int difference_1 = 50; //any road within radius 100 is connected
        int difference_2 = 50;
        if(currentRoad.getTrafficDirection().equals("east") || currentRoad.getTrafficDirection().equals("west")) {
            for (int j = 0; j < xPositions.size(); j++) { // loops through every position
                int Difference_x = Math.abs(xPositions.get(j) - num);
                if (Difference_x < difference_1) { // checks if difference is getting smaller
                    index.add(j);
                    //difference_1 = Difference_x;
                }
            }
        }
        else if(currentRoad.getTrafficDirection().equals("south") || currentRoad.getTrafficDirection().equals("north")) {
            for (int j = 0; j < yPositions.size(); j++) { // loops through every position
                int Difference_y = Math.abs(yPositions.get(j) - num2);
                if (Difference_y < difference_2) { // checks if difference is getting smaller
                    index2.add(j);
                    //difference_2 = Difference_y;
                }
            }
        }
        int closestXPosition = 0;
        int closestYPosition = 0;
        Random random_method = new Random();
        if(index.size() !=0 || index2.size() !=0){
            if(currentRoad.getTrafficDirection().equals("east") || currentRoad.getTrafficDirection().equals("west")){
                int choice = random_method.nextInt(index.size());
                closestXPosition = xPositions.get(choice); //get(index.get(choice));
                System.out.println(choice);
                System.out.println(index.size());
            }
            else {
                int choice = random_method.nextInt(index2.size());
                closestYPosition = yPositions.get(choice);//get(index2.get(choice));
                System.out.println(choice);
                System.out.println(index2.size());
            }
            System.out.println(closestXPosition);

            for(int z = 0; z< roads.size(); z++){
                Road r = roads.get(z);
                if ((r.getRoadXPos() == closestXPosition || r.getEndRoadXPos() == closestXPosition) && r.getOrientation().equals("horizontal")) {
                    nextRoad = r;
                }
                else if ((r.getRoadYPos() == closestXPosition || r.getEndRoadYPos() == closestXPosition) && r.getOrientation().equals("vertical")){
                    nextRoad = r;
                }
                if ((r.getRoadYPos() == closestYPosition || r.getEndRoadXPos() == closestYPosition) && r.getOrientation().equals("horizontal")) {
                    nextRoad = r;
                }
                else if ((r.getRoadXPos() == closestYPosition || r.getEndRoadXPos() == closestYPosition) && r.getOrientation().equals("vertical")){
                    nextRoad = r;
                }
            }
            xPositions.clear();
            yPositions.clear();
            index.clear();
            index2.clear();
            return nextRoad;
        }else{ return null;}


    }


    public void move() {
        if(canMoveForward()) {
            if(road.getTrafficDirection().equals("east") || road.getTrafficDirection().equals("south")) {
                xPos += 25*speed;
            }
            else if(road.getTrafficDirection().equals("west") || road.getTrafficDirection().equals("north")){
                xPos -= 25*speed;
            }
            if (checkIfAtEndOfRoad()) {
                System.out.println("hello");
                try {
                    Road r = nextRoad();
                    if(r!= null){

                    setCurrentRoad(r);
                    if(r.getOrientation().equals("horizontal") && r.getTrafficDirection().equals("east") || r.getOrientation().equals("vertical") && r.getTrafficDirection().equals("south")) {
                        for (int x = r.getRoadXPos(); x + getCarWidth() < r.getRoadLength()*25+ r.getEndRoadXPos(); x = x + 30) {
                            setCarXPosition(x);
                            setCarYPosition(getRoadCarIsOn().getRoadYPos()+5);
                            if(!collision(x, this)){
                                return;
                            }
                        }
                    }
                    else if(r.getOrientation().equals("horizontal") && r.getTrafficDirection().equals("west") || r.getOrientation().equals("vertical") && r.getTrafficDirection().equals("north")){
                        for (int x = r.getRoadXPos() + r.getRoadLength()*25 - getCarWidth(); x > r.getRoadXPos(); x = x - 30) {
                            setCarXPosition(x);
                            setCarYPosition(getRoadCarIsOn().getRoadYPos()+5);
                            if(!collision(x, this)){
                                return;
                            }
                        }
                    }
                } else{
                        System.out.println("yahahlo");
                        speed=0;
                        Const.getCurrentCity().getCars().remove(this); // •	A vehicle disappears from the city when it reaches the end of a road
                    }
                }
                catch (IndexOutOfBoundsException e){

                   /* setCurrentRoad(road);
                    xPos = road.getRoadXPos();
                    yPos = road.getRoadYPos() + 5;*/


                }
            }
        }

    }

}
