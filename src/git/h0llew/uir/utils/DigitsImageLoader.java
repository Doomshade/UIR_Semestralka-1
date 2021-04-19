package git.h0llew.uir.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class DigitsImageLoader {

    private final String rootPath;

    public DigitsImageLoader(String rootPath) {
        this.rootPath = rootPath;
    }

    //--

    private static final String[] EXTENSIONS = new String[]{
            "png", "jpg"
    };

    private static final FilenameFilter IMAGE_FILTER = (dir, name) -> {
        for (final String ext : EXTENSIONS) {
            if (name.endsWith("." + ext)) {
                return true;
            }
        }
        return false;
    };

    //--

    private static final int DIGITS = 10;

    public BufferedImage[][] loadDigits() {
        BufferedImage[][] res = new BufferedImage[DIGITS][];

        try {
            for (int i = 0; i < DIGITS; i++) {
                res[i] = loadDigit(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return res;
    }

    private BufferedImage[] loadDigit(int digit) throws IOException, NullPointerException {
        File digitDirectoryPath = new File(rootPath + "\\" + digit);
        if (!digitDirectoryPath.isDirectory())
            throw new NotDirectoryException(digitDirectoryPath.getAbsolutePath());

        LinkedList<BufferedImage> imagesList = new LinkedList<>();

        File[] images = digitDirectoryPath.listFiles(IMAGE_FILTER);
        if (images == null)
            throw new NullPointerException();
        for (File file : images) {
            imagesList.add(ImageIO.read(file));
        }

        return imagesList.toArray(new BufferedImage[0]);
    }
}
