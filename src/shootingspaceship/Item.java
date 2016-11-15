/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shootingspaceship;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author sungyu
 */
public class Item extends Enemy{
    private int itemNum;
    
    public Item(int x, int y, int itemNum) {
        super(x, y, (float)1.5, (float)1.5, 1280, 1024, (float)1.0, 1);
        this.itemNum = itemNum;
    }
    
    public int getItemNum() {
        return itemNum;
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.green);
        int[] x_poly = {(int)this.getX(), (int)this.getX() - 10, (int)this.getX(), (int)this.getX() + 10};
        int[] y_poly = {(int)this.getY() + 15, (int)this.getY(), (int)this.getY() + 10, (int)this.getY()};
        g.fillPolygon(x_poly, y_poly, 4);
    }
}
