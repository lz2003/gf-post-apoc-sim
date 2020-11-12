import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Lumberjacks scatter across the world and chop down trees to collect
 * wood for creating buildings.
 * 
 * @author Lucy Zhao
 * @version 2020-11-10
 */
public class Lumberjack extends Human
{
    private int treeX, treeY;
    private Tree targetTree;
    private boolean enroute = false;
    
    /**
     * The constructor for the Lumberjack class.
     * 
     * @param xLoc  the x location
     * @param yLoc  the y location
     */
    public Lumberjack(int xLoc, int yLoc) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
               
        this.type = LUMBERJACK;
        addHealthBar();  
        sprite = LUMBERJACK_SPRITE;
        setImage(sprite);
    }
    
    /**
     * Controls the behavior of the lumberjack.
     */
    public void _update() {
        // Ensures that lumberjacks spread out
        if (!enroute)
        {
            findTree();
            if (targetTree != null)
            {
                targetTree.setTargetStatus(true);
            }
            enroute = true;
        }
        checkIsAtLocation(treeX, treeY);
        if (isWorking) work();
        else checkTree();
        moveTo(treeX, treeY);
        drainFood();
        randomZombieChance();
    }
    
    /**
     * Find the nearest tree to chop down.
     */
    private void findTree() {
        if(WorldManagement.trees.size() > 0) {
            targetTree = ((Tree)WorldManagement.getTrees().get(0)); 
            treeX = targetTree.getX();
            treeY = targetTree.getY();
            for (int i = 0; i < WorldManagement.getTrees().size(); i++)
            {
                Tree tree = ((Tree)WorldManagement.getTrees().get(i));
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
    private void checkTree() {
        if(atLocation) {
            if(targetTree != null) {
                isWorking = true;
                workBar = new StatBar(BUILDER_WORK_TIME, this, Color.GREEN, Color.GRAY);
                WorldManagement.getWorld().addObject(workBar, xLoc, yLoc);
                return;
            }
            enroute = false;
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
        WorldManagement.getHumans().remove(this);
        WorldManagement.getWorld().removeObject(this);
    }
    
    /**
     * The work method where the lumberjack gains resources for the 
     * human population.
     */
    protected void work()
    {
        workBar.update(workBar.getCurrVal()-1);
        if (workBar.getCurrVal() == 0)
        {
            targetTree.chop();
            isWorking = false;
            enroute = false;
            WorldManagement.getWorld().removeObject(workBar);
        }
    }
    
}
