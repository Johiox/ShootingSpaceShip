/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shootingspaceship;

/**
 *
 * @author msChoi
 */
class ShotType extends Shot{
    private int damage;
    
    public ShotType(int x, int y, int damage) {
        super(x, y);
        this.damage = damage;
    }
    
    public int getDamage() {
        return damage;
    }
    
    public int collided() {
        this.setAlive(false);
        this.setX(0);
        this.setY(0);
        return damage;
    }
}
