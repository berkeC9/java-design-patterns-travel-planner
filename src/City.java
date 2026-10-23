public class City {

    // Kept private for strict encapsulation (course grading requirement)
    private String name;
    private int population;
    private double area;
    private double currentTemperature;
    private WeatherState currentWeatherState;

    // Required empty constructor for JSON deserialization fallback
    public City() {
    }

    public City(String name, int population, double area, double currentTemperature, WeatherState currentWeatherState) {
        this.name = name;
        this.population = population;
        this.area = area;
        this.currentTemperature = currentTemperature;
        this.currentWeatherState = currentWeatherState;
    }

    // Boilerplate getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public synchronized double getCurrentTemperature() {
        return currentTemperature;
    }

    public synchronized void setCurrentTemperature(double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public synchronized WeatherState getCurrentWeatherState() {
        return currentWeatherState;
    }

    public synchronized void setCurrentWeatherState(WeatherState currentWeatherState) {
        this.currentWeatherState = currentWeatherState;
    }

    // Overridden to display readable text in the GUI ListBox instead of memory reference
    @Override
    public String toString() {
        // Hardcoded format to match the GUI mockup from the project PDF exactly
        return name + " (Pop: " + population + ", Area: " + area + " km²)"; 
    }
}
