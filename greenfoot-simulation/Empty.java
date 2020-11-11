import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Empty building class for when no building has been built in the buildingslot
 * 
 * @author Leo Foo
 * @version 2020-10-10
 */
public class Empty extends Building
{
    public Empty()
    {
        sprite = EMPTY_SPRITE;
        setImage(sprite);
    }
    public void _update() {
        
    }
 
}
