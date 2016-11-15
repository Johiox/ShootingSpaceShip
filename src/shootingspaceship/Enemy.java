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
class Enemy extends Sprite{
    private float delta_x;
    private float delta_y;
    private int max_x;
    private int max_y;
    private float delta_y_inc;
    private final int collision_distance = 10;
    private int healthPoint;

    public Enemy(int x, int y, float delta_x, float delta_y, int max_x, int max_y, float delta_y_inc, int healthPoint) {
        super(x, y);
        this.delta_x = delta_x;
        this.delta_y = delta_y;
        this.max_x = max_x;
        this.max_y = max_y;
        this.delta_y_inc = delta_y_inc;
        this.healthPoint = healthPoint;
    }
    
    public int getHealth() {
        return healthPoint;
    }

    public void move() {
        this.moveX(delta_x);
        this.moveY(delta_y);

        if (this.getX() < 0) {
            this.setX(0);
            delta_x = -delta_x;
        }
        else if (this.getX() > max_x) {
            this.setX(max_x);
            delta_x = -delta_x;
        }
        if (this.getY() > max_y) {
            this.setY(0);
            delta_y += delta_y_inc;
        }
    }

    public boolean isCollidedWithShot(ShotType[][] shots) {
        for(ShotType[] shoting : shots) {
            for (ShotType shot : shoting) {
                if (shot == null) {
                    continue;
                }
                if (-collision_distance <= (this.getY() - shot.getY()) && (this.getY() - shot.getY() <= collision_distance)) {
                    if (-collision_distance <= (this.getX() - shot.getX()) && (this.getX() - shot.getX() <= collision_distance)) {
                        //collided.
                        healthPoint -= shot.collided();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isCollidedWithPlayer(Player player) {
        if (-collision_distance <= (this.getY() - player.getY()) && (this.getY() - player.getY() <= collision_distance)) {
            if (-collision_distance <= (this.getX() - player.getX()) && (this.getX() - player.getX() <= collision_distance)) {
                //collided.
                return true;
            }
        }
        return false;
    }
    
    public boolean isGrazeWithPlayer(Player player) {
        if(-collision_distance - 15 <= (this.getY() - player.getY()) && (this.getY() - player.getY() <= collision_distance + 15)) {
            if(-collision_distance - 15 <= (this.getX() - player.getX()) && (this.getX() - player.getX() <= collision_distance + 15)) {
                return true;
            }
        }
        return false;
    }

    public void draw(Graphics g) {
        g.setColor(Color.yellow);
        int[] x_poly = {(int)this.getX(), (int)this.getX() - 10, (int)this.getX(), (int)this.getX() + 10};
        int[] y_poly = {(int)this.getY() + 15, (int)this.getY(), (int)this.getY() + 10, (int)this.getY()};
        g.fillPolygon(x_poly, y_poly, 4);
    }
    
    public void grazedEnemy(Graphics g) {
        g.setColor(Color.blue);
        int[] x_poly = {(int)this.getX(), (int)this.getX() - 10, (int)this.getX(), (int)this.getX() + 10};
        int[] y_poly = {(int)this.getY() + 15, (int)this.getY(), (int)this.getY() + 10, (int)this.getY()};
        g.fillPolygon(x_poly, y_poly, 4);
    }
}