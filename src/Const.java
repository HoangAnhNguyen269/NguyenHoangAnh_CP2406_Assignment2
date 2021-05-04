import java.util.ArrayList;
import java.util.List;

public class Const {

    public static String cityName = "Sydney";

    public static int updateSpeed = 300;
    public static final int WIDTH_MOTORBIKE = 7;
    public static final int WIDTH_SEDAN = WIDTH_MOTORBIKE * 2;
    public static final int WIDTH_BUS = WIDTH_SEDAN * 3;
    public static final int HEIGHT_MOTORBIKE = 8;
    public static final int HEIGHT_SEDAN = 12;
    public static final int HEIGHT_BUS = 15;
    public static List<City> cities=new ArrayList<>();
    public static List<String> savedCity=new ArrayList<>();

    public static City getCurrentCity(){
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getName().equals(cityName)){
                return cities.get(i);
            }
        }
        return null;
    }
}
