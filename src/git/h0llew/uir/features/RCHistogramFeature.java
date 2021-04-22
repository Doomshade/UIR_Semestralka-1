package git.h0llew.uir.features;

import git.h0llew.uir.utils.BinaryImageConvertor;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Vezme jako vektor priznaku pocet radku a sloupecku s nebilymi pixely.
 * Postup tvorby priznakoveho vektoru:
 * 1) preved obrazek na binarni
 * 2) vytvor priznakovy vektor o velikosti vyska + sirka obrazku
 * 3) pridej vsechny sumy nebilych pixelu v radcich a sloupcich obrazku do priznakoveho vektoru
 *
 * @author Martin Jakubasek
 */
public class RCHistogramFeature implements IFeature {

    //-- PUBLIC FIELDS

    /**
     * vychozi prah
     */
    public static final int DEFAULT_THRESHOLD = 10;

    //-- PRIVATE ATTRIBUTES

    /**
     * prah
     */
    private int threshold;

    //-- CONSTRUCTORS

    /**
     * Vytvori novou tridu vytvarejici priznak, jako vektor
     * obsahujici soucet vsech cernych pixelu v radcich a sloupecka v obrazku, ktery je preveden na binarni
     */
    public RCHistogramFeature() {
        this(DEFAULT_THRESHOLD);
    }

    /**
     * Vytvori novou tridu vytvarejici priznak, jako vektor
     * obsahujici soucet vsech cernych pixelu v radcich a sloupecka v obrazku, ktery je preveden na binarni
     *
     * @param threshold prah z intervalu <0;255>
     */
    public RCHistogramFeature(int threshold) {
        setThreshold(threshold);
    }

    //-- OVERRIDE METHODS

    @Override
    public double[] createFeature(BufferedImage image) {
        BufferedImage binaryImage = convertToBinaryImage(image);
        return calculateFeature(binaryImage);
    }

    //-- PRIVATE METHODS

    /**
     * Prevede obrazek na binarni
     *
     * @param image obrazek
     * @return binarni obrazek
     */
    private BufferedImage convertToBinaryImage(BufferedImage image) {
        return BinaryImageConvertor.convertFromGrayscale(image, threshold);
    }

    /**
     * Spocita pocet cernych pixelu v radcich a sloupeckach obrazku
     *
     * @param image obrazek cislice
     * @return priznakovy vektor obrazku cislice
     */
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

    //-- GET/SET

    /**
     * Nastavi prah, ktery je pouzit pri zmene sedocerneho obrazku na binarni (cernobily)
     *
     * @param threshold prah z intervalu <0;255>
     * @throws IllegalArgumentException prah musi by z intervalu <0;255>
     */
    public void setThreshold(int threshold) throws IllegalArgumentException {
        if (threshold < 0 || threshold > 255)
            throw new IllegalArgumentException("Threshold must be <0;255>");

        this.threshold = threshold;
    }

    /**
     * Vrati prah, ktery je pouzit pri zmene sedocerneho obrazku na binarni (cernobily)
     *
     * @return prah
     */
    public int getThreshold() {
        return threshold;
    }
}
