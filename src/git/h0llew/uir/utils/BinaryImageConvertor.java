package git.h0llew.uir.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Trida pro prevod obrazku na binarni
 *
 * @author Martin Jakubasek
 */
public class BinaryImageConvertor {

    /**
     * Zmeni sedotonovy obrazek na binarni
     *
     * @param image     obrazek
     * @param threshold prah
     * @return binarni obrazek
     * @throws NullPointerException     obrazek nesmi byt null
     * @throws IllegalArgumentException prah musi byz z <0;255>
     */
    public static BufferedImage convertFromGrayscale(BufferedImage image, int threshold) throws NullPointerException, IllegalArgumentException {
        if (image == null)
            throw new NullPointerException();
        if (threshold < 0 || threshold > 255)
            throw new IllegalArgumentException("Threshold must be <0;255>");


        Color thresholdColor = new Color(threshold, threshold, threshold);

        BufferedImage binaryImg = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        int[] rgb = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
        for (int i = 0; i < rgb.length; i++) {
            Color pxColor = new Color(rgb[i]);
            rgb[i] = (pxColor.getRed() >= thresholdColor.getRed()) ? Color.BLACK.getRGB() : Color.WHITE.getRGB();
        }

        binaryImg.setRGB(0, 0, binaryImg.getWidth(), binaryImg.getHeight(), rgb, 0, binaryImg.getWidth());
        return binaryImg;
    }
}
