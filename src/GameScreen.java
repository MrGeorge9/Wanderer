import java.awt.*;

public class GameScreen {

    int skeletonX;
    int skeletonY;
    int skeleton1X;
    int skeleton1Y;
    int skeleton2X;
    int skeleton2Y;
    int skeleton3X;
    int skeleton3Y;
    int bossX;
    int bossY;
    public int skeletonCycle1 = 0;
    public int skeletonCycle2 = 0;
    public int skeletonCycle3 = 0;
    public int bossCycle = 0;
    int [][] gameboard =
            {       {0,0,0,1,0,0,0,0,0,0},
                    {0,1,1,1,0,1,0,1,1,0},
                    {0,0,0,0,0,1,0,1,1,0},
                    {0,0,0,0,0,1,0,0,0,0},
                    {1,1,1,1,0,1,1,1,1,0},
                    {0,1,0,1,0,0,0,0,0,0},
                    {0,1,0,1,0,1,1,0,1,0},
                    {0,0,0,0,0,1,1,0,1,0},
                    {0,1,1,1,0,0,0,0,1,0},
                    {0,0,0,1,0,1,1,0,0,0},
            };

    public boolean isReachable (int x, int y) {
        if ((x>=0) && (y>=0) && (x<10) && (y<10)) {
            if (gameboard[y][x] != 1) {
                return true;
            }
        }
        return false;
    }
    public void floorTiles (Graphics graphics) {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (gameboard[y][x] == 1) {
                    PositionedImage floorTile = new PositionedImage("img/wall.png", x*72, y*72);
                    floorTile.draw(graphics);
                } else {
                    PositionedImage walls = new PositionedImage("img/floor.png", x*72, y*72);
                    walls.draw(graphics);
                }
            }
        }
    }
    public void getSkeleton() {
        int x = (int)(Math.random()*10);
        int y = (int)(Math.random()*10);
        while (!isReachable(x, y)) {
            x = (int)(Math.random()*10);
            y = (int)(Math.random()*10);
        }
        skeletonX = x;
        skeletonY = y;
    }
    public void movingSkeleton1 (Graphics graphics) {
        if (skeletonCycle1 == 0 ) {
            this.getSkeleton();
            skeleton1X = skeletonX;
            skeleton1Y = skeletonY;
        }
        if (skeletonCycle1 %2==0) {
            boolean isMoving = false;
            while (!isMoving) {
                if (    (!isReachable(skeleton1X + 1, skeleton1Y)) && (!isReachable(skeleton1X - 1, skeleton1Y)) &&
                        (!isReachable(skeleton1X, skeleton1Y + 1)) && (!isReachable(skeleton1X, skeleton1Y - 1)) ) {
                    isMoving = true;
                } else {
                    int x = (int) (Math.random() * 4);
                    switch (x) {
                        case 0: {
                            if (isReachable(skeleton1X + 1, skeleton1Y)) {
                                skeleton1X += 1;
                                gameboard[skeleton1Y][skeleton1X-1] = 0;
                                gameboard[skeleton1Y][skeleton1X] = 4;
                                isMoving = true;
                            }
                            break;
                        }
                        case 1: {
                            if (isReachable(skeleton1X - 1, skeleton1Y)) {
                                skeleton1X -= 1;
                                gameboard[skeleton1Y][skeleton1X+1] = 0;
                                gameboard[skeleton1Y][skeleton1X] = 4;
                                isMoving = true;
                            }
                            break;
                        }
                        case 2: {
                            if (isReachable(skeleton1X, skeleton1Y + 1)) {
                                skeleton1Y += 1;
                                gameboard[skeleton1Y-1][skeleton1X] = 0;
                                gameboard[skeleton1Y][skeleton1X] = 4;
                                isMoving = true;
                            }
                            break;
                        }
                        case 3: {
                            if (isReachable(skeleton1X, skeleton1Y - 1)) {
                                skeleton1Y -= 1;
                                gameboard[skeleton1Y+1][skeleton1X] = 0;
                                gameboard[skeleton1Y][skeleton1X] = 4;
                                isMoving = true;
                            }
                            break;
                        }
                    }
                }
            }
        }
        skeletonCycle1++;
    }
    public void movingSkeleton2 (Graphics graphics) {
        if (skeletonCycle2 == 0 ) {
            this.getSkeleton();
            skeleton2X = skeletonX;
            skeleton2Y = skeletonY;
        }
        if (skeletonCycle2 %2==0) {
            boolean isMoving = false;
            while (!isMoving) {
                if (    (!isReachable(skeleton2X + 1, skeleton2Y)) && (!isReachable(skeleton2X - 1, skeleton2Y)) &&
                        (!isReachable(skeleton2X, skeleton2Y + 1)) && (!isReachable(skeleton2X, skeleton2Y - 1)) ) {
                    isMoving = true;
                } else {
                    int x = (int) (Math.random() * 4);
                    switch (x) {
                        case 0: {
                            if (isReachable(skeleton2X + 1, skeleton2Y)) {
                                skeleton2X += 1;
                                gameboard[skeleton2Y][skeleton2X-1] = 0;
                                gameboard[skeleton2Y][skeleton2X] = 5;
                                isMoving = true;
                            }
                            break;
                        }
                        case 1: {
                            if (isReachable(skeleton2X - 1, skeleton2Y)) {
                                skeleton2X -= 1;
                                gameboard[skeleton2Y][skeleton2X+1] = 0;
                                gameboard[skeleton2Y][skeleton2X] = 5;
                                isMoving = true;
                            }
                            break;
                        }
                        case 2: {
                            if (isReachable(skeleton2X, skeleton2Y + 1)) {
                                skeleton2Y += 1;
                                gameboard[skeleton2Y-1][skeleton2X] = 0;
                                gameboard[skeleton2Y][skeleton2X] = 5;
                                isMoving = true;
                            }
                            break;
                        }
                        case 3: {
                            if (isReachable(skeleton2X, skeleton2Y - 1)) {
                                skeleton2Y -= 1;
                                gameboard[skeleton2Y+1][skeleton2X] = 0;
                                gameboard[skeleton2Y][skeleton2X] = 5;
                                isMoving = true;
                            }
                            break;
                        }
                    }
                }
            }
        }
        skeletonCycle2++;
    }
    public void movingSkeleton3 (Graphics graphics) {
        if (skeletonCycle3 == 0 ) {
            this.getSkeleton();
            skeleton3X = skeletonX;
            skeleton3Y = skeletonY;
        }
        if (skeletonCycle3 %2==0) {
            boolean isMoving = false;
            while (!isMoving) {
                if (    (!isReachable(skeleton3X + 1, skeleton3Y)) && (!isReachable(skeleton3X - 1, skeleton3Y)) &&
                        (!isReachable(skeleton3X, skeleton3Y + 1)) && (!isReachable(skeleton3X, skeleton3Y - 1)) ) {
                    isMoving = true;
                } else {
                    int x = (int) (Math.random() * 4);
                    switch (x) {
                        case 0: {
                            if (isReachable(skeleton3X + 1, skeleton3Y)) {
                                skeleton3X += 1;
                                gameboard[skeleton3Y][skeleton3X-1] = 0;
                                gameboard[skeleton3Y][skeleton3X] = 6;
                                isMoving = true;
                            }
                            break;
                        }
                        case 1: {
                            if (isReachable(skeleton3X - 1, skeleton3Y)) {
                                skeleton3X -= 1;
                                gameboard[skeleton3Y][skeleton3X+1] = 0;
                                gameboard[skeleton3Y][skeleton3X] = 6;
                                isMoving = true;
                            }
                            break;
                        }
                        case 2: {
                            if (isReachable(skeleton3X, skeleton3Y + 1)) {
                                skeleton3Y += 1;
                                gameboard[skeleton3Y-1][skeleton3X] = 0;
                                gameboard[skeleton3Y][skeleton3X] = 6;
                                isMoving = true;
                            }
                            break;
                        }
                        case 3: {
                            if (isReachable(skeleton3X, skeleton3Y - 1)) {
                                skeleton3Y -= 1;
                                gameboard[skeleton3Y+1][skeleton3X] = 0;
                                gameboard[skeleton3Y][skeleton3X] = 6;
                                isMoving = true;
                            }
                            break;
                        }
                    }
                }
            }
        }
        skeletonCycle3++;
    }
    public void drawSkeleton1(Graphics graphics) {
            PositionedImage skeleton = new PositionedImage("img/skeleton.png", skeleton1X * 72, skeleton1Y * 72);
            skeleton.draw(graphics);
    }
    public void drawSkeleton2(Graphics graphics) {
        PositionedImage skeleton = new PositionedImage("img/skeleton.png", skeleton2X * 72, skeleton2Y * 72);
        skeleton.draw(graphics);
    }
    public void drawSkeleton3(Graphics graphics) {
        PositionedImage skeleton = new PositionedImage("img/skeleton.png", skeleton3X * 72, skeleton3Y * 72);
        skeleton.draw(graphics);
    }
    public void getBoss () {
        int x = (int)(Math.random()*10);
        int y = (int)(Math.random()*10);
        while (!isReachable(x, y)) {
            x = (int) (Math.random() * 10);
            y = (int) (Math.random() * 10);
        }
        bossX = x;
        bossY = y;
        gameboard[y][x] = 3;
    }
    public void moveBoss(Graphics graphics) {
        if (bossCycle == 0 ) {
            this.getBoss();
        }
        if (bossCycle%2==0) {
            boolean isMoving = false;
            while (!isMoving) {
                if (    (!isReachable(bossX + 1, bossY)) && (!isReachable(bossX - 1, bossY)) &&
                        (!isReachable(bossX, bossY + 1)) && (!isReachable(bossX, bossY - 1)) ) {
                    isMoving = true;
                } else {
                    int x = (int) (Math.random() * 4);
                    switch (x) {
                        case 0: {
                            if (isReachable(bossX + 1, bossY)) {
                                bossX += 1;
                                gameboard[bossY][bossX-1] = 0;
                                gameboard[bossY][bossX] = 3;
                                isMoving = true;
                            }
                            break;
                        }
                        case 1: {
                            if (isReachable(bossX - 1, bossY)) {
                                bossX -= 1;
                                gameboard[bossY][bossX+1] = 0;
                                gameboard[bossY][bossX] = 3;
                                isMoving = true;
                            }
                            break;
                        }
                        case 2: {
                            if (isReachable(bossX, bossY +1)) {
                                bossY += 1;
                                gameboard[bossY-1][bossX] = 0;
                                gameboard[bossY][bossX] = 3;
                                isMoving = true;
                            }
                            break;
                        }
                        case 3: {
                            if (isReachable(bossX, bossY -1)) {
                                bossY -= 1;
                                gameboard[bossY +1][bossX] = 0;
                                gameboard[bossY][bossX] = 3;
                                isMoving = true;
                            }
                            break;
                        }
                    }
                }
            }
        }
        bossCycle++;
    }
    public void drawBoss(Graphics graphics) {
        PositionedImage bigBoss = new PositionedImage("img/boss.png", bossX*72, bossY*72);
        bigBoss.draw(graphics);
    }
}
