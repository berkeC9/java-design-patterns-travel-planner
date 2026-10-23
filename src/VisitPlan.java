// Core component for the Decorator design pattern.
// Both the base concrete component and all activity decorators must implement this contract.
public interface VisitPlan {
    
    // Builds and returns the cumulative description of the plan (e.g., "Ankara Visit, Museum, Mall").
    String getDescription(); 
    
    // Calculates the total accumulated cost of the base visit plus all decorated activities.
    double getCost();        
    
    // Calculates the total accumulated time spent.
    double getTime();        
}