package git.h0llew.uir;

import git.h0llew.uir.symptoms.BrightnessMethod;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedImage input = ImageIO.read(new File("C:\\Users\\jakub\\IdeaProjects\\UIR_Semestralka\\test.png"));
        //BufferedImage res = BinaryImageConvertor.convertFromGrayscale(input, 10);

        BrightnessMethod brightnessMethod = new BrightnessMethod();

        System.out.println(Arrays.toString(brightnessMethod.createSymptom(input)));

        System.out.println("hi");
    }
}
