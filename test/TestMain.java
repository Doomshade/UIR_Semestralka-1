import git.h0llew.uir.features.GapsFeature;
import git.h0llew.uir.features.IFeature;
import git.h0llew.uir.features.RCHistogramFeature;
import git.h0llew.uir.features.RawDataFeature;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class TestMain {

    public static void main(String[] args) throws IOException {
        BufferedImage testImage = ImageIO.read(new File("C:\\Users\\jakub\\Desktop\\zzz\\test\\testImage.png"));

        testFeatures(testImage);
    }

    //-- FEATURES

    private static void testFeatures(BufferedImage image) {
        //testFeatureGeneration(image, new RawDataFeature());
        //testFeatureGeneration(image, new GapsFeature());
        testFeatureGeneration(image, new RCHistogramFeature());
    }

    private static void testFeatureGeneration(BufferedImage image, IFeature feature) {
        double[] featureVector = feature.createFeature(image);
        System.out.println(Arrays.toString(featureVector));
    }
}
