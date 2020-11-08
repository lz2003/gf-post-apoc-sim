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
    private static int SPAWN_DELAY = 300;
    private int spawnDelay = 0;
    public House(BuildingSlot slot) {
        sprite = new GreenfootImage("house.png");
        this.slot = slot;
    }
    public void _update() {
        if(WorldManagement.hasHousing() && WorldManagement.food >= 25f && spawnDelay < 0) {
            int humanType = (int)(Math.random() * 2322) % Human.TOTAL_HUMAN_TYPES;
            WorldManagement.addHuman(humanType, slot.getX(), slot.getY());
            WorldManagement.food -= 15f;
            spawnDelay = SPAWN_DELAY;
        }
        spawnDelay--;
    }
    public void destroy() {
 
    }
}
