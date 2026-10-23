import java.util.List;

public class CloudyCityIterator implements CityIterator {
    
    private List<City> cities;
    private int position = 0;

    public CloudyCityIterator(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public boolean hasNext() {
        // Advances the internal pointer until the next CLOUDY city is found.
        while (position < cities.size()) {
            if (cities.get(position).getCurrentWeatherState() == WeatherState.CLOUDY) {
                return true;
            }
            position++;
        }
        return false;
    }

    @Override
    public City next() {
        // returning null instead of throwing an exception 
        // to maintain consistency across iterators and prevent GUI crashes.
        if (hasNext()) {
            return cities.get(position++);
        }
        return null;
    }
}