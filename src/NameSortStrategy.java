import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NameSortStrategy implements SortStrategy {
    
    @Override
    public void sort(List<City> cities) {
        // with inconsistent user input ("Ankara" vs "ankara").
        Collections.sort(cities, new Comparator<City>() {
            @Override
            public int compare(City c1, City c2) {
                return c1.getName().compareToIgnoreCase(c2.getName());
            }
        });
        
        System.out.println("Cities sorted alphabetically by name.");
    }
}