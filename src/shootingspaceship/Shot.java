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
class Shot extends Sprite{
    private boolean alive;
    private final int radius = 3;

    public Shot(int x, int y, int max_x, int max_y) {
        super(x, y, max_x, max_y);
        alive = true;
    }
    
    public boolean getAlive() {
        return alive;
    }
    
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void moveShot(int speed) {
        moveY(speed);
    }

    public void drawShot(Graphics g) {
        if (!alive) {
            return;
        }
        g.setColor(Color.yellow);
        g.fillOval((int)getX(), (int)getY(), radius, radius);
    }
}
