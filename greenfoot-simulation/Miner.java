import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Miners work at the mine structure and help collect iron for crafting
 * sentry bullets for protection.
 * 
 * @author Lucy Zhao
 * @version 2020-11-10
 */
public class Miner extends Human {
    
    /**
     * The constructor for the Miner class.
     * 
     * @param xLoc  the x location
     * @param yLoc  the y location
     */
    public Miner(int xLoc, int yLoc) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
                
        this.type = MINER;
        this.buildingType = BuildingSlot.MINE;
        addHealthBar();   
        sprite = MINER_SPRITE;
        setImage(sprite);
    }
    
    /**
     * Controls the behavior of the miner.
     */
    public void _update() {
        checkRoute(buildingType, xLoc, yLoc);
        if (isWorking) work();
        else checkMine();
        drainFood();
        randomZombieChance();
    }
    
    /**
     * Checks if the miner has arrived at a mine, if so, start working,
     * otherwise keep moving.
     */
    private void checkMine()
    {
        checkIsAtLocation(targetX, targetY);
        if (atLocation)
        {
            if (targetBuilding != null && targetBuilding.getType() == buildingType)
            {
                workBar = new StatBar(MINER_WORK_TIME, this, Color.GREEN, Color.GRAY);
                WorldManagement.getWorld().addObject(workBar, xLoc, yLoc);
                isWorking = true;
                // Prevents spamming of sound
                if ((int) (Math.random() * 10) == 1)
                {
                    WorldManagement.playSound(mineSound);
                }
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
     * The work method where the miner gains resources for the human
     * population
     */
    protected void work()
    {
        workBar.update(workBar.getCurrVal()-1);
        if (workBar.getCurrVal() < 0)
        {
            WorldManagement.updateIron((int) (Math.random() * 5));
            isWorking = false;
            WorldManagement.getWorld().removeObject(workBar);
        }
    }
}
