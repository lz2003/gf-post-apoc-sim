import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * House sprite for the house buildingslot
 * 
 * @author Young Chen
 * @version 2020-10-10
 */
public class House extends Building
{
    private BuildingSlot slot;
    private static int SPAWN_DELAY = 300;
    private int spawnDelay = 0;
    public House(BuildingSlot slot) {
        sprite = HOUSE_SPRITE;
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
