import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Miner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Miner extends Human {
    
    public Miner(int xLoc, int yLoc) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        
        this.type = MINER;
        this.buildingType = BuildingSlot.MINE;
        
        sprite = new GreenfootImage("miner.png");
        setImage(sprite);
        //WorldManagement.world.addObject(sprite, xLoc, yLoc);
    }
    public void _update() {
        checkRoute(BuildingSlot.MINE, getX(), getY());
        checkIsAtBuilding(targetX, targetY);
        mine();
        moveTo(targetX, targetY);
        drainFood();
    }
    
    private void mine()
    {
        if (atBuilding)
        {
            if (targetBuilding.getType() == BuildingSlot.MINE)
            {
                // Mine
            }
            targetBuilding.setTargetStatus(false);
            enroute = false;
        }
    }
}
