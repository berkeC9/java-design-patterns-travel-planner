// Core Publisher interface for the Observer design pattern.
// Manages the subscription lifecycle and broadcasts state changes to dependent components.
public interface WeatherSubject {
    
    // Registers an observer to the subscription list.
    void attach(WeatherObserver observer); 
    
    // Removes an observer from the subscription list.
    void detach(WeatherObserver observer); 
    
    // Broadcasts an update signal to all registered observers when the internal state changes.
    void notifyObservers(); 
}