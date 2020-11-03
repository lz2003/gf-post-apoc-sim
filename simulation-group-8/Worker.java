/**
 * Write a description of class Worker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Worker extends Human {
        protected BuildingSlot targetBuilding;
        protected boolean atBuilding = false, enRoute = false;
        protected int targetX = 600, targetY = 0;
        protected int buildingType;
        
        public void goToRandBuilding() {
            setAtBuilding();
            if(!atBuilding) {
                if(!enRoute) {
                    targetBuilding = getRandBuildingOfType(buildingType);
                    enRoute = true;
                }
            } else  {
                enRoute = false;
                if(!enRoute) {
                    targetBuilding = getRandBuildingOfType(buildingType);
                    enRoute = true;
                }
            }
            if(targetBuilding != null) {
                moveTo(targetBuilding.getX(), targetBuilding.getY());
            }
            drainFood();
        }
        
        private void setAtBuilding() {
            if(targetBuilding != null) {
                atBuilding = calcDist(xLoc, targetBuilding.getX(), yLoc, targetBuilding.getY()) < DEFAULT_SPEED;
            }
        }
    }