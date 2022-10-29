import java.awt.*;

public class SkeletonsBackup {

    public class GameScreen {

        int [][] skeletons = new int[3][2];
        int [] boss = new int[2];
        int skeletonX;
        int skeletonY;
        int bossX;
        int bossY;

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
        public int skeletonCycle = 0;
        public int bossCycle = 0;
        public int numberOfSkeletons = 3;

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

        public void getSkeletons () {
            int x = (int)(Math.random()*10);
            int y = (int)(Math.random()*10);
            for (int i = 0; i < 3; i++) {
                while (!isReachable(x, y)) {
                    x = (int)(Math.random()*10);
                    y = (int)(Math.random()*10);
                }
                skeletons[i][0] = y;
                skeletons[i][1] = x;
                gameboard[y][x] = 2;
                x = (int)(Math.random()*10);
                y = (int)(Math.random()*10);
            }
        }

        public void moveSkeletons (Graphics graphics) {
            if (skeletonCycle == 0 ) {
                this.getSkeletons();
            }
            if (skeletonCycle%2==0) {
                for (int i = 0; i < 3; i++) {
                    boolean isMoving = false;
                    while (!isMoving) {
                        if (    (!isReachable(skeletons[i][1] + 1, skeletons[i][0])) && (!isReachable(skeletons[i][1] - 1, skeletons[i][0])) &&
                                (!isReachable(skeletons[i][1], skeletons[i][0] + 1)) && (!isReachable(skeletons[i][1], skeletons[i][0] - 1)) ) {
                            isMoving = true;
                        } else {
                            int x = (int)(Math.random()*4);
                            switch (x) {
                                case 0: {
                                    if (isReachable(skeletons[i][1] +1, skeletons[i][0])) {
                                        skeletons[i][1] +=1;
                                        gameboard[skeletons[i][0]][skeletons[i][1]-1] = 0;
                                        gameboard[skeletons[i][0]][skeletons[i][1]] = 4+i;
                                        isMoving = true;
                                    }
                                    break;
                                }
                                case 1: {
                                    if (isReachable(skeletons[i][1] -1, skeletons[i][0])) {
                                        skeletons[i][1] -=1;
                                        gameboard[skeletons[i][0]][skeletons[i][1]+1] = 0;
                                        gameboard[skeletons[i][0]][skeletons[i][1]] = 4+i;
                                        isMoving = true;
                                    }
                                    break;
                                }
                                case 2: {
                                    if (isReachable(skeletons[i][1], skeletons[i][0] +1)) {
                                        skeletons[i][0] +=1;
                                        gameboard[skeletons[i][0]-1][skeletons[i][1]] = 0;
                                        gameboard[skeletons[i][0]][skeletons[i][1]] = 4+i;
                                        isMoving = true;
                                    }
                                    break;
                                }
                                case 3: {
                                    if (isReachable(skeletons[i][1], skeletons[i][0] -1)) {
                                        skeletons[i][0] -= 1;
                                        gameboard[skeletons[i][0]+1][skeletons[i][1]] = 0;
                                        gameboard[skeletons[i][0]][skeletons[i][1]] = 4+i;
                                        isMoving = true;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }

            }
            drawSkeletons(graphics);
            skeletonCycle++;
        }

        public void drawSkeletons(Graphics graphics) {
            for (int i = 0; i < numberOfSkeletons; i++) {
                PositionedImage skeleton = new PositionedImage("img/skeleton.png", skeletons[i][1] * 72, skeletons[i][0] * 72);
                skeleton.draw(graphics);
            }
        }

        public void reduceSkeletons () {
            numberOfSkeletons--;
        }

        public void getBoss () {
            int x = (int)(Math.random()*10);
            int y = (int)(Math.random()*10);
            while (!isReachable(x, y)) {
                x = (int) (Math.random() * 10);
                y = (int) (Math.random() * 10);
            }
            boss[0] = y;
            boss[1] = x;
            gameboard[y][x] = 3;
        }

        public void moveBoss(Graphics graphics) {
            if (bossCycle == 0 ) {
                this.getBoss();
            }
            if (bossCycle%2==0) {
                boolean isMoving = false;
                while (!isMoving) {
                    if (    (!isReachable(boss[1] + 1, boss[0])) && (!isReachable(boss[1] - 1, boss[0])) &&
                            (!isReachable(boss[1], boss[0] + 1)) && (!isReachable(boss[1], boss[0] - 1)) ) {
                        isMoving = true;
                    } else {
                        int x = (int) (Math.random() * 4);
                        switch (x) {
                            case 0: {
                                if (isReachable(boss[1] + 1, boss[0])) {
                                    boss[1] += 1;
                                    gameboard[boss[0]][boss[1]-1] = 0;
                                    gameboard[boss[0]][boss[1]] = 3;
                                    isMoving = true;
                                }
                                break;
                            }
                            case 1: {
                                if (isReachable(boss[1] - 1, boss[0])) {
                                    boss[1] -= 1;
                                    gameboard[boss[0]][boss[1]+1] = 0;
                                    gameboard[boss[0]][boss[1]] = 3;
                                    isMoving = true;
                                }
                                break;
                            }
                            case 2: {
                                if (isReachable(boss[1], boss[0]+1)) {
                                    boss[0] += 1;
                                    gameboard[boss[0]-1][boss[1]] = 0;
                                    gameboard[boss[0]][boss[1]] = 3;
                                    isMoving = true;
                                }
                                break;
                            }
                            case 3: {
                                if (isReachable(boss[1], boss[0]-1)) {
                                    boss[0] -= 1;
                                    gameboard[boss[0]+1][boss[1]] = 0;
                                    gameboard[boss[0]][boss[1]] = 3;
                                    isMoving = true;
                                }
                                break;
                            }
                        }

                    }
                }
            }
            drawBoss(graphics);
            bossCycle++;
        }

        public void drawBoss(Graphics graphics) {
            PositionedImage bigBoss = new PositionedImage("img/boss.png", boss[1]*72, boss[0]*72);
            bigBoss.draw(graphics);
        }

    }

}
