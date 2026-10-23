// Base Decorator class that delegates operations to the wrapped component.
// All specific visit activities (Museum, Park, etc.) will extend this to chain their behaviors.
public abstract class VisitDecorator implements VisitPlan {
    
    // Protected visibility allows concrete subclass decorators to access the underlying wrapped layer. 
    protected VisitPlan wrappedPlan; 

    public VisitDecorator(VisitPlan planToWrap) {
        this.wrappedPlan = planToWrap;
    }

    @Override
    public String getDescription() {
        // Simply passes the call down the decorator stack.
        // Concrete subclasses will override this to append their own data to the result.
        return wrappedPlan.getDescription();
    }

    @Override
    public double getCost() {
        return wrappedPlan.getCost();
    }

    @Override
    public double getTime() {
        return wrappedPlan.getTime();
    }
}