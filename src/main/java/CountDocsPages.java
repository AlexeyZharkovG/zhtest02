import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;


public class CountDocsPages {
    private static int documentsCount = 0;
    private static int pagesCount = 0;

    public static void FileList(File file) throws IOException, InvalidFormatException {

        for(File item : Objects.requireNonNull(file.listFiles())){
            if(item.isDirectory()){
                File subfolder = new File(item.getAbsolutePath());
                FileList(subfolder);
            } else if (item.getName().endsWith("docx")) {
                documentsCount++;
                XWPFDocument doc = new XWPFDocument(OPCPackage.open(item.getAbsolutePath()));
                pagesCount += doc.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
            } else if (item.getName().endsWith("pdf")){
                documentsCount++;
                PDDocument pdfReader = PDDocument.load(item);
                pagesCount += pdfReader.getNumberOfPages();
            }
        }
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        Scanner scanner = new Scanner(System.in);
        File dir = new File(scanner.nextLine());
        FileList(dir);
        System.out.println("Documents: " + documentsCount + "\nPages: " + pagesCount);
    }
}