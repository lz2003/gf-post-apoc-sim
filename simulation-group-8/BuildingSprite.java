import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BuildingSprite here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BuildingSprite extends Actor
{
    GreenfootImage image;
    public BuildingSprite() {
        image = new GreenfootImage("empty.png");
        image.scale(WorldManagement.BUILDING_SIZE, WorldManagement.BUILDING_SIZE);
        setImage(image);
    }
    
    public void setImage(int buildingID) {
        switch(buildingID) {
            case BuildingSlot.ARMOURY:
                image = new GreenfootImage("armoury.png");
                break;
            case BuildingSlot.BARRACKS:
                image = new GreenfootImage("barracks.png");
                break;
            case BuildingSlot.FARM:
                image = new GreenfootImage("farm.png");
                break;
            case BuildingSlot.MINE:
                image = new GreenfootImage("mine.png");
                break;
            case BuildingSlot.SENTRY:
                image = new GreenfootImage("sentry.png");
                break;
            case BuildingSlot.STORAGE:
                image = new GreenfootImage("storage.png");
                break;
            case BuildingSlot.HOUSE:
                image = new GreenfootImage("house.png");
                break;
            case BuildingSlot.EMPTY:
                image = new GreenfootImage("empty.png");
                break;
        }
        image.scale(WorldManagement.BUILDING_SIZE, WorldManagement.BUILDING_SIZE);
        setImage(image);
    }
    public void act() 
    {
        // Add your action code here.
    }    
}
