import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// acts as the core Publisher for the Observer pattern 
// runs asynchronously as an independent Thread (Runnable).
public class WeatherStation implements WeatherSubject, Runnable {
    
    private List<WeatherObserver> observers;
    private Random random;

    public WeatherStation() {
        observers = new ArrayList<>();
        random = new Random();
    }

    @Override
    public synchronized void attach(WeatherObserver observer) {
        observers.add(observer);
    }

    @Override
    public synchronized void detach(WeatherObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        // Broadcasting the update signal to all registered UI components
        List<WeatherObserver> snapshot;
        synchronized (this) {
            snapshot = new ArrayList<>(observers);
        }
        for (WeatherObserver obs : snapshot) {
            obs.update(); 
        }
    }

    @Override
    public void run() {
        // Running an infinite loop to simulate continuous weather monitoring.
        // The 3000ms delay is a requirement to trigger updates every 3 seconds.
        while (true) {
            try {
                Thread.sleep(3000); 
                
                changeWeatherRandomly();
                
                // State has changed; triggering UI observers to re-render.
                notifyObservers(); 
                
            } catch (InterruptedException e) {
                System.out.println("Weather simulation thread interrupted.");
                break;
            }
        }
    }

    private void changeWeatherRandomly() {
        List<City> cities = CityRepository.getInstance().getCities();
        WeatherState[] states = WeatherState.values(); 

        for (City city : cities) {
            // Assigning a random weather state from the available enums (SUNNY, CLOUDY, RAINY, SNOWY).
            WeatherState newState = states[random.nextInt(states.length)];
            city.setCurrentWeatherState(newState);
            
            // Generating a localized temperature between -5.0 and +35.0.
            double newTemp = -5.0 + (40.0 * random.nextDouble());
            
            // Rounding to 1 decimal place (e.g., 24.5) to prevent UI clutter and ensure clean presentation.
            newTemp = Math.round(newTemp * 10.0) / 10.0; 
            city.setCurrentTemperature(newTemp);
        }
        
        System.out.println("Weather conditions updated globally!");
    }
}
