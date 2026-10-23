public class ParkVisit extends VisitDecorator {
    
    public ParkVisit(VisitPlan planToWrap) {
        super(planToWrap);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Walk in the Park";
    }

    @Override
    public double getCost() {
        // Explicitly adding 0.0 to indicate that public parks have no entry fee.
        return super.getCost() + 0.0; 
    }

    @Override
    public double getTime() {
        // Estimated standard duration for a casual park walk.
        return super.getTime() + 1.5; 
    }
}