public class BaseCityVisit implements VisitPlan {
    
    private City city;

    public BaseCityVisit(City city) {
        this.city = city;
    }

    @Override
    public String getDescription() {
        return "Base plan for visiting " + city.getName();
    }

    @Override
    public double getCost() {
        // Dynamic base cost calculation based on the city's population
        // Calculates a base fee relative to the city size 
        double baseCost = city.getPopulation() / 100000.0;
        
        // Rounding the result to 2 decimal places for currency format 
        return Math.round(baseCost * 100.0) / 100.0;
    }

    @Override
    public double getTime() {
        // Dynamic base time calculation simulating traffic and transportation overhead in larger cities.
        // Starts with a 1.0 hour base time, adding 0.5 extra hours for every 5 million residents.
        double baseTime = 1.0 + (city.getPopulation() / 5000000.0) * 0.5;
        
        // Rounding the result to 1 decimal place 
        return Math.round(baseTime * 10.0) / 10.0; 
    }
}