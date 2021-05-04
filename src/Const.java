import java.util.ArrayList;
import java.util.List;

public class Const {

    public static String cityName = "Sydney";

    public static int updateSpeed = 500;
    public static final int LENGTH_MOTORBIKE = 7;
    public static final int LENGTH_SEDAN = LENGTH_MOTORBIKE * 2;
    public static final int LENGTH_BUS = LENGTH_SEDAN * 3;
    public static final int BREADTH_MOTORBIKE = LENGTH_MOTORBIKE/2;
    public static final int BREADTH_SEDAN = LENGTH_SEDAN/2;
    public static final int BREADTH_BUS = LENGTH_BUS/2;
    public static List<City> cities =new ArrayList<>();
    public static List<String> savedCity =new ArrayList<>();

    public static City getCurrentCity(){
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getName().equals(cityName)){
                return cities.get(i);
            }
        }
        return null;
    }
}
