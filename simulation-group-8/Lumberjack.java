/**
 * Write a description of class Lumberjack here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lumberjack extends Human {
        private int tX, tY;
        private boolean foundTree = false;
        public Lumberjack(int xLoc, int yLoc) {
            this.xLoc = xLoc;
            this.yLoc = yLoc;
            
            this.type = LUMBERJACK;
            
            sprite = new HumanSprite(this.type);
            WorldManagement.world.addObject(sprite, xLoc, yLoc);
        }   

        public void _update() {
            findTree();
            moveTo(tX, tY);
            chopTree();
            drainFood();
        }
    
        private void findTree() {
            if(WorldManagement.trees.size() > 0) {
                tX = ((Tree)WorldManagement.trees.get(0)).getX();
                tY = ((Tree)WorldManagement.trees.get(0)).getY();
                foundTree = true;
            } else {
                // else go in random direction
                tX = Math.abs((int) (Math.random() * 2000) % 500);
                tY = Math.abs((int) (Math.random() * 2000) % 500);
                foundTree = false;
            }
        }

        private void chopTree() {
            if(foundTree) {
                if(calcDist(getX(), tX, getY(), tY) < DEFAULT_SPEED) {
                    ((Tree)WorldManagement.trees.get(0)).chop();
                }
            }
        }
    }