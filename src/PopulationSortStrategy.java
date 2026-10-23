import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PopulationSortStrategy implements SortStrategy {
    
    @Override
    public void sort(List<City> cities) {
        // Defaulting to descending order for better UX. 
        // Makes more sense to display highly populated cities at the top of the list.
        Collections.sort(cities, new Comparator<City>() {
            @Override
            public int compare(City c1, City c2) {
                // Comparing c2 to c1 directly to force descending sort order
                return Integer.compare(c2.getPopulation(), c1.getPopulation());
            }
        });
        
        System.out.println("Cities sorted by population (Descending).");
    }
}