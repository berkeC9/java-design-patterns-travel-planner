public class MuseumVisit extends VisitDecorator {
    
    public MuseumVisit(VisitPlan planToWrap) {
        super(planToWrap);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Visit Museum";
    }

    @Override
    public double getCost() {
        // Hardcoding a flat entry fee assuming no museum pass is available.
        return super.getCost() + 15.0; 
    }

    @Override
    public double getTime() {
        // Estimated time required to complete the museum tour.
        return super.getTime() + 2.0; 
    }
}