import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Farmers work at farm structures and collect food resources to prevent
 * the population from starving.
 * 
 * @author Lucy Zhao
 * @version 2020-11-10
 */
public class Farmer extends Human
{
    
    /**
     * The constructor for the Farmer class.
     * 
     * @param xLoc  the x location
     * @param yLoc  the y location
     */
    public Farmer(int xLoc, int yLoc) {
        this.xLoc = xLoc;
        this.yLoc = yLoc; 
        
        this.type = FARMER;
        this.buildingType = BuildingSlot.FARM;
        this.sprite = FARMER_SPRITE;
        addHealthBar(); 
        setImage(sprite);
    }
    
    /**
     * Controls the behavior of the farmer.
     */
    public void _update() {
        checkRoute(buildingType, xLoc, yLoc);
        if (isWorking) work();
        else checkFarm();
        drainFood();
        randomZombieChance();
    }
    
    /**
     * Checks if the farmer has arrived at a farm, if so, start working,
     * otherwise keep moving.
     */
    private void checkFarm()
    {
        checkIsAtLocation(targetX, targetY);
        if (atLocation)
        {
            if (targetBuilding != null && targetBuilding.getType() == buildingType)
            {
                workBar = new StatBar(BUILDER_WORK_TIME, this, Color.GREEN, Color.GRAY);
                WorldManagement.getWorld().addObject(workBar, xLoc, yLoc);
                isWorking = true;
                setRandomRotation();
                return;
            }
            enroute = false;
        }
        else
        {
            moveTo(targetX, targetY);
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
            WorldManagement.updateFood((float) (Math.random() * 5));
            isWorking = false;
            WorldManagement.getWorld().removeObject(workBar);
        }
    }
    
}
