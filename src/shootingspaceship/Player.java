/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shootingspaceship;

/**
 *
 * @author wgpak
 */
import java.awt.Graphics;

class Player extends Sprite{
    private int min_x;
    private int min_y;
    
    public Player(int x, int y, int min_x, int max_x, int min_y, int max_y) {
        super(x, y, max_x, max_y);
        this.min_x = min_x;
        this.min_y = min_y;
        setImage(toolkit.getImage("src/shootingspaceship/Plane.png"));
    }

    public void movingX(int speed) {
        this.moveX(speed);
        if(this.getX() < min_x) {
            this.setX(min_x);
        }
        if(this.getX() > this.getMaxX()) {
            this.setX(this.getMaxX());
        }
    }
    public void movingY(int speed) {
        this.moveY(speed);
        if(this.getY() < min_y) {
            this.setY(min_y);
        }
        if(this.getY() > this.getMaxY()) {
            this.setY(this.getMaxY());
        }
    }

    public ShotType generateShot(int damage) {
        ShotType shot = new ShotType((int)this.getX(), (int)this.getY(), this.getMaxX(), this.getMaxY(), damage);
        return shot;
    }
    
    public void drawPlayer(Graphics g) {
        // Drawing player image
        g.drawImage(getImage(), (int)this.getX() - 27, (int)this.getY() - 8, null);
    }
}
