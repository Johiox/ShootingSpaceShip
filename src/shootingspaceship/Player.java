/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shootingspaceship;

import java.awt.Graphics;
import java.awt.Color;
/**
 *
 * @author wgpak
 */
import java.awt.Graphics;
import java.awt.Color;

public class Player extends Sprite{
    private int min_x;
    private int max_x;

    public Player(int x, int y, int min_x, int max_x) {
        super(x, y);
        this.min_x = min_x;
        this.max_x = max_x;
    }

    public void moveX(int speed) {
        this.moveX(speed);
        if(this.getX() < min_x) {
            this.setX(min_x);
        }
        if(this.getX() > max_x) {
            this.setX(max_x);
        }
    }

    public ShotType generateShot(int damage) {
        ShotType shot = new ShotType((int)this.getX(), (int)this.getY(), damage);
        return shot;
    }

    public void drawPlayer(Graphics g) {
        g.setColor(Color.red);
        int[] x_poly = {(int)this.getX(), (int)this.getX() - 10, (int)this.getX(), (int)this.getX() + 10};
        int[] y_poly = {(int)this.getY(), (int)this.getY() + 15, (int)this.getY() + 10, (int)this.getY() + 15};
        g.fillPolygon(x_poly, y_poly, 4);
    }
}
