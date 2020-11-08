import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Miners work at the mine structure and help collect iron for crafting
 * sentry bullets for protection.
 * 
 * @author Lucy Zhao
 * @version 2020-11-08
 */
public class Miner extends Human {
    
    public Miner(int xLoc, int yLoc) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        
        this.type = MINER;
        this.buildingType = BuildingSlot.MINE;
        
        sprite = new GreenfootImage("miner.png");
        setImage(sprite);
    }
    
    /**
     * Controls the behavior of the miner.
     */
    public void _update() {
        checkRoute(buildingType, xLoc, yLoc);
        checkIsAtLocation(targetX, targetY);
        if (isWorking) work();
        else checkMine();
        moveTo(targetX, targetY);
        drainFood();
    }
    
    /**
     * Checks if the miner has arrived at a mine, if so, start working,
     * otherwise keep moving.
     */
    private void checkMine()
    {
        if (atLocation)
        {
            if (targetBuilding != null && targetBuilding.getType() == buildingType)
            {
                workBar = new StatBar(MINER_WORK_TIME, this, Color.YELLOW, Color.RED);
                WorldManagement.world.addObject(workBar, xLoc, yLoc);
                isWorking = true;
                return;
            } 
            enroute = false;
        }
    }
    
    /**
     * The work method where the miner gains resources for the human
     * population.
     */
    protected void work()
    {
        workBar.update(workBar.getCurrVal()-1);
        if (workBar.getCurrVal() < 0)
        {
            WorldManagement.iron += (int) (Math.random() * 5);
            isWorking = false;
            WorldManagement.world.removeObject(workBar);
        }
    }
}
