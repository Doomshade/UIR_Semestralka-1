package git.h0llew.uir.classifiers;

/**
 * Sablona klasifikatoru cislic z priznakoveho vektoru.
 *
 * @author Martin Jakubasek
 */
public abstract class ADigitClassifier {

    //-- STATIC ATTRIBUTES

    private static final String NOT_TRAINED_ERROR = "Classifier is not trained!";
    private static final String FEATURE_VECTOR_IS_NULL = "Feature vector cannot be null";

    //-- PRIVATE ATTRIBUTES

    /**
     * testovaci sada priznaku obrazku cislic
     **/
    protected double[][][] trainingFeatureVectors;
    /**
     * priznak spravne natrenovane mnoziny
     **/
    protected boolean isTrained;

    //-- CONSTRUCTORS

    /**
     * Vytvori novy klasifikator cislic.
     * U klasifikatoru je potreba urcit mnozinu testovacich dat ve formatu [cislice][priznak obrazku][priznakovy vektor obrazku].
     * Cislice musi byt vzestupne a nasledovat za sebou.
     *
     * @param trainingFeatureVectors testovaci sada priznaku obrazku cislic
     */
    public ADigitClassifier(double[][][] trainingFeatureVectors) {
        this.trainingFeatureVectors = trainingFeatureVectors;
    }

    //-- ABSTRACT METHODS

    /**
     * Klasifikuje prvek podle natrenovaneho klasifikatoru.
     *
     * @param featureVector priznakovy vektor obrazku s cislici
     * @return rozpoznana cislice
     */
    protected abstract int classify(double[] featureVector);

    /**
     * Natrenuje klasifikator. Pri zdarenem natrenovani vrati true, jinak false
     *
     * @return true -> natrenovani se podarilo / false -> natrenovani se nepodarilo
     */
    protected abstract boolean train();

    //-- IMPLEMENTED METHODS

    /**
     * Natrenuje klasifikator a nastavi promenou 'isTrained' na true -> natrenovani se podarilo / false -> natrenovani se nepodarilo
     *
     * @return true -> natrenovani se podarilo / false -> natrenovani se nepodarilo
     */
    public boolean trainClassifier() {
        isTrained = train();
        return isTrained;
    }

    /**
     * Klasifikuje prvek podle natrenovaneho klasifikatoru.
     *
     * @param featureVector priznakovy vektor obrazku s cislici
     * @return rozpoznana cislice
     * @throws RuntimeException chyba nastane v okamziku, kdy metoda bude zavolana drive, nez je klasifikator natrenovan
     */
    public int classifyFeatureVector(double[] featureVector) throws RuntimeException {
        if (!isTrained)
            throw new RuntimeException(NOT_TRAINED_ERROR);
        if (featureVector == null)
            throw new NullPointerException(FEATURE_VECTOR_IS_NULL);

        return classify(featureVector);
    }

    /**
     * Otestuje funkcnost klasifikatoru oproti zadanym datum v parametru metody.
     * Testovaci mnozina je ve formatu [cislice][priznak obrazku][priznakovy vektor obrazku].
     * Cislice musi byt vzestupne a nasledovat za sebou.
     *
     * @param testFeatureVectors testovaci sada vsech priznaku obrazku vsech cislic
     * @return presnost klasifikatoru uvedena v procentech jako cislo z intervalu <0;1>
     * @throws RuntimeException chyba nastane v okamziku, kdy metoda bude zavolana drive, nez je klasifikator natrenovan
     */
    public double testClassifier(double[][][] testFeatureVectors) throws RuntimeException {
        if (!isTrained)
            throw new RuntimeException(NOT_TRAINED_ERROR);
        if (testFeatureVectors == null)
            throw new NullPointerException(FEATURE_VECTOR_IS_NULL);

        int all = 0;
        int hits = 0;
        for (int digit = 0; digit < testFeatureVectors.length; digit++) {
            double[][] digitFeatureVectors = testFeatureVectors[digit];
            for (int i = 0; i < digitFeatureVectors.length; i++) {
                double[] featureVector = digitFeatureVectors[i];
                all++;

                int result = classify(featureVector);
                if (result == digit)
                    hits++;
            }
        }

        return (double) hits / all;
    }

    //-- GET/SET

    /**
     * Vrati informaci o tom zda byl klasifikator natrenovan.
     *
     * @return true -> klasifikator byl natrenovan, false -> klasifikator nebyl natrenovan
     */
    public boolean getIsTrained() {
        return isTrained;
    }
}
