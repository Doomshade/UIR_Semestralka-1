package git.h0llew.uir.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.*;

/**
 * Trida slouzi pro nacitani souboru s obrazky cislic
 *
 * @author Martin Jakubasek
 */
public class DigitsImageLoader {

    //-- PRIVATE ATTRIBUTES

    /**
     * Cesta k souboru
     */
    private final String rootPath;

    //-- CONSTRUCTORS

    /**
     * Vytvori novou tridu, ktera slouzi k nacitani obrazku s cislicemi
     *
     * @param rootPath cesta ke korenovemu adresari adresare obsahujici adresare s obrazky cislic
     */
    public DigitsImageLoader(String rootPath) {
        this.rootPath = rootPath;
    }

    //-- PRIVATE STATIC METHODS

    /**
     * mozne koncovky souboru
     */
    private static final String[] EXTENSIONS = new String[]{
            "png", "jpg"
    };

    /**
     * filtr souboru podle koncovek
     */
    private static final FilenameFilter IMAGE_FILTER = (dir, name) -> {
        for (final String ext : EXTENSIONS) {
            if (name.endsWith("." + ext)) {
                return true;
            }
        }
        return false;
    };

    //-- PRIVATE STATIC ATTRIBUTES

    private static final int DIGITS = 10;

    /**
     * Nacte vsechny obrazky cislic
     *
     * @return obrazky cislic
     */
    public BufferedImage[][] loadDigits() {
        BufferedImage[][] res = new BufferedImage[DIGITS][];

        try {
            for (int i = 0; i < DIGITS; i++) {
                res[i] = loadDigit(i);
                //loadDigit(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return res;
    }

    /**
     * Nacte obrazky cislice
     *
     * @param digit cislice
     * @return obrazky cislice
     * @throws IOException          soubor neni adresar nebo jina chyba
     * @throws NullPointerException prazdna slozka obrazku
     */
    private BufferedImage[] loadDigit(int digit) throws IOException, NullPointerException {
        File digitDirectoryPath = new File(rootPath + "\\" + digit);
        if (!digitDirectoryPath.isDirectory())
            throw new NotDirectoryException(digitDirectoryPath.getAbsolutePath());

        ArrayList<BufferedImage> imagesList = new ArrayList<>();

        File[] images = digitDirectoryPath.listFiles();
        if (images == null)
            throw new NullPointerException();
        for (File file : images) {
            imagesList.add(ImageIO.read(file));
        }

        return imagesList.toArray(new BufferedImage[0]);
    }
}
