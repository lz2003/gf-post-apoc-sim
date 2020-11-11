import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Mine sprite for the mine buildingslot
 * 
 * @author Leo Foo 
 * @version 2020-10-10
 */
public class Mine extends Building
{
    public Mine() {
        sprite = MINE_SPRITE;
        setImage(sprite);
        this.setRotation((int)Math.round(Math.random()*4.0)*90);
    }
    public void _update() {
        
    }
}
