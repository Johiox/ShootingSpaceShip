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
class Item extends Enemy{
    private int itemNum;
    private int count;
    
    public Item(int x, int y, float delta_x, float delta_y, int max_x, int max_y, float delta_y_inc, int itemNum) {
        super(x, y, delta_x, delta_y, max_x, max_y, delta_y_inc, 1);
        this.itemNum = itemNum;
        count = 0;
    }
    
    public int getItemNum() {
        return itemNum;
    }
    
    public void move() {
        this.moveX(this.getDeltaX());
        this.moveY(this.getDeltaY());

        if(this.getX() < 0) {
            this.setX(0);
            this.setDeltaX(-this.getDeltaX());
            ++count;
        }
        else if(this.getX() >= this.getMaxX()) {
            this.setX(this.getMaxX());
            this.setDeltaX(-this.getDeltaX());
            ++count;
        }
        if (this.getY() >= this.getMaxY()) {//y값 튕김 효과
            this.setY(this.getMaxY());
            this.setDeltaY(this.getDeltaY() - this.getDeltaYINC());
            ++count;
        }
        else if (this.getY() < 0) {
            this.setDeltaY(this.getDeltaY() + this.getDeltaYINC());
            ++count;
        }
    }
    
    public boolean count() {
        if(count == 10) {
            count = 0;
            return false;
        }
        else {
            return true;
        }
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.green);
        int[] x_poly = {(int)this.getX(), (int)this.getX() - 10, (int)this.getX(), (int)this.getX() + 10};
        int[] y_poly = {(int)this.getY() + 15, (int)this.getY(), (int)this.getY() + 10, (int)this.getY()};
        g.fillPolygon(x_poly, y_poly, 4);
    }
}