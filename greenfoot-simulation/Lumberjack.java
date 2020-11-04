import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Lumberjack here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lumberjack extends Human
{
    private int treeX, treeY;
    private Tree targetTree;
    private boolean foundTree = false;
    public Lumberjack(int xLoc, int yLoc) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        
        this.type = MINER;
        //this.buildingType = BuildingSlot.MINE;
        
        sprite = new GreenfootImage("lumberjack.png");
        setImage(sprite);
        //WorldManagement.world.addObject(sprite, xLoc, yLoc);
    }
    
    public void _update() {
            findTree();
            moveTo(treeX, treeY);
            chopTree();
            drainFood();
    }
    
    /**
     * Find the nearest (not yet) tree to chop down.
     */
    private void findTree() {
        if(WorldManagement.trees.size() > 0) {
            treeX = ((Tree)WorldManagement.trees.get(0)).getX();
            treeY = ((Tree)WorldManagement.trees.get(0)).getY();
            foundTree = true;
        } else {
            // else go in random direction
            treeX = Math.abs((int) (Math.random() * 2000) % 500);
            treeY = Math.abs((int) (Math.random() * 2000) % 500);
            foundTree = false;
        }
    }

    /**
     * Chops down the tree at specified location
     */
    private void chopTree() {
        if(foundTree) {
            if(Utils.calcDist(getX(), treeX, getY(), treeY) < DEFAULT_SPEED) {
                ((Tree)WorldManagement.trees.get(0)).chop();
            }
        }
    }
}
