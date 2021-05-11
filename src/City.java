import java.util.ArrayList;

public class City {

    private String name;
    private ArrayList<Road> roads;
    private ArrayList<Car> cars;

    public City(String name) {
        this.name = name;
        roads = new ArrayList<>();
        cars = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    public void setRoads(ArrayList<Road> roads) {
        this.roads = roads;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    public void addRoad(Road road) {
        roads.add(road);
    }

    public void addCar(Car car) {
        cars.add(car);
    }



}
