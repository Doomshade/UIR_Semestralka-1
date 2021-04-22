package git.h0llew.uir.features;

import git.h0llew.uir.utils.BinaryImageConvertor;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Vezme jako vektor priznaku primo vsechny pixely obrazku.
 * Postup tvorby priznakoveho vektoru:
 * 1) preved obrazek na binarni
 * 2) vytvor priznakovy vektor o velikosti vyska x sirka obrazku
 * 3) pridej vsechny pixely obrazku do priznakoveho vektoru (0 cerny pixel, 1 bily pixel)
 *
 * @author Martin Jakubasek
 */
public class RawDataFeature implements IFeature {

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
     * Vytvori novou tridu vytvarejici priznak jako vektor vsech pixelu v obrazku, ktery je preveden na binarni
     */
    public RawDataFeature() {
        this(DEFAULT_THRESHOLD);
    }

    /**
     * Vytvori novou tridu vytvarejici priznak jako vektor vsech pixelu v obrazku, ktery je preveden na binarni
     *
     * @param threshold prah z intervalu <0;255>
     */
    public RawDataFeature(int threshold) {
        setThreshold(threshold);
    }

    //-- OVERRIDE METHODS

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
