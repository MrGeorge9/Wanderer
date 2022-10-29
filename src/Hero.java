import java.awt.*;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class Hero extends Character{

    int level = 1;

    public Hero() {
        currentHP = 20 + 3*d6;
        maxHP = currentHP;
        defenderPoint = 2*d6;
        strikePoint = 5 + d6;
    }

    @Override
    public void battle(Character character, Graphics graphics) {
        this.strike(character);
        if (character.currentHP <= 0) {
            int chance = (int)(Math.random()*3);
            if (chance==1){
                this.leveling();
            }
        }
        character.strike(this);
        if (this.currentHP <= 0) {
            PositionedImage youLost = new PositionedImage("img/gameover.png", 100, 50);
            youLost.draw(graphics);
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            System.exit(0);
                        }
                    },
                    2000
            );
            while (true);
        }
    }

    @Override
    public void leveling () {
        maxHP += d6;
        defenderPoint += d6;
        strikePoint += d6;
        this.level++;
    }
}
