import git.h0llew.uir.classifiers.ADigitClassifier;
import git.h0llew.uir.classifiers.MinimumDistanceClassifier;
import git.h0llew.uir.features.GapsFeature;
import git.h0llew.uir.features.IFeature;
import git.h0llew.uir.features.RCHistogramFeature;
import git.h0llew.uir.features.RawDataFeature;
import git.h0llew.uir.utils.DigitsImageLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class TestMain {

    public static void main(String[] args) throws IOException {
        /*
        BufferedImage testImage = ImageIO.read(new File("C:\\Users\\jakub\\Desktop\\zzz\\test\\testImage.png"));
        testFeatures(testImage);
         */

        /*
        String path = "C:\\Users\\jakub\\Desktop\\mnist_experimental\\mnist_small\\training";
        testImageLoader(path);
        System.out.println("*-*");
        path = "C:\\Users\\jakub\\Desktop\\mnist_experimental\\mnist_png\\training";
        testImageLoader(path);
         */

        String path = "C:\\Users\\jakub\\Desktop\\mnist_png\\training";
        //testImageLoader(path);
        DigitsImageLoader loader = new DigitsImageLoader(path);
        BufferedImage[][] images = loader.loadDigits();

        RCHistogramFeature featureMethod = new RCHistogramFeature();
        featureMethod.setThreshold(5);

        long start = System.currentTimeMillis();
        double[][][] featureVectors1 = new double[images.length][][];
        for (int digit = 0; digit < featureVectors1.length; digit++) {
            featureVectors1[digit] = new double[images[digit].length][];
            double[][] imageFeatureVectors = featureVectors1[digit];

            for (int i = 0; i < imageFeatureVectors.length; i++) {
                double[] featureVector = featureMethod.createFeature(images[digit][i]);
                imageFeatureVectors[i] = featureVector;
            }
        }

        testClassifier(new MinimumDistanceClassifier(featureVectors1));
        long e = System.currentTimeMillis() - start;
        System.out.println(e);
    }

    //-- CLASSIFIERS

    private static void testClassifier(ADigitClassifier classifier) {
        classifier.trainClassifier();
    }

    //-- FEATURES

    private static void testFeatures(BufferedImage image) {
        testFeatureGeneration(image, new RawDataFeature());
        testFeatureGeneration(image, new GapsFeature());
        testFeatureGeneration(image, new RCHistogramFeature());
    }

    private static void testFeatureGeneration(BufferedImage image, IFeature feature) {
        double[] featureVector = feature.createFeature(image);
        System.out.println(Arrays.toString(featureVector));
    }

    //-- IMAGE LOADER

    private static void testImageLoader(String path) {
        DigitsImageLoader a = new DigitsImageLoader(path);
        BufferedImage[][] images = a.loadDigits();

        for (BufferedImage[] digit : images) {
            System.out.println(digit.length);
        }
    }
}
