import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Builder here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Builder extends Human
{
    private BuildingSlot nearest;
    private boolean atBuilding;
    private int bX, bY;
    private boolean enroute = false;
    public Builder(int xLoc, int yLoc) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        
        this.type = BUILDER;
        
        sprite = new GreenfootImage("builder.png");
        setImage(sprite);
    }
    public void _update() {
            if (!enroute)
            {
                nearest = getNearestBuilding(BuildingSlot.EMPTY, 350 ,350);
                checkBuilding(nearest);
                enroute = true;
                nearest.setTargetStatus(true);
            }
            checkIsAtBuilding();
            build();
            moveTo(bX, bY);

            drainFood();
    }    
    
    private void checkBuilding(BuildingSlot building)
    {
        if (building.getType() == BuildingSlot.EMPTY)
        {
             bX = nearest.getX();
             bY = nearest.getY();
        }
        else // Walk around randomly
        {
             bX = Math.abs((int) (Math.random() * 2000) % 500);
             bY = Math.abs((int) (Math.random() * 2000) % 500);
        }
    }
    
    private void build() { 
        if(atBuilding) { 
            if(WorldManagement.getBuildingSlot(nearestIndex).getType() == BuildingSlot.EMPTY) {
                if(WorldManagement.wood >= 15) {
                    WorldManagement.getBuildingSlot(nearestIndex).setBuilding(WorldManagement.highestDemand);
                    WorldManagement.wood -= 15;
                }
            }
            nearest.setTargetStatus(false);
            enroute = false;
        }
    }

    private void checkIsAtBuilding() {
        atBuilding = (Utils.calcDist(xLoc, bX, yLoc, bY) < DEFAULT_SPEED+50);
    }
    
    /**
     * Removes the human instance from the list and the world.
     */
    public void die() {
        if (nearest != null)
        {
            nearest.setTargetStatus(false);
        }
        WorldManagement.humans.remove(this);
        WorldManagement.world.removeObject(this);
    }
}
