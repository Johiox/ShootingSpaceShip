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

    public Shot(int x, int y) {
        super(x, y);
        alive = true;
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

    public void collided() {
        alive = false;
    }
}
