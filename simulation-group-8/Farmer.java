/**
 * Write a description of class Farmer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Farmer extends Worker {
        public Farmer(int xLoc, int yLoc) {
            this.xLoc = xLoc;
            this.yLoc = yLoc;
            
            this.type = FARMER;
            this.buildingType = BuildingSlot.FARM;
            
            sprite = new HumanSprite(this.type);
            WorldManagement.world.addObject(sprite, xLoc, yLoc);
        }
        public void _update() {
            goToRandBuilding();
            drainFood();
        }
    }