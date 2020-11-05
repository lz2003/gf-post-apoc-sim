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
    private boolean enroute = false;
    public Lumberjack(int xLoc, int yLoc) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        
        this.type = LUMBERJACK;
        
        sprite = new GreenfootImage("lumberjack.png");
        setImage(sprite);
    }
    
    public void _update() {
            // Ensures that lumberjacks spread out
            if (!enroute)
            {
                findTree();
                if (targetTree != null)
                {
                    targetTree.setTargetStatus(true);
                    enroute = true;
                }
            }
            moveTo(treeX, treeY);
            chopTree();
            drainFood();
    }
    
    /**
     * Find the nearest tree to chop down.
     */
    private void findTree() {
        if(WorldManagement.trees.size() > 0) {
            targetTree = ((Tree)WorldManagement.trees.get(0)); 
            treeX = targetTree.getX();
            treeY = targetTree.getY();
            for (int i = 0; i < WorldManagement.trees.size(); i++)
            {
                Tree tree = ((Tree)WorldManagement.trees.get(i));
                if(Utils.calcDist(getX(), treeX, getY(), treeY) > Utils.calcDist(getX(), tree.getX(), getY(), tree.getY())) {
                    if (!tree.getTargetStatus())
                    {
                        targetTree = tree;
                        treeX = tree.getX();
                        treeY = tree.getY();
                    }
                }
            }
        } else {
            // else go in random direction
            treeX = Math.abs((int) (Math.random() * 2000) % 500);
            treeY = Math.abs((int) (Math.random() * 2000) % 500);
        }
    }

    /**
     * Chops down the tree at specified location
     */
    private void chopTree() {
        if(targetTree != null) {
            if(Utils.calcDist(getX(), treeX, getY(), treeY) < DEFAULT_SPEED+40) {
                targetTree.chop();
                enroute = false;
            }
        }
    }
    
    /**
     * Removes the human instance from the list and the world.
     */
    public void die() {
        if (targetTree != null)
        {
            targetTree.setTargetStatus(false);
        }
        WorldManagement.humans.remove(this);
        WorldManagement.world.removeObject(this);
    }
    
    
}
