import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Builders build new structures when enough resources are present. Each
 * building created is determine by its current demand.
 * 
 * @author Lucy Zhao
 * @version 2020-11-08
 */
public class Builder extends Human
{
    public Builder(int xLoc, int yLoc) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        
        this.type = BUILDER;
        
        sprite = BUILDER_SPRITE;
        setImage(sprite);
    }
    
    /**
     * Controls the behavior of the builder.
     */
    public void _update() {
        checkRoute(BuildingSlot.EMPTY, 350, 350);
        checkIsAtLocation(targetX, targetY);
        if (!isWorking) checkBuild();
        else work();
        moveTo(targetX, targetY);
        drainFood();
        randomZombieChance();
    }    
 
    /**
     * Build a new building of the highest demand.
     */
    private void checkBuild() { 
        if(atLocation) { 
            if(targetBuilding != null && targetBuilding.getType() == BuildingSlot.EMPTY) {
                if(WorldManagement.wood >= 15) {
                    workBar = new StatBar(BUILDER_WORK_TIME, this, Color.GREEN, Color.GRAY);
                    WorldManagement.world.addObject(workBar, xLoc, yLoc);
                    WorldManagement.wood -= 15;
                    isWorking = true;
                    return;
                }
                targetBuilding.setTargetStatus(false);
            }
            enroute = false;
        }
    }
    
    /**
     * The work method where the builder creates buildings for the human
     * population.
     */
    protected void work()
    {
        workBar.update(workBar.getCurrVal()-1);
        if (workBar.getCurrVal() <= 0)
        {
            WorldManagement.getBuildingSlot(nearestIndex).setBuilding(WorldManagement.highestDemand);
            isWorking = false;
            targetBuilding.setTargetStatus(false);
            enroute = false;
            WorldManagement.world.removeObject(workBar);
        }
    }
}
