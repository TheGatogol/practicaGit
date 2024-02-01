package automation;

mport java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import config.ConfigPaths;

/**
* Author: Jose Rodrigo Escutia Rios
* Framework: Keywords powered by Selenium (KS)
* Version: 1.0.0
*/

public class printEvidence{

static XWPFDocument doc;
static XWPFParagraph title;
static XWPFRun run;
static ConfigPaths paths = new ConfigPaths();
static String headerPath = "";
static boolean firstStep = true;
static String getOS = "";

public static void createNewDocument(String oS){
   
doc = new XWPFDocument();
title = doc.createParagraph();
run = title.createRun();
getOS = oS;

if(oS.equals("Windows")){
headerPath = paths.headerPath();
}

if(oS.equals("MacOS")){
headerPath = paths.headerPathMAC();
}

if(oS.equals("Linux")){
headerPath = paths.headerPathUnix();
}

XWPFHeaderFooterPolicy headerFooterPolicy = doc.getHeaderFooterPolicy();
 
if (headerFooterPolicy == null) headerFooterPolicy = doc.createHeaderFooterPolicy();

 XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);

 XWPFParagraph paragraph = header.createParagraph();
 paragraph.setAlignment(ParagraphAlignment.CENTER);

 XWPFRun run = paragraph.createRun();  
 String imgFile = headerPath;
 FileInputStream headerImg = null;

try{
headerImg = new FileInputStream(imgFile);
run.addPicture(headerImg, XWPFDocument.PICTURE_TYPE_PNG, "Header.png", Units.toEMU(400), Units.toEMU(80));
}

catch(Exception e){
String handleError = ""+e;
if(handleError.contains("java.io.FileNotFoundException:")){
System.out.println("[Error] The system couldn't find the header at: "+imgFile);
}
}

 XWPFFooter footer = headerFooterPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT);

 paragraph = footer.createParagraph();
 paragraph.setAlignment(ParagraphAlignment.CENTER);

 run = paragraph.createRun();
     run.setColor("000000");
     run.setText("Test Automatizado                                                                                            Página ");
     
     paragraph.getCTP().addNewFldSimple().setInstr("PAGE \\* ARABIC MERGEFORMAT");
     run = paragraph.createRun();  
     run.setText(" de ");
     paragraph.getCTP().addNewFldSimple().setInstr("NUMPAGES \\* ARABIC MERGEFORMAT");
       
 CTSectPr sectPr = doc.getDocument().getBody().getSectPr();
 if (sectPr == null) sectPr = doc.getDocument().getBody().addNewSectPr();
 CTPageMar pageMar = sectPr.getPgMar();
 if (pageMar == null) pageMar = sectPr.addNewPgMar();
 pageMar.setLeft(BigInteger.valueOf(720)); //720 TWentieths of an Inch Point (Twips) = 720/20 = 36 pt = 36/72 = 0.5"
 pageMar.setRight(BigInteger.valueOf(720));
 pageMar.setTop(BigInteger.valueOf(1440)); //1440 Twips = 1440/20 = 72 pt = 72/72 = 1"
 pageMar.setBottom(BigInteger.valueOf(1440));
 pageMar.setHeader(BigInteger.valueOf(908)); //45.4 pt * 20 = 908 = 45.4 pt header from top
 pageMar.setFooter(BigInteger.valueOf(568)); //28.4 pt * 20 = 568 = 28.4 pt footer from bottom
}

public static void addNewDescription(String text) throws InvalidFormatException, IOException {

if(firstStep) {
firstStep = false;
}
else {
run.addBreak();
run.addBreak();
}
run.setColor("000000");
run.setText(text);
run.setBold(true);
title.setAlignment(ParagraphAlignment.CENTER);
run.addBreak();
run.addBreak();
}

public static void getScreenshoot() throws InvalidFormatException, IOException, HeadlessException, AWTException {

String imgFile = "evidencia.jpg";

BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
ImageIO.write(image, "jpg", new File("evidencia.jpg"));
FileInputStream is = new FileInputStream(imgFile);

run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, imgFile, Units.toEMU(500), Units.toEMU(350)); //Altura x Ancho
is.close();

for(int i=0; i<=11; i++){
run.addBreak();
}

}

public static void takeSnapShot(WebDriver webdriver) throws Exception{

        String imgFile = "Screenshot.jpg";
File img = ((TakesScreenshot)webdriver).getScreenshotAs(OutputType.FILE);
FileUtils.copyFile(img, new File(imgFile));  
        FileInputStream is = new FileInputStream(imgFile);

run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, imgFile, Units.toEMU(435), Units.toEMU(325)); //Altura x Ancho

is.close();

for(int i=0; i<=14; i++){
run.addBreak();
}
}

public static String closeFile(String docName) throws Exception{
   
ConfigPaths paths = new ConfigPaths();
DateFormat dateFormat = new SimpleDateFormat("[dd-MM-yyyy][HH-mm-ss]");
Date date = new Date();
String evidenceName = "";

switch(getOS){

case "Windows":
evidenceName = paths.evidencePath()+dateFormat.format(date)+" "+docName+".docx";
break;

case "MacOS":
evidenceName = paths.evidencePathMAC()+dateFormat.format(date)+" "+docName+".docx";
break;

case "Linux":
evidenceName = paths.evidencePathUnix()+dateFormat.format(date)+" "+docName+".docx";
break;
}

FileOutputStream docx = new FileOutputStream(evidenceName);
doc.write(docx);
docx.close();
   

File imgEvidencia = new File("Screenshot.jpg");
if(imgEvidencia.exists()){
imgEvidencia.delete();
}
   
   firstStep = true;
   
   return dateFormat.format(date)+" "+docName+".docx";
}

}
