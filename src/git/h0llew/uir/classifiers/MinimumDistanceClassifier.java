package git.h0llew.uir.classifiers;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MinimumDistanceClassifier extends ADigitClassifier {

    private double[][] etalons;

    /**
     * Vytvori novy klasifikator cislic.
     * U klasifikatoru je potreba urcit mnozinu testovacich dat ve formatu [cislice][priznak obrazku][priznakovy vektor obrazku].
     * Cislice musi byt vzestupne a nasledovat za sebou.
     *
     * @param trainingFeatureVectors testovaci sada priznaku obrazku cislic
     */
    public MinimumDistanceClassifier(double[][][] trainingFeatureVectors) {
        super(trainingFeatureVectors);
    }

    @Override
    protected int classify(double[] featureVector) {
        double distance = Double.POSITIVE_INFINITY;
        int digit = -1;

        for (int i = 0; i < etalons.length; i++) {
            double currentDistance = calculateDistance(etalons[i], featureVector);
            if (currentDistance > distance)
                continue;

            distance = currentDistance;
            digit = i;
        }

        return digit;
    }

    @Override
    protected boolean train() {
        etalons = new double[trainingFeatureVectors.length][];

        for (int digit = 0; digit < trainingFeatureVectors.length; digit++) {
            double[][] digitFeatureVectors = trainingFeatureVectors[digit];
            double[] etalon = new double[0];

            for (int i = 0; i < digitFeatureVectors.length; i++) {
                double[] iFeatureVector = digitFeatureVectors[i];

                etalon = addToEtalon(iFeatureVector, etalon);
            }

            for (int i = 0; i < etalon.length; i++) {
                etalon[i] = (etalon[i] / digitFeatureVectors.length);
            }
            etalons[digit] = etalon;
        }

        return true;
    }

    //-- PRIVATE METHODS

    private double[] addToEtalon(double[] featureVector, double[] etalon) {
        double[] res = etalon;

        if (featureVector.length > etalon.length)
            res = Arrays.copyOf(etalon, featureVector.length);

        for (int i = 0; i < featureVector.length; i++) {
            res[i] += featureVector[i];
        }

        return res;
    }

    /**
     * Vypocita euklidovskou vzdalenost mezi dvema vektory
     *
     * @param a vektor a
     * @param b vektor b
     * @return vzdalenost [a;b]
     */
    private double calculateDistance(double[] a, double[] b) {
        double[] larger = a;
        double[] smaller = b;

        if (b.length > a.length) {
            larger = b;
            smaller = a;
        }

        double distance = 0;
        for (int i = 0; i < smaller.length; i++) {
            double e = smaller[i] - larger[i];
            distance += (e * e);
        }

        return Math.sqrt(distance);
    }
}
