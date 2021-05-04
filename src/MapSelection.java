import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapSelection implements ActionListener {

    private JFrame frame = new JFrame("Map selection");

    private JButton createMap = new JButton("cuztomize a map");


    private JButton openAMap = new JButton("open a map");
    private Container bottomNavBar = new Container();
    private Container mapPool = new Container();

    public static void main(String[] args) {
        new MapSelection();
    }

    public MapSelection() {
        frame.setSize(1200, 700);
        frame.setLayout(new BorderLayout());
        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        int width = 500;
        int height = 200;
        frame.setBounds(center.x - width / 2, center.y - height / 2, width, height);
        mapPool.setLayout(new FlowLayout());
        frame.add(mapPool, BorderLayout.PAGE_END);
        initCenter();
        bottomNavBar.setLayout(new GridLayout(1, 4));
        bottomNavBar.add(createMap);
        createMap.addActionListener(this);
        bottomNavBar.add(openAMap);
        openAMap.addActionListener(this);
        frame.add(bottomNavBar, BorderLayout.NORTH);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == createMap) {
            String name = JOptionPane.showInputDialog("Please input city name:");
            if (name == null || name.equals("")) {
                return;
            }
            City city = new City(name);
            //road default in city
            Road roadStart = new Road();
            city.addRoad(roadStart);
            Const.cities.add(city);
            updateCenter();
        } else if (source == openAMap) {
            if (Const.cities.size() == 0) {
                JOptionPane.showMessageDialog(null, "please create city first.");
                return;
            }

            String[] options = new String[Const.cities.size()];
            for (int i = 0; i < Const.cities.size(); i++) {
                options[i] = Const.cities.get(i).getName();
            }
            String name = (String) JOptionPane.showInputDialog(null, "Please select a city:",
                    "Tip", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (name == null) {
                return;
            }
            Const.cityName = name;
            frame.dispose();
            new Simulator();
        } else {
            Const.cityName = ((JButton) source).getText();
            frame.dispose();
            new Simulator();
        }
    }

    private void initCenter() {
        //at first time ,will init city and road info
        Const.savedCity.clear();
        Const.cities.clear();
        Const.savedCity.addAll(SaveFile.readAllCity("city.txt"));
        for (int i = 0; i < Const.savedCity.size(); i++) {
            String name = Const.savedCity.get(i);
            City cityInfo=SaveFile.readCityInfo(name);
            Const.cities.add(cityInfo);
        }
        updateCenter();
    }

    private void updateCenter() {
        mapPool.removeAll();
        for (int i = 0; i < Const.cities.size(); i++) {
            JButton jButton = new JButton(Const.cities.get(i).getName());
            jButton.addActionListener(this);
            mapPool.add(jButton);
        }
        mapPool.revalidate();
    }
}
