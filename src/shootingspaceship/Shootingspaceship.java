package shootingspaceship;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Shootingspaceship extends JPanel implements Runnable {

    private Thread th;
    private Player player;
    private ShotType[][] shots;
    private int[] shotCount;
    private ArrayList enemies;
    private ArrayList items;
    private int shotSpeed = -5;
    private int playerLeftSpeed = -3;
    private int playerRightSpeed = 3;
    private int playerUpSpeed = -3;
    private int playerDownSpeed = 3;
    private final int width = 1280;
    private final int height = 1024;
    private final int playerMargin = 10;
    private final int enemyMaxDownSpeed = 2;
    private final int enemyMaxHorizonSpeed = 2;
    private final int enemyTimeGap = 500; //unit: msec
    private final float enemyDownSpeedInc = 0.3f;
    private final int maxEnemySize = 1000;
    private int enemySize;
    private javax.swing.Timer timer;
    private boolean playerMoveLeft;
    private boolean playerMoveRight;
    private boolean playerMoveUp;
    private boolean playerMoveDown;
    private boolean playerFire;
    private boolean gamePause;
    
    private Image dbImage;
    private Graphics dbg;
    private Random rand;
    private int countFire;
    private final int maxFirstShot = 200;
    private final int maxSecondShot = 100;
    private final int maxThirdShot = 50;
    private final int maxFourthShot = 25;
    private final int shotType = 4;
    private int increaseSecondShot;
    private int increaseThirdShot;
    private int increaseFourthShot;
    private int maxShotNum = 20;
    private int currentShot;
    private int playerHealth;
    private final int infiniteFire = 200;

    public Shootingspaceship() {
        setBackground(Color.black);
        setPreferredSize(new Dimension(width, height));
        player = new Player(width / 2, (int) (height * 0.9), playerMargin, width-playerMargin, playerMargin, height-playerMargin);
        shots = new ShotType[4][];
        shots[0] = new ShotType[maxFirstShot];
        shots[1] = new ShotType[maxSecondShot];
        shots[2] = new ShotType[maxThirdShot];
        shots[3] = new ShotType[maxFourthShot];
        shotCount = new int[4];
        shotCount[0] = infiniteFire;
        for(int i = 1; i < shotCount.length; ++i) {
            shotCount[i] = 25;
        }
        increaseSecondShot = 32;
        increaseThirdShot = 16;
        increaseFourthShot = 8;
        currentShot = 0;
        countFire = 0;
        enemies = new ArrayList();
        items = new ArrayList();
        enemySize = 0;
        rand = new Random(1);
        timer = new javax.swing.Timer(enemyTimeGap, new addANewEnemy());
        timer.start();
        addKeyListener(new ShipControl());
        addMouseListener(new ShipControl());
        setFocusable(true);
        gamePause = false;
        playerHealth = 3;
    }

    public void start() {
        th = new Thread(this);
        th.start();
    }

    private class addANewEnemy implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (++enemySize <= maxEnemySize) {
                float downspeed;
                do {
                    downspeed = rand.nextFloat() * enemyMaxDownSpeed;
                } while (downspeed == 0);

                float horspeed = rand.nextFloat() * 2 * enemyMaxHorizonSpeed - enemyMaxHorizonSpeed;
                //System.out.println("enemySize=" + enemySize + " downspeed=" + downspeed + " horspeed=" + horspeed);

                Enemy newEnemy = new Enemy((int) (rand.nextFloat() * width), 0, horspeed, downspeed, width, height, enemyDownSpeedInc, 10);
                enemies.add(newEnemy);
            } else {
                timer.stop();
            }
        }
    }

    private class ShipControl implements KeyListener,MouseListener {
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    playerMoveLeft = true;
                    break;
                case KeyEvent.VK_D:
                    playerMoveRight = true;
                    break;
                case KeyEvent.VK_W:
                    playerMoveUp = true;
                    break;
                case KeyEvent.VK_S:
                    playerMoveDown = true;
                    break;
                case KeyEvent.VK_SHIFT:
                    shotSpeed = -5 / 2;
                    playerLeftSpeed = -3 / 2;
                    playerRightSpeed = 3 / 2;
                    playerUpSpeed = -3 / 2;
                    playerDownSpeed = 3 / 2;
                    break;
                case KeyEvent.VK_P:
                    if(gamePause) {
                        gamePause = false;
                    }
                    else {
                        gamePause = true;
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    System.exit(0);
                /*case KeyEvent.VK_UP:
                    // generate new shot and add it to shots array
                    for (int i = 0; i < shots.length; i++) {
                        if (shots[i] == null) {
                            shots[i] = player.generateShot();
                            break;
                        }
                    }
                    break;*/
            }
        }

        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    playerMoveLeft = false;
                    break;
                case KeyEvent.VK_D:
                    playerMoveRight = false;
                    break;
                case KeyEvent.VK_W:
                    playerMoveUp = false;
                    break;
                case KeyEvent.VK_S:
                    playerMoveDown = false;
                    break;
                case KeyEvent.VK_SHIFT:
                    shotSpeed = -5;
                    playerLeftSpeed = -3;
                    playerRightSpeed = 3;
                    playerUpSpeed = -3;
                    playerDownSpeed = 3;
                    break;
            }
        }
        public void mousePressed(MouseEvent e) {
            switch(e.getButton()) {
                case MouseEvent.BUTTON1:
                    playerFire = true;
                    break;
                case MouseEvent.BUTTON3:
                    currentShot = (++currentShot) % shotType;
                    break;
            }
        }
        public void mouseReleased(MouseEvent e) {
            switch(e.getButton()) {
                case MouseEvent.BUTTON1:
                    playerFire = false;
                    break;
            }
        }
        public void keyTyped(KeyEvent e) {
        }
        public void mouseClicked(MouseEvent e) {
        }
        public void mouseEntered(MouseEvent e) {
        }
        public void mouseExited(MouseEvent e) {
        }
    }

    public void run() {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        //int c=0;

        while (true) {
            //System.out.println( ++c );
            // do operations on shots in shots array
            for(int j = 0; j < shots.length; ++j) {
                for (int i = 0; i < shots[j].length; i++) {
                    if (shots[j][i] != null) {
                        // move shot
                        shots[j][i].moveShot(shotSpeed);

                        // test if shot is out
                        if (shots[j][i].getY() < 0) {
                            // remove shot from array
                            shots[j][i] = null;
                        }
                    }
                }
            }

            if (playerMoveLeft) {
                player.movingX(playerLeftSpeed);
            }
            if (playerMoveRight) {
                player.movingX(playerRightSpeed);
            }
            if(playerMoveUp) {
                player.movingY(playerUpSpeed);
            }
            if(playerMoveDown) {
                player.movingY(playerDownSpeed);
            }
            if(playerFire) {
                if(countFire == 10) { // single shot and wait nine times
                    for(int i = 0; i < shots[currentShot].length; ++i) {
                        if(0 < shotCount[currentShot])
                            if(shots[currentShot][i] == null) {
                                shots[currentShot][i] = player.generateShot(currentShot + 1);
                                if(currentShot != 0) {
                                    --shotCount[currentShot];
                                }
                                break;
                            }
                    }
                    countFire = 0;
                }
                else {
                    ++countFire;
                }
            }

            Iterator enemyList = enemies.iterator();
            while (enemyList.hasNext()) {
                Enemy enemy = (Enemy) enemyList.next();
                enemy.move();
            }
            Iterator itemList = items.iterator();
            while(itemList.hasNext()) {
                Item item = (Item)itemList.next();
                item.move();
            }

            repaint();
            
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                // do nothing
            }

            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
    }

    public void initImage(Graphics g) {
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        dbg.setColor(getForeground());
        //paint (dbg);

        g.drawImage(dbImage, 0, 0, this);
    }

    public void paintComponent(Graphics g) {
        initImage(g);

        // draw player
        player.drawPlayer(g);

        Iterator enemyList = enemies.iterator();
        while (enemyList.hasNext()) {
            Enemy enemy = (Enemy) enemyList.next();
            enemy.draw(g);
            if (enemy.isCollidedWithShot(shots)) {
                if(enemy.getHealth() < 0) {
                    if(rand.nextInt(9) <= 2) { // Creation Item is 1/2
                        Item newItem = new Item((int)enemy.getX(), (int)enemy.getY(), enemy.getDeltaX(), enemy.getDeltaY(), enemy.getMaxX(), enemy.getMaxY(), enemy.getDeltaYINC(), rand.nextInt(4));
                        items.add(newItem);
                    }
                    enemyList.remove();
                    --enemySize;
                }
            }
            Iterator itemList = items.iterator();
            if(itemList.hasNext()) {
                Item item = (Item)itemList.next();
                item.draw(g);
                if(item.count()) {
                    itemList.remove();
                }
                if(item.isCollidedWithPlayer(player)) {
                    switch(item.getItemNum()) {
                        case 0:
                            if(this.shotCount[1] + increaseSecondShot > maxSecondShot) {
                                this.shotCount[1] = maxSecondShot;
                            }
                            else {
                                this.shotCount[1] += increaseSecondShot;
                            }
                            break;
                        case 1:
                            if(this.shotCount[2] + increaseThirdShot > maxThirdShot) {
                                this.shotCount[2] = maxThirdShot;
                            }
                            else {
                                this.shotCount[2] += increaseThirdShot;
                            }
                            break;
                        case 2:
                            if(this.shotCount[3] + increaseFourthShot > maxFourthShot) {
                                this.shotCount[3] = maxFourthShot;
                            }
                            else {
                                this.shotCount[3] += increaseFourthShot;
                            }
                            break;
                        case 3:
                            break;
                    }
                }
            }
            if (enemy.isCollidedWithPlayer(player)) {
                if(playerHealth < 0) {
                    enemyList.remove();
                    System.exit(0);
                }
                else {
                    --playerHealth;
                }
            }
            if(enemy.isGrazeWithPlayer(player)) {
                enemy.grazedEnemy(g);
            }
        }

        // draw shots
        for(int j = 0; j < shots.length; ++j) {
            for (int i = 0; i < shots[j].length; i++) {
                if (shots[j][i] != null) {
                    shots[j][i].drawShot(g);
                }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame frame = new JFrame("Shooting");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Shootingspaceship ship = new Shootingspaceship();
        frame.getContentPane().add(ship);
        frame.pack();
        frame.setVisible(true);
        ship.start();
    }
}
