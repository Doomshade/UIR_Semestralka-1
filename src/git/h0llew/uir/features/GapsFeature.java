package git.h0llew.uir.features;

import git.h0llew.uir.utils.BinaryImageConvertor;
import git.h0llew.uir.utils.SobelEffect;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Vezme jako vektor priznaku pocet mezer v radcich obrazku.
 * Postup tvorby priznakoveho vektoru:
 * 1) projed obrazek sobelovym filtrem a preved ho na binarni
 * 2) vytvor priznakovy vektor o velikosti sirka obrazku
 * 3) pridej vsechny sumy mezer pixelu v radcich obrazku do priznakoveho vektoru
 *
 * @author Martin Jakubasek
 */
public class GapsFeature implements IFeature {

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
     * Vytvori novou tridu vytvarejici priznak, jako vektor obsahujici soucet vsech mezer v radcich obrazku,
     * ktery je preveden na binarni a nasledne premenen sobelovy filtrem pro detekci hran.
     */
    public GapsFeature() {
        this(DEFAULT_THRESHOLD);
    }

    /**
     * Vytvori novou tridu vytvarejici priznak, jako vektor obsahujici soucet vsech mezer v radcich obrazku,
     * ktery je preveden na binarni a nasledne premenen sobelovy filtrem pro detekci hran.
     *
     * @param threshold prah z intervalu <0;255>
     */
    public GapsFeature(int threshold) {
        setThreshold(threshold);
    }

    //-- OVERRIDE METHODS

    @Override
    public double[] createFeature(BufferedImage image) {
        BufferedImage edgeImage = SobelEffect.transformImage(image);
        edgeImage = BinaryImageConvertor.convertFromGrayscale(edgeImage, threshold);
        return calculateFeature(edgeImage);
    }

    //-- PRIVATE METHODS

    /**
     * Vypocita pocet mezer v radcich obrazku, ktery je modifikovan sobelovym filtrem a nasledne preveden na binarni
     *
     * @param image obrazek
     * @return priznakovy vektor
     */
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
