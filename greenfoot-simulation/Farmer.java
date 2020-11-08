import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Farmer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Farmer extends Human
{
    
    public Farmer(int xLoc, int yLoc) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        
        this.type = FARMER;
        this.buildingType = BuildingSlot.FARM;
        
        sprite = farmer;
        setImage(farmer);
        //WorldManagement.world.addObject(sprite, xLoc, yLoc);
    }
    
    public void _update() {
        checkRoute(BuildingSlot.FARM, getX(), getY());
        checkIsAtBuilding(targetX, targetY);
        farm();
        moveTo(targetX, targetY);
        drainFood();
    }
    
    private void farm()
    {
        if (atBuilding)
        {
            if (targetBuilding.getType() == BuildingSlot.FARM)
            {
                // Farm
            }
            targetBuilding.setTargetStatus(false);
            enroute = false;
        }
    }
    
}
