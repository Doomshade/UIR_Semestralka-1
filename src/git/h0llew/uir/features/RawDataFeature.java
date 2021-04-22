package git.h0llew.uir.features;

import git.h0llew.uir.utils.BinaryImageConvertor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RawDataFeature implements IFeature {

    @Override
    public double[] createFeature(BufferedImage image) {
        BufferedImage binaryImage = BinaryImageConvertor.convertFromGrayscale(image, 10);

        double[] res = new double[binaryImage.getWidth() * binaryImage.getHeight()];
        int counter = 0;

        for (int y = 0; y < binaryImage.getHeight(); y++) {
            for (int x = 0; x < binaryImage.getWidth(); x++) {

                res[counter] = new Color(binaryImage.getRGB(x, y)).getRed() / 255d;
                counter++;
            }
        }

        return res;
    }
}
