package decorator;

import java.io.File;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class SmartDocument implements Document {

    @Override
    public String parse(String filePath) {
        ITesseract tesseract = new Tesseract();
            tesseract.setDatapath("src/main/resources");
            tesseract.setLanguage("eng");

        try {
            return tesseract.doOCR(new File(filePath));
        } catch (TesseractException e) {
            throw new RuntimeException("Error while performing OCR", e);
        }
    };
}