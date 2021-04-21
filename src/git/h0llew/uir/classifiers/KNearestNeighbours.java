package git.h0llew.uir.classifiers;

import java.util.PriorityQueue;

/**
 * Implemantace algoritmu K-nejblizsich sousedu
 *
 * @author Martin Jakubasek
 */
public class KNearestNeighbours extends ADigitClassifier {

    //-- PRIVATE ATTRIBUTES

    /* pocet sousedu */
    private int defaultK = -1;
    private int k = defaultK;

    //-- CONSTRUCTORS

    /**
     * Vytvori novy klasifikator cislic.
     * U klasifikatoru je potreba urcit mnozinu testovacich dat ve formatu [cislice][priznak obrazku][priznakovy vektor obrazku].
     * Cislice musi byt vzestupne a nasledovat za sebou.
     *
     * @param trainingFeatureVectors testovaci sada priznaku obrazku cislic
     */
    public KNearestNeighbours(double[][][] trainingFeatureVectors) {
        super(trainingFeatureVectors);
        setDefaultK();
    }

    //-- OVERRIDE METHODS

    @Override
    protected int classify(double[] featureVector) {
        PriorityQueue<FeatureVectorWrapper> neighbours = new PriorityQueue<>();

        for (int digit = 0; digit < trainingFeatureVectors.length; digit++) {
            double[][] digitFeatureVectors = trainingFeatureVectors[digit];
            for (int i = 0; i < digitFeatureVectors.length; i++) {
                double[] iFeatureVector = digitFeatureVectors[i];

                double distance = calculateDistance(iFeatureVector, featureVector);
                neighbours.add(new FeatureVectorWrapper(iFeatureVector, digit, distance));
            }
        }

        return guessDigit(neighbours);
    }

    @Override
    protected boolean train() {
        /* nic se netrenuje */
        return true;
    }

    //-- PRIVATE STATIC CLASS

    /**
     * Wrapper priznakoveho vekroru.
     * Slouzi pro zjisteni K nejblizsich sousedu
     */
    private static class FeatureVectorWrapper implements Comparable<FeatureVectorWrapper> {

        public final double[] featureVector;
        public final int digit;
        public final double distance;

        public FeatureVectorWrapper(double[] featureVector, int digit, double distance) {
            this.featureVector = featureVector;
            this.digit = digit;
            this.distance = distance;
        }

        @Override
        public int compareTo(FeatureVectorWrapper o) {
            return Double.compare(distance, o.distance);
        }
    }

    //-- PRIVATE METHODS

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

    /**
     * Podle sousedu zvoli predpokladanou cislici zadaneho priznakoveho vektoru
     *
     * @param neighbours sousedi
     * @return predpokladana cislice
     */
    private int guessDigit(PriorityQueue<FeatureVectorWrapper> neighbours) {
        double[] weight = new double[trainingFeatureVectors.length]; // pocet cislic

        for (int i = 0; i < k; i++) {
            FeatureVectorWrapper neighbour = neighbours.poll();
            if (neighbour == null)
                break;
            weight[neighbour.digit] = getWeight(neighbour, i);
        }

        int largestVote = 0;
        for (int i = 0; i < weight.length; i++) {
            largestVote = (weight[largestVote] < weight[i]) ? i : largestVote;
        }

        return largestVote;
    }

    /**
     * Vrati vahu priznaku v poradi sousedu
     *
     * @param wrapper priznak
     * @param rank    poradi
     * @return vaha priznaku
     */
    private double getWeight(FeatureVectorWrapper wrapper, int rank) {
        return 1; // pro pripad kdyby byla jina vaha
    }

    /**
     * Nastavi vychozi 'k'
     */
    private void setDefaultK() {
        this.defaultK = 1;
        this.k = defaultK;
    }

    // -- GET/SET

    /**
     * Zmeni pocet sousedu, ktery se berou v potaz pri klasifikaci cislice.
     *
     * @param k novy pocet sousedu
     */
    public void overrideK(int k) {
        this.k = k;
    }

    /**
     * Zmeny pocet sousedu na vychozi pocet sousedu.
     */
    public void resetK() {
        this.k = defaultK;
    }

    /**
     * Vrati pocet soused, ktery se berou v potaz pri klasifikaci cislice.
     *
     * @return pocet sousedu
     */
    public int getK() {
        return k;
    }

    /**
     * Vrati predem urceny pocet soused, ktery se berou v potaz pri klasifikaci cislice.
     * Pokud nebyl klasifikator natrenovan vrati -1
     *
     * @return pocet sousedu nebo -1
     */
    public int getDefaultK() {
        return defaultK;
    }
}
