import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Solver {
    private static int baseSpeed = 1;

    public static int getBaseSpeed() {
        return baseSpeed;
    }
    public abstract void walkThrough();
    public abstract void reset();
}
