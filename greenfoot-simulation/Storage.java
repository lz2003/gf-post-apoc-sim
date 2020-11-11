import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Storage sprite for the storage buildingslot
 * 
 * @author Leo Foo
 * @version 2020-10-10
 */
public class Storage extends Building
{
    public Storage() {
        sprite = STORAGE_SPRITE;
        setImage(sprite);
        this.setRotation((int)Math.round(Math.random()*4.0)*90);
    }
    public void _update() {
        
    }
}
