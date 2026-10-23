public class CityCenterVisit extends VisitDecorator {
    
    public CityCenterVisit(VisitPlan planToWrap) {
        super(planToWrap);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", City Center Walk";
    }

    @Override
    public double getCost() {
        // Factoring in minor incidental expenses during the walk (e.g., grabbing a coffee).
        return super.getCost() + 10.0; 
    }

    @Override
    public double getTime() {
        // Estimated time allocation for exploring the downtown area.
        return super.getTime() + 2.0; 
    }
}