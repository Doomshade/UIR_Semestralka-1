package git.h0llew.uir;

import git.h0llew.uir.classifiers.MinimumDistanceClassifier;
import git.h0llew.uir.features.IFeature;
import git.h0llew.uir.features.RawDataFeature;
import git.h0llew.uir.utils.DigitsImageLoader;

import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args) throws Exception {

        //BufferedImage input = ImageIO.read(new File("C:\\Users\\jakub\\IdeaProjects\\UIR_Semestralka\\test.png"));

        //BufferedImage res = BinaryImageConvertor.convertFromGrayscale(input, 10);

        /*
        RowColHistogram rowColHistogram = new RowColHistogram();

        System.out.println(Arrays.toString(rowColHistogram.createSymptom(input)));
         */


        //BufferedImage res = SobelEffect.transformImage(input);
        //double[] feature = new EdgesAndGaps().createFeature(input);
        //System.out.println(Arrays.toString(feature));


        /*
        String test = "C:\\Users\\jakub\\Desktop\\mnist_png\\training";
        DigitsImageLoader loader = new DigitsImageLoader(test);


        long start = System.currentTimeMillis();
        BufferedImage[][] res = loader.loadDigits();
        long current = (System.currentTimeMillis() - start);
        System.out.println(current);

        EdgesAndGaps eag = new EdgesAndGaps();

        int counter = 0;
        for (BufferedImage[] digit : res) {
            System.out.println(counter + " ----- " + Arrays.toString(eag.createFeature(digit[0])));
            counter++;
        }
        System.out.println("-------------------------");


         */
        /*
        long start = System.currentTimeMillis();
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<BufferedImage> gamp = (ArrayList<BufferedImage>) DigitsImageLoader.loadImgs(new File(test), list);
        long current = (System.currentTimeMillis() - start);
        System.out.println(current);
         */

        //        System.out.println("hi");

        test();
    }

    private static void test() {
        String test = "C:\\Users\\jakub\\Desktop\\mnist_experimental\\mnist_png\\training";
        DigitsImageLoader loader = new DigitsImageLoader(test);
        long s = System.currentTimeMillis();
        BufferedImage[][] images = loader.loadDigits();
        long e = System.currentTimeMillis() - s;
        System.out.println("Nacitani training dat: " + e);

        IFeature featureMethod = new RawDataFeature();

        s = System.currentTimeMillis();
        double[][][] featureVectors = new double[images.length][][];
        for (int digit = 0; digit < featureVectors.length; digit++) {
            featureVectors[digit] = new double[images[digit].length][];
            double[][] imageFeatureVectors = featureVectors[digit];

            for (int i = 0; i < imageFeatureVectors.length; i++) {
                double[] featureVector = featureMethod.createFeature(images[digit][i]);
                imageFeatureVectors[i] = featureVector;
            }
        }
        e = System.currentTimeMillis() - s;
        System.out.println("Vytvoreni vektoru training dat: " + e);

        //KNearestNeighbours classifier = new KNearestNeighbours(featureVectors);
        MinimumDistanceClassifier classifier = new MinimumDistanceClassifier(featureVectors);
        classifier.trainClassifier();
        //int digit = classifier.classifyFeatureVector(featureVectors[0][0]);

        s = System.currentTimeMillis();
        String test1 = "C:\\Users\\jakub\\Desktop\\mnist_experimental\\mnist_png\\testing";
        DigitsImageLoader loader1 = new DigitsImageLoader(test1);
        BufferedImage[][] images1 = loader1.loadDigits();
        e = System.currentTimeMillis() - s;
        System.out.println("Nacitani test dat: " + e);

        s = System.currentTimeMillis();
        double[][][] featureVectors1 = new double[images1.length][][];
        for (int digit = 0; digit < featureVectors1.length; digit++) {
            featureVectors1[digit] = new double[images1[digit].length][];
            double[][] imageFeatureVectors = featureVectors1[digit];

            for (int i = 0; i < imageFeatureVectors.length; i++) {
                double[] featureVector = featureMethod.createFeature(images1[digit][i]);
                imageFeatureVectors[i] = featureVector;
            }
        }
        e = System.currentTimeMillis() - s;
        System.out.println("Vytvoreni vektoru test dat: " + e);

        double accuracy = classifier.testClassifier(featureVectors1);
        System.out.println(accuracy);
        /*
        for (int i = 1; i <= 20; i++) {
            classifier.overrideK(i);
            long start = System.currentTimeMillis();
            double accuracy = classifier.testClassifier(featureVectors1);
            long end = System.currentTimeMillis() - start;
            System.out.println(i + " [" + accuracy + "] " + end);
        }
         */
    }
}
