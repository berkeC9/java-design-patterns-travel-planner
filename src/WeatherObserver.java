// Contract for the Observer design pattern.
// UI components or other dependent modules must implement this to listen for and react to weather state changes.
public interface WeatherObserver {
    
    // Triggered by the observable whenever a state change occurs.
    void update(); 
}