public class ShoppingMallVisit extends VisitDecorator {
    
    public ShoppingMallVisit(VisitPlan planToWrap) {
        super(planToWrap);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Shopping Mall";
    }

    @Override
    public double getCost() {
        // Hardcoding an estimated discretionary spending amount for the shopping trip.
        return super.getCost() + 50.0; 
    }

    @Override
    public double getTime() {
        // Estimated duration considering average time spent browsing and shopping.
        return super.getTime() + 3.0; 
    }
}