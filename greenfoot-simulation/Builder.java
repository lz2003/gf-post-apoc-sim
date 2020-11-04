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
    public Builder(int xLoc, int yLoc) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        
        this.type = MINER;
        //this.buildingType = BuildingSlot.MINE;
        
        sprite = new GreenfootImage("builder.png");
        setImage(sprite);
        //WorldManagement.world.addObject(sprite, xLoc, yLoc);
    }
    public void _update() {
            checkIsAtBuilding();
            build();
            moveTo(nearest.getX(), nearest.getY());
            drainFood();
    }    
    
    private void build() { 
        if(atBuilding) { 
            if(WorldManagement.getBuildingSlot(nearestIndex).getType() == BuildingSlot.EMPTY) {
                if(WorldManagement.wood >= 15) {
                    WorldManagement.getBuildingSlot(nearestIndex).setBuilding(WorldManagement.highestDemand);
                    WorldManagement.wood -= 15;
                }
            }
        }
    }

    private void checkIsAtBuilding() {
        nearest = getNearestBuilding(BuildingSlot.EMPTY, 350 ,350);
        atBuilding = (Utils.calcDist(xLoc, nearest.getX(), yLoc, nearest.getY()) < DEFAULT_SPEED);
    }
}
