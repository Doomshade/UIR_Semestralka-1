package git.h0llew.uir.symptoms;

import git.h0llew.uir.utils.BinaryImageConvertor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BrightnessMethod implements ISymptom {

    public static final int DEFAULT_THRESHOLD = 10;
    private int threshold;

    public BrightnessMethod() {
        this(DEFAULT_THRESHOLD);
    }

    public BrightnessMethod(int threshold) {
        setThreshold(threshold);
    }

    @Override
    public double[] createSymptom(BufferedImage image) {
        BufferedImage binaryImage = convertToBinaryImage(image);
        return calculateSymptom(binaryImage);
    }

    private BufferedImage convertToBinaryImage(BufferedImage image) {
        return BinaryImageConvertor.convertFromGrayscale(image, threshold);
    }

    private double[] calculateSymptom(BufferedImage image) {
        double[] symptomVector = new double[image.getWidth() + image.getHeight()];

        int[] rgb = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color pxColor = new Color(rgb[x + y * image.getWidth()]);

                if (!pxColor.equals(Color.WHITE)) {
                    symptomVector[x]++;
                    symptomVector[y + image.getWidth()]++;
                }
            }
        }

        return symptomVector;
    }

    public void setThreshold(int threshold) throws IllegalArgumentException {
        if (threshold < 0 || threshold > 255)
            throw new IllegalArgumentException("Threshold must be <0;255>");

        this.threshold = threshold;
    }

    public int getThreshold() {
        return threshold;
    }
}
