import java.util.List;

// Base interface for the sorting Strategy pattern.
// Enforces the OCP(new sorting algorithms can be added simply by implementing this interface, without touching the core client code.)
public interface SortStrategy {
    void sort(List<City> cities);
}