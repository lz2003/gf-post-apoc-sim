import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class House here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class House extends Building
{
    private BuildingSlot slot;
    public House(BuildingSlot slot) {
        sprite = new GreenfootImage("house.png");
        this.slot = slot;
        setImage(sprite);
    }
    public void _update() {
        if(WorldManagement.hasHousing()) {
            int humanType = (int)(Math.random() * 2322) % Human.TOTAL_HUMAN_TYPES;
            WorldManagement.addHuman(humanType, slot.getX(), slot.getY());
        }
    }
    public void destroy() {

    }
}
