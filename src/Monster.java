import java.awt.*;

public class Monster extends Character{

    public Monster() {
        currentHP = 2*level*d6;
        maxHP = currentHP;
        defenderPoint = (level/2)*d6;
        strikePoint = level* d6;
    }

    @Override
    public void battle(Character character, Graphics graphics) {
        this.strike(character);
        if (character.currentHP <= 0) {
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
        character.strike(this);
        if (this.currentHP <= 0) {
            character.leveling();
        }
    }
}
