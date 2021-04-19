package git.h0llew.uir;

import git.h0llew.uir.features.EdgesAndGaps;
import git.h0llew.uir.features.RowColHistogram;
import git.h0llew.uir.utils.DigitsImageLoader;
import git.h0llew.uir.utils.SobelEffect;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {

        //BufferedImage input = ImageIO.read(new File("C:\\Users\\jakub\\IdeaProjects\\UIR_Semestralka\\test.png"));
        //BufferedImage res = BinaryImageConvertor.convertFromGrayscale(input, 10);

        /*
        RowColHistogram rowColHistogram = new RowColHistogram();

        System.out.println(Arrays.toString(rowColHistogram.createSymptom(input)));
         */


        //BufferedImage res = SobelEffect.transformImage(input);
        //double[] feature = new EdgesAndGaps().createFeature(input);
        //System.out.println(Arrays.toString(feature));

        /*
        String test = "C:\\Users\\jakub\\Desktop\\mnist_png\\training";
        DigitsImageLoader loader = new DigitsImageLoader(test);

        BufferedImage[][] res = loader.loadDigits();

        System.out.println("hi");
         */
    }
}
