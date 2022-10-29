import java.awt.*;
import java.util.Scanner;

public class Character {

    int maxHP;
    int currentHP;
    int defenderPoint;
    int strikePoint;
    static int level = 1;
    int d6 = ((int)(Math.random()*6))+1;

    public Character() {
    }

    public void battle(Character character, Graphics graphics) {
    }

    public void strike (Character character) {
        int strikeValue;
        strikeValue = strikePoint + 2*d6;
        if (strikeValue>character.defenderPoint) {
            character.currentHP-= (strikeValue - character.defenderPoint);
        }
    }

    public void leveling () {
    }

    public void charStats (Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(490, 680, 230, 20);
        graphics.setColor(Color.BLACK);
        graphics.drawString("Monster (level " + this.level + ") HP: " + this.currentHP + "/" + this.maxHP + " | DP: " + this.defenderPoint +
                " | SP: " + this.strikePoint , 495, 695);
    }
}
