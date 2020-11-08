import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Builder here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Builder extends Human
{
    public Builder(int xLoc, int yLoc) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        
        this.type = BUILDER;
        
        sprite = new GreenfootImage("builder.png");
        setImage(sprite);
    }
    
    public void _update() {
            checkRoute(BuildingSlot.EMPTY, 350, 350);
            checkIsAtBuilding(targetX, targetY);
            build();
            moveTo(targetX, targetY);
            drainFood();
    }    
 
    /**
     * Build a new building of the highest demand.
     */
    private void build() { 
        if(atBuilding) { 
            if(WorldManagement.getBuildingSlot(nearestIndex).getType() == BuildingSlot.EMPTY) {
                if(WorldManagement.wood >= 15) {
                    WorldManagement.getBuildingSlot(nearestIndex).setBuilding(WorldManagement.highestDemand);
                    WorldManagement.wood -= 15;
                }
            }
            targetBuilding.setTargetStatus(false);
            enroute = false;
        }
    }
}
