import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Farmers work at farm structures and collect food resources to prevent
 * the population from starving.
 * 
 * @author Lucy Zhao
 * @version 2020-11-08
 */
public class Farmer extends Human
{
    
    public Farmer(int xLoc, int yLoc) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        
        this.type = FARMER;
        this.buildingType = BuildingSlot.FARM;
        
        sprite = FARMER_SPRITE;
        setImage(sprite);
    }
    
    /**
     * Controls the behavior of the farmer.
     */
    public void _update() {
        checkRoute(buildingType, xLoc, yLoc);
        checkIsAtLocation(targetX, targetY);
        if (isWorking) work();
        else checkFarm();
        moveTo(targetX, targetY);
        drainFood();
    }
    
    /**
     * Checks if the farmer has arrived at a farm, if so, start working,
     * otherwise keep moving.
     */
    private void checkFarm()
    {
        if (atLocation)
        {
            if (targetBuilding != null && targetBuilding.getType() == buildingType)
            {
                workBar = new StatBar(FARMER_WORK_TIME, this, Color.YELLOW, Color.RED);
                WorldManagement.world.addObject(workBar, xLoc, yLoc);
                isWorking = true;
                return;
            }
            enroute = false;
        }
    }
    
    /**
     * The work method where the farmer gains resources for the human
     * population.
     */
    protected void work()
    {
        workBar.update(workBar.getCurrVal()-1);
        if (workBar.getCurrVal() < 0)
        {
            WorldManagement.food += (int) (Math.random() * 5);
            isWorking = false;
            WorldManagement.world.removeObject(workBar);
        }
    }
    
}
