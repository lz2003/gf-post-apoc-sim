import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class HumanSprite here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HumanSprite extends Actor
{
    /**
     * Act - do whatever the HumanSprite wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    GreenfootImage image;
    public HumanSprite(int humanID) {
           switch(humanID) {
               case Human.BUILDER: 
                   image = new GreenfootImage("builder.png");
                   break;
               case Human.FARMER: 
                   image = new GreenfootImage("farmer.png");
                   break;
               case Human.LUMBERJACK: 
                   image = new GreenfootImage("lumberjack.png");
                   break;
               case Human.MINER: 
                   image = new GreenfootImage("miner.png");
                   break;
           }
           setImage(image);
    }
    public void act() 
    {
        // Add your action code here.
    }    
}
