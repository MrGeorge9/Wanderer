import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board extends JComponent implements KeyListener {

    GameScreen gameScreen = new GameScreen();
    Hero hero = new Hero();
    Boss boss = new Boss();
    Monster skeleton1 = new Monster();
    Monster skeleton2 = new Monster();
    Monster skeleton3 = new Monster();
    public boolean bossIsAlive = true;
    public boolean skeleton1IsAlive = true;
    public boolean skeleton2IsAlive = true;
    public boolean skeleton3IsAlive = true;
    public boolean isFighting = false;
    public int heroMovement = 0;
    int heroX;
    int heroY;
    String heroPosition = "img/hero-down.png";

    public Board() {
        heroX = 0;
        heroY = 0;
        setPreferredSize(new Dimension(720, 720));
        setVisible(true);
    }

    @Override
    public void paint(Graphics graphics) {

        super.paint(graphics);
        gameScreen.floorTiles(graphics);
        getHeroStats(graphics);
        drawArenaLevel(graphics);
        if (skeleton1IsAlive) {
            gameScreen.drawSkeleton1(graphics);
        }
        if (skeleton2IsAlive) {
            gameScreen.drawSkeleton2(graphics);
        }
        if (skeleton3IsAlive) {
            gameScreen.drawSkeleton3(graphics);
        }
        if (bossIsAlive) {
            gameScreen.drawBoss(graphics);
        }
        drawHero(graphics);

        System.out.println("-------------");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(gameScreen.gameboard[i][j]);
            }
            System.out.println();
        }
        if (heroMovement==0) {
            drawBeginning(graphics);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("RPG Game");
        Board board = new Board();
        frame.add(board);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
        frame.addKeyListener(board);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (isFighting) {
            fight(e);
            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_W) {
            if (heroMovement %2==0) {
                heroPosition = "img/hero-up.png";
            } else {
                heroPosition = "img/hero-up2.png";
            }
            heroMovement++;

            if (gameScreen.isReachable(heroX, heroY -1)){
                heroY -=1;
                gameScreen.gameboard[heroY +1][heroX] = 0;
            }
            monsterMove();

            if (battleCondition() || reverseBattleConditions()) {
                    this.getMonsterStats();
                    PositionedImage Fight = new PositionedImage("img/fight.png", 210, 50);
                    Fight.draw(getGraphics());
                    isFighting = true;

            } else {
                gameScreen.gameboard[heroY][heroX] = 2;
                repaint();
            }

        } else if(e.getKeyCode() == KeyEvent.VK_S) {
            if (heroMovement %2==0) {
                heroPosition = "img/hero-down.png";
            } else {
                heroPosition = "img/hero-down2.png";
            }
            heroMovement++;

            if (gameScreen.isReachable(heroX, heroY +1)) {
                heroY += 1;
                gameScreen.gameboard[heroY -1][heroX] = 0;
            }
            monsterMove();

            if (battleCondition() || reverseBattleConditions()) {
                this.getMonsterStats();
                PositionedImage Fight = new PositionedImage("img/fight.png", 210, 50);
                Fight.draw(getGraphics());
                isFighting = true;

            } else {
                gameScreen.gameboard[heroY][heroX] = 2;
                repaint();
            }

        } else if(e.getKeyCode() == KeyEvent.VK_D) {
            if (heroMovement %2==0) {
                heroPosition = "img/hero-right.png";
            } else {
                heroPosition = "img/hero-right2.png";
            }
            heroMovement++;

            if (gameScreen.isReachable(heroX +1, heroY)) {
                heroX += 1;
                gameScreen.gameboard[heroY][heroX -1] = 0;
            }
            monsterMove();

            if (battleCondition() || reverseBattleConditions()) {
                this.getMonsterStats();
                PositionedImage Fight = new PositionedImage("img/fight.png", 210, 50);
                Fight.draw(getGraphics());
                isFighting = true;

            } else {
                gameScreen.gameboard[heroY][heroX] = 2;
                repaint();
            }

        } else if(e.getKeyCode() == KeyEvent.VK_A) {
            if (heroMovement %2==0) {
                heroPosition = "img/hero-left.png";
            } else {
                heroPosition = "img/hero-left2.png";
            }
            heroMovement++;


            if (gameScreen.isReachable(heroX -1, heroY)) {
                heroX -= 1;
                gameScreen.gameboard[heroY][heroX +1] = 0;
            }
            monsterMove();

            if (battleCondition() || reverseBattleConditions()) {
                this.getMonsterStats();
                PositionedImage Fight = new PositionedImage("img/fight.png", 210, 50);
                Fight.draw(getGraphics());
                isFighting = true;

            } else {
                gameScreen.gameboard[heroY][heroX] = 2;
                repaint();
            }
        }
    }


    public void fight (KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (gameScreen.gameboard[heroY][heroX] == 3) {
                if (boss.currentHP>0){
                    hero.battle(boss, getGraphics());
                    this.getMonsterStats();
                    this.getHeroStats(getGraphics());
                } else {
                    bossIsAlive = false;
                    isFighting = false;
                    repaint();
                }

            }
            if (gameScreen.gameboard[heroY][heroX] == 4) {
                if (skeleton1.currentHP>0){
                    hero.battle(skeleton1, getGraphics());
                    this.getMonsterStats();
                    this.getHeroStats(getGraphics());
                } else {
                    skeleton1IsAlive = false;
                    if (!skeleton2IsAlive && !skeleton3IsAlive) {
                        giveKey();
                    } else {
                        int keyChance = (int)(Math.random()*5);
                        if (keyChance==1){
                            giveKey();
                        }
                    }
                    isFighting = false;
                    repaint();
                }
            }
            if (gameScreen.gameboard[heroY][heroX] == 5) {
                if (skeleton2.currentHP>0){
                    hero.battle(skeleton2, getGraphics());
                    this.getMonsterStats();
                    this.getHeroStats(getGraphics());
                } else {
                    skeleton2IsAlive = false;
                    if (!skeleton1IsAlive && !skeleton3IsAlive) {
                        giveKey();
                    } else {
                        int keyChance = (int)(Math.random()*5);
                        if (keyChance==1){
                            giveKey();
                        }
                    }
                    isFighting = false;
                    repaint();
                }
            }
            if (gameScreen.gameboard[heroY][heroX] == 6) {
                if (skeleton3.currentHP>0){
                    hero.battle(skeleton3, getGraphics());
                    this.getMonsterStats();
                    this.getHeroStats(getGraphics());
                } else {
                    skeleton3IsAlive = false;
                    if (!skeleton1IsAlive && !skeleton2IsAlive) {
                        giveKey();
                    } else {
                        int keyChance = (int)(Math.random()*5);
                        if (keyChance==1){
                            giveKey();
                        }
                    }
                    isFighting = false;
                    repaint();
                }
            }
            if (gameScreen.gameboard[gameScreen.skeleton1Y][gameScreen.skeleton1X] == 2) {
                if (skeleton1.currentHP>0){
                    skeleton1.battle(hero, getGraphics());
                    this.getMonsterStats();
                    this.getHeroStats(getGraphics());
                } else {
                    skeleton1IsAlive = false;
                    if (!skeleton2IsAlive && !skeleton3IsAlive) {
                        giveKey();
                    } else {
                        int keyChance = (int)(Math.random()*5);
                        if (keyChance==1){
                            giveKey();
                        }
                    }
                    isFighting = false;
                    repaint();
                }
            }
            if (gameScreen.gameboard[gameScreen.skeleton2Y][gameScreen.skeleton2X] == 2) {
                if (skeleton2.currentHP>0){
                    skeleton2.battle(hero, getGraphics());
                    this.getMonsterStats();
                    this.getHeroStats(getGraphics());
                } else {
                    skeleton2IsAlive = false;
                    if (!skeleton1IsAlive && !skeleton3IsAlive) {
                        giveKey();
                    } else {
                        int keyChance = (int)(Math.random()*5);
                        if (keyChance==1){
                            giveKey();
                        }
                    }
                    isFighting = false;
                    repaint();
                }
            }
            if (gameScreen.gameboard[gameScreen.skeleton3Y][gameScreen.skeleton3X] == 2) {
                if (skeleton3.currentHP>0){
                    skeleton3.battle(hero, getGraphics());
                    this.getMonsterStats();
                    this.getHeroStats(getGraphics());
                } else {
                    skeleton3IsAlive = false;
                    if (!skeleton1IsAlive && !skeleton2IsAlive) {
                        giveKey();
                    } else {
                        int keyChance = (int)(Math.random()*5);
                        if (keyChance==1){
                            giveKey();
                        }
                    }
                    isFighting = false;
                    repaint();
                }
            }
            if (gameScreen.gameboard[gameScreen.bossY][gameScreen.bossX] == 2) {
                if (boss.currentHP > 0) {
                    boss.battle(hero, getGraphics());
                    this.getMonsterStats();
                    this.getHeroStats(getGraphics());
                } else {
                    bossIsAlive = false;
                    isFighting = false;
                    repaint();
                }
            }
        }
    }

    public boolean battleCondition () {
        if (gameScreen.gameboard[heroY][heroX] == 3 ||
                gameScreen.gameboard[heroY][heroX] == 4 ||
                gameScreen.gameboard[heroY][heroX] == 5 ||
                gameScreen.gameboard[heroY][heroX] == 6) {
            return true;
        } else {
            return false;
        }
    }

    public boolean reverseBattleConditions () {
        if (    (gameScreen.gameboard[gameScreen.skeleton1Y][gameScreen.skeleton1X] == 2) ||
                (gameScreen.gameboard[gameScreen.skeleton2Y][gameScreen.skeleton2X] == 2) ||
                (gameScreen.gameboard[gameScreen.skeleton3Y][gameScreen.skeleton3X] == 2) ||
                (gameScreen.gameboard[gameScreen.bossX][gameScreen.bossY] == 2) ){
        return true;
        } else return false;
    }

    public void getMonsterStats() {
        if (gameScreen.gameboard[heroY][heroX] == 3) {
            boss.charStats(getGraphics());
        } else if (gameScreen.gameboard[heroY][heroX] == 4) {
            skeleton1.charStats(getGraphics());
        } else if (gameScreen.gameboard[heroY][heroX] == 5) {
            skeleton2.charStats(getGraphics());
        } else if (gameScreen.gameboard[heroY][heroX] == 6) {
            skeleton3.charStats(getGraphics());
        }
    }

    public void getHeroStats (Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(490, 700, 230, 20);
        graphics.setColor(Color.BLACK);
        graphics.drawString("Hero (level " + hero.level + ") HP: " + hero.currentHP + "/" + hero.maxHP + " | DP: " + hero.defenderPoint +
                " | SP: " + hero.strikePoint , 495, 715);
    }

    public void monsterMove () {
        if (skeleton1IsAlive) {
            gameScreen.movingSkeleton1(getGraphics());
        }
        if (skeleton2IsAlive) {
            gameScreen.movingSkeleton2(getGraphics());
        }
        if (skeleton3IsAlive) {
            gameScreen.movingSkeleton3(getGraphics());
        }
        if (bossIsAlive) {
            gameScreen.moveBoss(getGraphics());
        }
    }

    public void drawBeginning(Graphics graphics) {
        PositionedImage wander = new PositionedImage("img/Wanderer.png", 120, 170);
        wander.draw(graphics);
        PositionedImage press = new PositionedImage("img/Press.png", 220, 260);
        press.draw(graphics);
    }

    public void giveKey () {
        heroX = 0;
        heroY = 0;

        int hpChance = (int)(Math.random()*100);
        if (hpChance<10) {
            hero.currentHP = hero.maxHP;
        } else if (hpChance<50) {
            if ((hero.currentHP + hero.maxHP/3) > hero.maxHP) {
                hero.currentHP = hero.maxHP;
            } else {
                hero.currentHP = hero.currentHP + hero.maxHP/3;
            }
        } else {
            if ((hero.currentHP + hero.maxHP/10) > hero.maxHP) {
                hero.currentHP = hero.maxHP;
            } else {
                hero.currentHP = hero.currentHP + hero.maxHP/10;
            }
        }

        Character.level++;
        skeleton1 = new Monster();
        skeleton2 = new Monster();
        skeleton3 = new Monster();
        boss = new Boss();

        skeleton1IsAlive = true;
        gameScreen.getSkeleton();
        gameScreen.skeleton1X = gameScreen.skeletonX;
        gameScreen.skeleton1Y = gameScreen.skeletonY;

        skeleton2IsAlive = true;
        gameScreen.getSkeleton();
        gameScreen.skeleton2X = gameScreen.skeletonX;
        gameScreen.skeleton2Y = gameScreen.skeletonY;

        skeleton3IsAlive = true;
        gameScreen.getSkeleton();
        gameScreen.skeleton3X = gameScreen.skeletonX;
        gameScreen.skeleton3Y = gameScreen.skeletonY;

        bossIsAlive = true;
        gameScreen.getBoss();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (gameScreen.gameboard[i][j]!=1) {
                    gameScreen.gameboard [i][j] = 0;
                }
            }
        }

    }

    public void drawHero (Graphics graphics) {
        PositionedImage imageHero = new PositionedImage(heroPosition, heroX *72, heroY *72);
        imageHero.draw(graphics);
    }

    public void drawArenaLevel (Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, 80, 15);
        graphics.setColor(Color.WHITE);
        graphics.drawString("Arena level: " + Character.level, 5, 13);
    }

}
