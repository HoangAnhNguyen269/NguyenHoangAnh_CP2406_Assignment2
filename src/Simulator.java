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
    private JFrame frame = new JFrame(Const.cityName + " traffic sim");

    // fixed starting road on map
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

    //south container
    private JButton startSim = new JButton("start");
    private JButton exitSim = new JButton("exit");
    private JButton removeRoad = new JButton("remove last road");
    private JButton saveCity = new JButton("save city");
    private JButton back = new JButton("back city list");
    private Container south = new Container();

    //west container
    private Container west = new Container();
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
        frame.setSize(1200, 700);
        frame.setLayout(new BorderLayout());
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
        south.setLayout(new GridLayout(1, 4));
        south.add(startSim);
        startSim.addActionListener(this);
        south.add(exitSim);
        exitSim.addActionListener(this);
        south.add(removeRoad);
        removeRoad.addActionListener(this);
        south.add(saveCity);
        saveCity.addActionListener(this);
        south.add(back);
        back.addActionListener(this);
        frame.add(south, BorderLayout.SOUTH);

        //buttons on west side
        west.setLayout(new GridLayout(16, 1));
        west.add(addSedan);
        addSedan.addActionListener(this);
        west.add(addBus);
        addBus.addActionListener(this);
        west.add(addBike);
        addBike.addActionListener(this);
        west.add(addRoad);
        addRoad.addActionListener(this);
        west.add(label);
        west.add(length);
        length.addActionListener(this);
        west.add(label1);
        west.add(rate);
        rate.addActionListener(this);

        //radio buttons on west side
        selections.add(vertical);
        selections.add(horizontal);
        west.add(vertical);
        vertical.addActionListener(this);
        horizontal.setSelected(true);
        west.add(horizontal);
        horizontal.addActionListener(this);

        selections2.add(hasLight);
        selections2.add(noLight);
        west.add(hasLight);
        hasLight.addActionListener(this);
        west.add(noLight);
        noLight.addActionListener(this);
        noLight.setSelected(true);

        selections3.add(northDirection);
        selections3.add(southDirection);
        selections3.add(eastDirection);
        selections3.add(westDirection);
        west.add(northDirection);
        northDirection.addActionListener(this);
        northDirection.setEnabled(false);
        west.add(southDirection);
        southDirection.addActionListener(this);
        southDirection.setEnabled(false);
        west.add(eastDirection);
        eastDirection.addActionListener(this);
        eastDirection.setSelected(true);
        west.add(westDirection);
        westDirection.addActionListener(this);

        frame.add(west, BorderLayout.WEST);
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
                Bus bus = new Bus(roads.get(0));
                Const.getCurrentCity().addCar(bus);
                for (int x = roads.get(0).roadXPos; x < bus.getRoadCarIsOn().getRoadLength() * 50; x = x + 30) {
                    bus.setCarXPosition(x);
                    bus.setCarYPosition(bus.getRoadCarIsOn().getRoadYPos() + 5);
                    if (!bus.collision(x, bus)) {
                        frame.repaint();
                        return;
                    }
                }
            }
        }
        if (source == addBike) {
            ArrayList<Road> roads = Const.getCurrentCity().getRoads();
            if (roads.size() != 0) {
                Motorbike motorbike = new Motorbike(roads.get(0));
                Const.getCurrentCity().addCar(motorbike);
                for (int x = roads.get(0).roadXPos; x < motorbike.getRoadCarIsOn().getRoadLength() * 50; x = x + 30) {
                    motorbike.setCarXPosition(x);
                    motorbike.setCarYPosition(motorbike.getRoadCarIsOn().getRoadYPos() + 5);
                    if (!motorbike.collision(x, motorbike)) {
                        frame.repaint();
                        return;
                    }
                }
            }
        }
        if (source == addSedan) {
            ArrayList<Road> roads = Const.getCurrentCity().getRoads();
            if (roads.size() != 0) {
                Sedan sedan = new Sedan(roads.get(0));
                Const.getCurrentCity().addCar(sedan);
                sedan.setCarYPosition(sedan.getRoadCarIsOn().getRoadYPos() + 5);
                for (int x = roads.get(0).roadXPos; x < sedan.getRoadCarIsOn().getRoadLength() * 50; x = x + 30) {
                    sedan.setCarXPosition(x);
                    if (!sedan.collision(x, sedan)) {
                        frame.repaint();
                        return;
                    }
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
            frame.repaint();

        }
        if (source == exitSim) {
            System.exit(0);
        }
        if (source == back) {
            frame.dispose();
            new MapSelection();
        }
        if (source == saveCity) {
            if (!Const.savedCity.contains(Const.cityName)){
                Const.savedCity.add(Const.cityName);
                SaveFile.saveData("city.txt",Const.savedCity);
            }

            ArrayList<Road> roads = Const.getCurrentCity().getRoads();
            List<String> data=new ArrayList<>();
            for (int i = 0; i < roads.size(); i++) {
                data.add(roads.get(i).toString());
            }
            SaveFile.saveData(Const.cityName+".txt",data);
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
