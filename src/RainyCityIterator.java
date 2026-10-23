import java.util.List;

public class RainyCityIterator implements CityIterator {
    
    private List<City> cities;
    private int position = 0;

    public RainyCityIterator(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public boolean hasNext() {
        // advances the internal pointer until the next RAINY city is found.
        while (position < cities.size()) {
            if (cities.get(position).getCurrentWeatherState() == WeatherState.RAINY) {
                return true;
            }
            position++;
        }
        return false;
    }

    @Override
    public City next() {
        // returning null instead of throwing an exception to maintain consistency across iterators and prevent GUI crashes.
        if (hasNext()) {
            return cities.get(position++);
        }
        return null;
    }
}