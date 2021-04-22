package git.h0llew.uir.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * detektor hran v obrazku
 *
 * @author Martin Jakubasek
 */
public class SobelEffect {

    static int[][] gx = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
    static int[][] gy = {{-1, 2, 1}, {0, 0, 0}, {1, 2, 1}};

    static int[][] dirs = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

    /**
     * Detekuje v obrazku hrany
     *
     * @param image obrazek
     * @return obrazek s detekovanyma hranama
     */
    public static BufferedImage transformImage(BufferedImage image) {
        BufferedImage res = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        int width = res.getWidth();
        int height = res.getHeight();

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {

                int sumGx = 0;
                int sumGy = 0;

                for (int[] curDir : dirs) {
                    int value = new Color(image.getRGB(x + curDir[0], y + curDir[1])).getBlue();

                    sumGx += (value * gx[curDir[1] + 1][curDir[0] + 1]);
                    sumGy += (value * gy[curDir[1] + 1][curDir[0] + 1]);
                }

                float result = (float) ((Math.sqrt((sumGx * sumGx) + (sumGy + sumGy)) % 255) / 255);
                Color resColor = new Color(result, result, result);
                res.setRGB(x, y, resColor.getRGB());
            }
        }

        return res;
    }
}
