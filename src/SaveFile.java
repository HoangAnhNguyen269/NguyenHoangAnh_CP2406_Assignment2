import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveFile {

    public static List<String> readAllCity(String fileName) {
        List<String> cities = new ArrayList<>();
        try (FileReader reader = new FileReader(fileName);
             BufferedReader br = new BufferedReader(reader)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                cities.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }

    public static City readCityInfo(String cityName) {
        City city = new City(cityName);
        try (FileReader reader = new FileReader(cityName + ".txt");
             BufferedReader br = new BufferedReader(reader)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Road:")) {
                    String info = line.replace("Road:", "");
                    String[] args = info.split(",");
                    if (args.length == 6) {
                        int numOfSegments = Integer.parseInt(args[0]);
                        String orientation = args[1];
                        int xPos = Integer.parseInt(args[2]);
                        int yPos = Integer.parseInt(args[3]);
                        String direction = args[4];
                        boolean hasLight = Integer.parseInt(args[5]) == 1;
                        Road road;
                        if (hasLight) {
                            road = new Road(numOfSegments, orientation, xPos, yPos, direction, new TrafficLight());
                        } else {
                            road = new Road(numOfSegments, orientation, xPos, yPos, direction);
                        }
                        city.addRoad(road);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return city;
    }

    public static void saveData(String txtPath, List<String> content) {
        FileOutputStream fileOutputStream = null;
        File file = new File(txtPath);
        try {
            if (file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            for (int i = 0; i < content.size(); i++) {
                fileOutputStream.write((content.get(i)+"\r\n").getBytes());
            }

            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
