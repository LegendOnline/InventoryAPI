package main.java.com.minecraftlegend.inventoryapi.Layouts;

import main.java.com.minecraftlegend.inventoryapi.GUIContainer;
import main.java.com.minecraftlegend.inventoryapi.GUIElement;
import main.java.com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class ModelLayout extends ExactLayout {

    private String layout;

    /**
     *
     * Syntax:
     *  x:y=i,x:y-x=i,x:y|y=i,x:y#x:y=i,*=i
     *
     *  x = x position of the target slot in the inventory
     *  y = y position of the target slot in the inventory
     *  i = GUIElement/char referenced with GUIElement
     *  - = fill slots horizontally
     *  | = fill slots vertically
     *  # = fill a square where pos1 is top-left and pos2 is bottom-right
     *  , = separate multiple filling operations
     *  * = fill all blank slots
     *
     *  <h2>Examples:</h2>
     *
     *  4:5=d   fill slot at x=4 & y=5 with GUIElement associated with char 'd'
     *  7:0|2=g fills slots vertically from x=7 & y=0 to x=7 & y=2 with GUIElement associated with char 'g'
     *
     *  <h2>Complete Example:</h2>
     *
     *  5:3=g,1:1-9=d,1:1|3=e,7:2#9:3=r,*=i
     */
   public ModelLayout(String layout){
        this.layout = layout;
        totalSize = getMaxY() * 9;
        dim = new Vector2i(9,getMaxY());
    }

    @Override
    public void apply(GUIContainer container) {
        String[] ops = layout.split(",");

        for (String op : ops) {
            if(op.matches("\\d:\\d=.")){
                Vector2i coords = parseCoordinates(op.split("=")[0]);
                char key = op.split("=")[1].charAt(0);
                Validate.isTrue(isSet(key), "key: " + key + " is not referenced!");
                GUIElement element = slots.get(key);
                element.setPosition(coords);
                container.add(element);
                element.draw();
            }else if(op.matches("\\d:\\d-\\d=.")){
                String[] components = op.split("=");
                Vector2i start = parseCoordinates(components[0].split("-")[0]);
                Vector2i end = new Vector2i(Integer.parseInt(components[0].split("-")[1]),start.getY());
                char key = components[1].charAt(0);
                Validate.isTrue(isSet(key), "key: " + key + " is not referenced!");
                GUIElement element = slots.get(key);
                for (int x = Math.min(start.getX(),end.getX()); x < Math.max(start.getX(),end.getX()) ; x++) {
                    Vector2i pos = start.clone();
                    pos.setX(x);
                    GUIElement clone = (GUIElement) element.clone();
                    clone.setPosition(pos);
                    container.add(clone);
                    clone.draw();
                }
            }else if(op.matches("\\d:\\d\\|\\d=.")){
                String[] components = op.split("=");
                Vector2i start = parseCoordinates(components[0].split("\\|")[0]);
                Vector2i end = new Vector2i(start.getX(),Integer.parseInt(components[0].split("\\|")[1]));
                char key = components[1].charAt(0);
                Validate.isTrue(isSet(key), "key: " + key + " is not referenced!");
                GUIElement element = slots.get(key);
                for (int y = Math.min(start.getY(),end.getY()); y < Math.max(start.getY(),end.getY()) ; y++) {
                    Vector2i pos = start.clone();
                    pos.setY(y);
                    GUIElement clone = (GUIElement) element.clone();
                    clone.setPosition(pos);
                    container.add(clone);
                    clone.draw();
                }
            }else if(op.matches("\\d:\\d#\\d:\\d=.")){
                String[] components = op.split("=");
                Vector2i start = parseCoordinates(components[0].split("#")[0]);
                Vector2i end = parseCoordinates(components[0].split("#")[1]);
                char key = components[1].charAt(0);
                Validate.isTrue(isSet(key), "key: " + key + " is not referenced!");
                GUIElement element = slots.get(key);

                    for (int y = start.getY(); y <= end.getY() ; y++) {
                        for (int x = start.getX(); x <= end.getX(); x++) {
                            Vector2i pos = new Vector2i(x,y);
                            GUIElement clone = (GUIElement) element.clone();
                            clone.setPosition(pos);
                            container.add(clone);
                            clone.draw();
                        }
                    }



            }else if(op.matches("\\*=.")){
                char key = op.split("=")[1].charAt(0);
                Validate.isTrue(isSet(key), "key: " + key + " is not referenced!");
                GUIElement element = slots.get(key);

                Inventory inv = container.getInventory();

                for (int y = 0; y < totalSize / 9; y++) {
                    for (int x = 0; x < 9; x++) {
                        if(inv.getItem(x + y*9) == null || inv.getItem(x + y*9).getType() == Material.AIR){
                            GUIElement clone = (GUIElement) element.clone();
                            clone.setPosition(new Vector2i(x,y));
                            container.add(clone);
                            clone.draw();
                        }
                    }
                }


            }else throw new IllegalArgumentException("Layout operation '"+op+"' is not defined!");
        }
    }

    private Vector2i parseCoordinates(String str){
        Validate.isTrue(str.matches("\\d:\\d"),"coordinates '"+str+"' doesnt match pattern x:y");
        String[] coords = str.split(":");
        int x = Integer.parseInt(coords[0]) - 1;
        int y = Integer.parseInt(coords[1]) - 1;
        return new Vector2i(x,y);
    }

    private int getMaxY(){
        String[] ops = layout.split(",");
        int max = 0;

        for (String op : ops) {
            if(op.matches("\\d:\\d=.")){
               Vector2i v = parseCoordinates(op.split("=")[0]);
                if(v.getY() > max) max = v.getY();
            }else if(op.matches("\\d:\\d-\\d=.")){
                Vector2i v = parseCoordinates(op.split("-")[0]);
                if(v.getY() > max) max = v.getY();
            }else if(op.matches("\\d:\\d\\|\\d=.")){
                int y = Integer.parseInt(op.split("\\|")[1].split("=")[0]);
                if(y > max) max = y;
            }else if(op.matches("\\d:\\d#\\d:\\d=.")){
                Vector2i v = parseCoordinates(op.split("#")[0]);
                Vector2i v1 = parseCoordinates(op.split("#")[1].split("=")[0]);
                Vector2i maxV = v.max(v1);
                if(maxV.getY() > max) max = maxV.getY();
            }else if(op.matches("\\*=.")){
                continue;
            }else throw new IllegalArgumentException("Layout operation '"+op+"' is not defined!");
        }
        return max;
    }

}
