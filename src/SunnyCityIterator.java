import java.util.List;

public class SunnyCityIterator implements CityIterator {
    
    private List<City> cities;
    private int position = 0;

    public SunnyCityIterator(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public boolean hasNext() {
        // Advances the internal pointer until the next SUNNY city is found.
        while (position < cities.size()) {
            if (cities.get(position).getCurrentWeatherState() == WeatherState.SUNNY) {
                return true;
            }
            position++;
        }
        return false;
    }

    @Override
    public City next() {
        // Relies on hasNext() to set the correct position. 
        // Returning null instead of throwing NoSuchElementException to prevent GUI crashes.
        if (hasNext()) {
            return cities.get(position++);
        }
        return null;
    }
}