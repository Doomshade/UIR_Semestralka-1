package git.h0llew.uir.features;

import git.h0llew.uir.utils.BinaryImageConvertor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RowColHistogram implements IFeature {

    public static final int DEFAULT_THRESHOLD = 10;
    private int threshold;

    public RowColHistogram() {
        this(DEFAULT_THRESHOLD);
    }

    public RowColHistogram(int threshold) {
        setThreshold(threshold);
    }

    @Override
    public double[] createFeature(BufferedImage image) {
        BufferedImage binaryImage = convertToBinaryImage(image);
        return calculateFeature(binaryImage);
    }

    private BufferedImage convertToBinaryImage(BufferedImage image) {
        return BinaryImageConvertor.convertFromGrayscale(image, threshold);
    }

    private double[] calculateFeature(BufferedImage image) {
        double[] featureVector = new double[image.getWidth() + image.getHeight()];

        int[] rgb = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color pxColor = new Color(rgb[x + y * image.getWidth()]);

                if (!pxColor.equals(Color.WHITE)) {
                    featureVector[x]++;
                    featureVector[y + image.getWidth()]++;
                }
            }
        }

        return featureVector;
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
