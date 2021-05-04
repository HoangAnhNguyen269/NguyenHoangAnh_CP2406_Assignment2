import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CitySelection implements ActionListener {

    private JFrame frame = new JFrame("City selection");

    private JButton createCity = new JButton("Cuztomize a city");
    private JButton openACity = new JButton("Open a city");
    private JButton editACity = new JButton("Edit a city");
    private JButton simulateACity = new JButton("Simulate a city");
    private Container bottomNavBar = new Container();
    private Container cityPool = new Container();

    public static void main(String[] args) {
        new CitySelection();
    }

    public CitySelection() {
        //frame.setSize(1200, 700);
        frame.setLayout(new BorderLayout());
        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        int widthCitySelection = 800;
        int heightCitySelection = 400;
        frame.setBounds(center.x - widthCitySelection / 2, center.y - heightCitySelection / 2, widthCitySelection, heightCitySelection);
        cityPool.setLayout(new FlowLayout());
        frame.add(cityPool, BorderLayout.PAGE_END);
        initCityPool();
        bottomNavBar.setLayout(new GridLayout(1, 4));
        bottomNavBar.add(createCity);
        createCity.addActionListener(this);
        bottomNavBar.add(openACity);
        openACity.addActionListener(this);
        bottomNavBar.add(editACity);
        editACity.addActionListener(this);
        bottomNavBar.add(simulateACity);
        simulateACity.addActionListener(this);
        frame.add(bottomNavBar, BorderLayout.NORTH);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == createCity) {
            String name = JOptionPane.showInputDialog("Please input city name:");
            if (name == null || name.equals("")) {
                return;
            }
            City city = new City(name);
            //road default
            Road roadStart = new Road();
            city.addRoad(roadStart);
            Const.cities.add(city);
            updateCityPool();
        } else if (source == openACity || source == editACity || source == simulateACity) {
            if (Const.cities.size() == 0) {
                JOptionPane.showMessageDialog(null, "please create a city first.");
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

    private void initCityPool() {
        //Initialize city pool
        Const.savedCity.clear();
        Const.cities.clear();
        Const.savedCity.addAll(SaveFile.readAllCity("cities.txt"));
        for (int i = 0; i < Const.savedCity.size(); i++) {
            String name = Const.savedCity.get(i);
            City cityInfo = SaveFile.readCityInfo(name);
            Const.cities.add(cityInfo);
        }
        updateCityPool();
    }

    private void updateCityPool() {
        cityPool.removeAll();
        for (int i = 0; i < Const.cities.size(); i++) {
            JButton jButton = new JButton(Const.cities.get(i).getName());
            jButton.addActionListener(this);
            cityPool.add(jButton);
        }
        cityPool.revalidate();
    }
}
