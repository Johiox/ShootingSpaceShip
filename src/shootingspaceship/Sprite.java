/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shootingspaceship;

/**
 *
 * @author mschoi
 */
class Sprite {
    private float pos_x;
    private float pos_y;
    private int max_x;
    private int max_y;
    public Sprite(float pos_x, float pos_y, int max_x, int max_y) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.max_x = max_x;
        this.max_y = max_y;
    }
    public float getX() {
        return pos_x;
    }
    public void setX(float pos_x) {
        this.pos_x = pos_x;
    }
    public float getY() {
        return pos_y;
    }
    public void setY(float pos_y) {
        this.pos_y = pos_y;
    }
    public void moveX(float delta) {
        this.pos_x += delta;
    }
    public void moveY(float delta) {
        this.pos_y += delta;
    }
    public int getMaxX() {
        return max_x;
    }
    public int getMaxY() {
        return max_y;
    }
}
