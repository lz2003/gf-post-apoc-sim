/**
 * Write a description of class Miner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Miner extends Worker {
    public Miner(int xLoc, int yLoc) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        
        this.type = MINER;
        this.buildingType = BuildingSlot.MINE;
        
        sprite = new HumanSprite(this.type);
        WorldManagement.world.addObject(sprite, xLoc, yLoc);
    }
    public void _update() {
        goToRandBuilding();
        drainFood();
    }
}
