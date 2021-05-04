import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class Simulator implements ActionListener, Runnable, MouseListener {


    private int x, y;
    private boolean running = false;
    private JFrame frame = new JFrame(Const.cityName + " traffic simulator");
    private int getX() {
        return x;
    }
    private int getY() {
        return y;
    }
    //north container
    private JLabel info = new JLabel("click on screen to select x,y position");
    private JLabel labelXPosField = new JLabel("Road x position");
    private JTextField xPosField = new JTextField("0");
    private JLabel labelYPosField = new JLabel("Road y position");
    private JTextField yPosField = new JTextField("0");
    private Container north = new Container();


    //south container (navigation bar)
    private JButton startSim = new JButton("start");
    private JButton exitSim = new JButton("exit");
    private JButton removeRoad = new JButton("remove last road");
    private JButton saveCity = new JButton("save city");
    private JButton back = new JButton("back city pool");
    private Container navBar = new Container();

    //west container
    private Container westNavBar = new Container();
    private JButton addSedan = new JButton("add sedan");
    private JButton addBus = new JButton("add bus");
    private JButton addBike = new JButton("add motorbike");
    private JButton addRoad = new JButton("add road");
    //road orientation selection
    private ButtonGroup selections = new ButtonGroup();
    private JRadioButton horizontal = new JRadioButton("horizontal");
    private JRadioButton vertical = new JRadioButton("vertical");
    //has traffic light selection
    private ButtonGroup selections2 = new ButtonGroup();
    private JRadioButton hasLight = new JRadioButton("traffic light(true)");
    private JRadioButton noLight = new JRadioButton("traffic light(false)");
    //road length
    private JLabel label = new JLabel("Enter road length");
    private JTextField length = new JTextField("5");
    private JLabel label1 = new JLabel("Enter update rate");
    private JTextField rate = new JTextField("300");

    //traffic direction
    private ButtonGroup selections3 = new ButtonGroup();
    private JRadioButton northDirection = new JRadioButton("north");
    private JRadioButton southDirection = new JRadioButton("south");
    private JRadioButton westDirection = new JRadioButton("west");
    private JRadioButton eastDirection = new JRadioButton("east");

    public Simulator() {
        //frame.setSize(1200, 700);
        frame.setLayout(new BorderLayout());
        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        int widthCitySelection = 1200;
        int heightCitySelection = 700;
        frame.setBounds(center.x - widthCitySelection / 2, center.y - heightCitySelection / 2, widthCitySelection, heightCitySelection);
        frame.add(Const.getCurrentCity().getRoads().get(0), BorderLayout.CENTER);
        Const.getCurrentCity().getRoads().get(0).addMouseListener(this);
        //north side info
        north.setLayout(new GridLayout(1, 5));
        north.add(info);
        north.add(labelXPosField);
        north.add(xPosField);
        north.add(labelYPosField);
        north.add(yPosField);
        frame.add(north, BorderLayout.NORTH);

        //buttons on the south side
        navBar.setLayout(new GridLayout(1, 4));
        navBar.add(startSim);
        startSim.addActionListener(this);
        navBar.add(exitSim);
        exitSim.addActionListener(this);
        navBar.add(removeRoad);
        removeRoad.addActionListener(this);
        navBar.add(saveCity);
        saveCity.addActionListener(this);
        navBar.add(back);
        back.addActionListener(this);
        frame.add(navBar, BorderLayout.SOUTH);

        //buttons on west side
        westNavBar.setLayout(new GridLayout(16, 1));
        westNavBar.add(addSedan);
        addSedan.addActionListener(this);
        westNavBar.add(addBus);
        addBus.addActionListener(this);
        westNavBar.add(addBike);
        addBike.addActionListener(this);
        westNavBar.add(addRoad);
        addRoad.addActionListener(this);
        westNavBar.add(label);
        westNavBar.add(length);
        length.addActionListener(this);
        westNavBar.add(label1);
        westNavBar.add(rate);
        rate.addActionListener(this);

        //radio buttons on west side
        selections.add(vertical);
        selections.add(horizontal);
        westNavBar.add(vertical);
        vertical.addActionListener(this);
        horizontal.setSelected(true);
        westNavBar.add(horizontal);
        horizontal.addActionListener(this);

        selections2.add(hasLight);
        selections2.add(noLight);
        westNavBar.add(hasLight);
        hasLight.addActionListener(this);
        westNavBar.add(noLight);
        noLight.addActionListener(this);
        noLight.setSelected(true);

        selections3.add(northDirection);
        selections3.add(southDirection);
        selections3.add(eastDirection);
        selections3.add(westDirection);
        westNavBar.add(northDirection);
        northDirection.addActionListener(this);
        northDirection.setEnabled(false);
        westNavBar.add(southDirection);
        southDirection.addActionListener(this);
        southDirection.setEnabled(false);
        westNavBar.add(eastDirection);
        eastDirection.addActionListener(this);
        eastDirection.setSelected(true);
        westNavBar.add(westDirection);
        westDirection.addActionListener(this);

        frame.add(westNavBar, BorderLayout.WEST);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.repaint();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (horizontal.isSelected()) {
            northDirection.setEnabled(false);
            southDirection.setEnabled(false);
            eastDirection.setEnabled(true);
            westDirection.setEnabled(true);
        } else if (vertical.isSelected()) {
            eastDirection.setEnabled(false);
            westDirection.setEnabled(false);
            northDirection.setEnabled(true);
            southDirection.setEnabled(true);
        }
        if (source == startSim) {
            if (!running) {
                running = true;
                Thread t = new Thread(this);
                t.start();
            }
        }
        if (source == removeRoad) {
            if (Const.getCurrentCity().getRoads().size() > 1) {
                Const.getCurrentCity().getRoads().remove(Const.getCurrentCity().getRoads().size() - 1);
                frame.repaint();
            }
        }
        if (source == addBus) {
            ArrayList<Road> roads = Const.getCurrentCity().getRoads();
            if (roads.size() != 0) {
                int currentPosi = 0;
                Bus bus = new Bus(roads.get(0));
                Const.getCurrentCity().addCar(bus);
                for (int x = roads.get(0).roadXPos; x < bus.getRoadCarIsOn().getRoadLength() * 33; x = x + 30) {//limit car based on the road length
                    bus.setCarXPosition(x);
                    bus.setCarYPosition(bus.getRoadCarIsOn().getRoadYPos() + 5);
                    if (!bus.collision(x, bus)) {
                        frame.repaint();
                        return;
                    }
                    currentPosi = x;
                }
                if(currentPosi + 30 > bus.getRoadCarIsOn().getRoadLength() * 33){
                    JOptionPane.showMessageDialog(null,"Can not add more car");
                }

            }
        }
        if (source == addBike) {
            ArrayList<Road> roads = Const.getCurrentCity().getRoads();
            if (roads.size() != 0) {
                int currentPosi = 0;
                Motorbike motorbike = new Motorbike(roads.get(0));
                Const.getCurrentCity().addCar(motorbike);
                for (int x = roads.get(0).roadXPos; x < motorbike.getRoadCarIsOn().getRoadLength() * 33; x = x + 30) {//limit car based on the road length
                    motorbike.setCarXPosition(x);
                    motorbike.setCarYPosition(motorbike.getRoadCarIsOn().getRoadYPos() + 5);
                    if (!motorbike.collision(x, motorbike)) {
                        frame.repaint();
                        return;
                    }
                    currentPosi = x;
                }
                if(currentPosi + 30 > motorbike.getRoadCarIsOn().getRoadLength() * 33){
                    JOptionPane.showMessageDialog(null,"Can not add more car");
                }
            }
        }
        if (source == addSedan) {
            ArrayList<Road> roads = Const.getCurrentCity().getRoads();
            
                if (roads.size() != 0) {
                    int currentPosi = 0;
                Sedan sedan = new Sedan(roads.get(0));
                Const.getCurrentCity().addCar(sedan);
                sedan.setCarYPosition(sedan.getRoadCarIsOn().getRoadYPos() + 5);
                for (int x = roads.get(0).roadXPos; x < sedan.getRoadCarIsOn().getRoadLength() * 33; x = x + 30) { //limit car based on the road length
                    sedan.setCarXPosition(x);
                    if (!sedan.collision(x, sedan)) {
                        frame.repaint();
                        return;
                    }
                    currentPosi = x;
                }
                if(currentPosi + 30 > sedan.getRoadCarIsOn().getRoadLength() * 33){
                    JOptionPane.showMessageDialog(null,"Can not add more car");
                }
            }


        }
        if (source == addRoad) {
            int roadLength = 5;
            String orientation = "horizontal";
            String direction = "east";
            int xPos = 0;
            int yPos = 0;
            Boolean lightOnRoad = false;
            if (vertical.isSelected()) {
                orientation = "vertical";
            } else if (horizontal.isSelected()) {
                orientation = "horizontal";
            }
            if (hasLight.isSelected()) {
                lightOnRoad = true;
            } else if (noLight.isSelected()) {
                lightOnRoad = false;
            }
            if (eastDirection.isSelected()) {
                direction = "east";
            } else if (westDirection.isSelected()) {
                direction = "west";
            } else if (northDirection.isSelected()) {
                direction = "north";
            } else if (southDirection.isSelected()) {
                direction = "south";
            }

            if (orientation.equals("horizontal")) {
                yPos = Integer.parseInt(yPosField.getText());
                xPos = Integer.parseInt(xPosField.getText());
            } else if (orientation.equals("vertical")) {
                xPos = Integer.parseInt(yPosField.getText());
                yPos = Integer.parseInt(xPosField.getText());
            }
            try {
                roadLength = Integer.parseInt(length.getText());
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "road length needs an integer");
                length.setText("5");
            }
            try {
                Const.updateSpeed = Integer.parseInt(rate.getText());
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "update rate an integer");
                rate.setText("300");
            }
            if (lightOnRoad) {
                Road road = new Road(roadLength, orientation, xPos, yPos, direction, new TrafficLight());
                Const.getCurrentCity().addRoad(road);
            } else {
                Road road = new Road(roadLength, orientation, xPos, yPos, direction);
                Const.getCurrentCity().addRoad(road);
            }
            if(roadLength<2 || roadLength>5){
                JOptionPane.showMessageDialog(null,"A road is at least twice the length of a bus (at most five times)");
            }
            else{frame.repaint();}


        }
        if (source == exitSim) {
            System.exit(0);
        }
        if (source == back) {
            frame.dispose();
            new CitySelection();
        }
        if (source == saveCity) {
            if (!Const.savedCity.contains(Const.cityName)){
                Const.savedCity.add(Const.cityName);
                SaveFile.saveData("cities.txt",Const.savedCity);
            }

            ArrayList<Road> roads = Const.getCurrentCity().getRoads();
            List<String> data=new ArrayList<>();
            for (int i = 0; i < roads.size(); i++) {
                data.add(roads.get(i).toString());
            }
            SaveFile.saveData(Const.cityName +".txt",data);
            JOptionPane.showMessageDialog(null, "save city success");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        xPosField.setText(Integer.toString(getX()));
        yPosField.setText(Integer.toString(getY()));
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void run() {
        boolean carCollision = false;
        ArrayList<Boolean> trueCases = new ArrayList<Boolean>();
        while (running) {
            try {
                Thread.sleep(Const.updateSpeed);
            } catch (Exception ignored) {
            }
            ArrayList<Road> roads = Const.getCurrentCity().getRoads();
            for (int j = 0; j < roads.size(); j++) {
                Road r = roads.get(j);
                TrafficLight l = r.getTrafficLight();
                if (l != null) {
                    l.operate();
                    if (l.getCurrentColor().equals("red")) {
                        r.setLightColor(Color.red);
                    } else {
                        r.setLightColor(Color.GREEN);
                    }
                }

            }
            ArrayList<Car> cars = Const.getCurrentCity().getCars();
            for (int i = 0; i < cars.size(); i++) {
                Car currentCar = cars.get(i);
                String direction = currentCar.getRoadCarIsOn().getTrafficDirection();
                if (!currentCar.collision(currentCar.getCarXPosition() + 30, currentCar) && (direction.equals("east") || direction.equals("south"))
                        || !currentCar.collision(currentCar.getCarXPosition(), currentCar) && (direction.equals("west") || direction.equals("north"))) {
                    currentCar.move();
                } else {
                    for (int z = 0; z < cars.size(); z++) {
                        Car otherCar = cars.get(z);
                        if (otherCar.getCarYPosition() != currentCar.getCarYPosition()) {
                            if (currentCar.getCarXPosition() + currentCar.getCarWidth() < otherCar.getCarXPosition()) {
                                trueCases.add(true); // safe to switch lane
                            } else {
                                trueCases.add(false); // not safe to switch lane
                            }
                        }
                    }
                    for (int l = 0; l < trueCases.size(); l++) {
                        if (!trueCases.get(l)) {
                            carCollision = true;
                            break;
                        }
                    }
                    if (!carCollision) {
                        currentCar.setCarYPosition(currentCar.getRoadCarIsOn().getRoadYPos() + 30);
                    }
                    for (int m = 0; m < trueCases.size(); m++) {
                        trueCases.remove(m);
                    }
                    carCollision = false;
                }

            }
            frame.repaint();

        }
    }
}
