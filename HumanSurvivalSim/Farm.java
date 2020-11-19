import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Sprite for the farm buildingslot
 * 
 * @author Leo Foo
 * @version 2020-10-10
 */
public class Farm extends Building
{
    /**
     * Constructor for the Farm class.
     */
    public Farm() {
       sprite = FARM_SPRITE;
       setImage(sprite);
       this.setRotation((int)Math.round(Math.random()*4.0)*90);
    }
}
