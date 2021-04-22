package git.h0llew.uir.features;

import java.awt.image.BufferedImage;

/**
 * Rozhrani pro tvorbu priznakoveho vektoru.
 *
 * @author Martin Jakubasek
 */
public interface IFeature {

    /**
     * Vytvory priznakovy vektor obrazku
     *
     * @param image obrazek
     * @return priznakovy vektor
     */
    double[] createFeature(BufferedImage image);
}
