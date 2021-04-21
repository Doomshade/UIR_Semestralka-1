package git.h0llew.uir.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
                //loadDigit(i);
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

        ArrayList<BufferedImage> imagesList = new ArrayList<>();

        File[] images = digitDirectoryPath.listFiles();
        if (images == null)
            throw new NullPointerException();
        for (File file : images) {
            imagesList.add(ImageIO.read(file));
        }

        return imagesList.toArray(new BufferedImage[0]);
    }

    public static List<BufferedImage> loadImgs(File dir, List<Integer> dirIndexes) throws IOException {
        return loadImgs(dir, new ArrayList<>(), new AtomicInteger(0), dirIndexes);
    }

    private static List<BufferedImage> loadImgs(File dir, List<BufferedImage> currImages, AtomicInteger index, List<Integer> dirIndexes) throws IOException {
        if (!dir.isDirectory()) {
            BufferedImage img = ImageIO.read(dir);
            currImages.add(img);
            index.incrementAndGet();
        } else {
            dirIndexes.add(index.getAcquire());
            File[] files = Objects.requireNonNull(dir.listFiles());
            for (File potentialDir : files) {
                //if (potentialDir.isDirectory()) {
                loadImgs(potentialDir, currImages, index, dirIndexes);
                //}
            }
        }
        return currImages;
    }
}
