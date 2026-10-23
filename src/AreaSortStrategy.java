import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AreaSortStrategy implements SortStrategy {
    
    @Override
    public void sort(List<City> cities) {
        // Keeping consistency with the population sort strategy. 
        // Defaulting to descending order so the largest areas appear first.
        Collections.sort(cities, new Comparator<City>() {
            @Override
            public int compare(City c1, City c2) {
                return Double.compare(c2.getArea(), c1.getArea());
            }
        });
        
        System.out.println("Cities sorted by area (Descending).");
    }
}