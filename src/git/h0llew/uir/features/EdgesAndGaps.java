package git.h0llew.uir.features;

import git.h0llew.uir.utils.SobelEffect;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EdgesAndGaps implements IFeature {

    @Override
    public double[] createFeature(BufferedImage image) {
        BufferedImage edgeImage = SobelEffect.transformImage(image);
        return calculateFeature(edgeImage);
    }

    private double[] calculateFeature(BufferedImage image) {
        double[] featureVector = new double[image.getHeight()];

        int[] rgb = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
        for (int y = 0; y < image.getHeight(); y++) {
            int lastX = -1;
            int counter = 0;

            for (int x = 0; x < image.getWidth(); x++) {
                Color pxColor = new Color(rgb[x + y * image.getWidth()]);

                if (!pxColor.equals(Color.BLACK)) {
                    if (lastX == -1) {
                        lastX = x;
                        continue;
                    }

                    counter += (x - lastX);
                    lastX = x;
                }
            }

            featureVector[y] = counter;
        }

        return featureVector;
    }
}
