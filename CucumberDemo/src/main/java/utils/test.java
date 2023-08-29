package utils;

import static utils.Constants.config;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class test {
	
	static String newrptfoldername;
	
	public static void main(String a[]) throws IOException {
		createTestRunIDFolder();
		moveReportsAndScreenshots();
	}
	
	public static void createTestRunIDFolder() throws IOException {
		String userdirectory = System.getProperty("user.dir");
		String cucumberreportdirectory = userdirectory + "\\target\\cucumber-html-reports";
		String screenshotsdirectory = userdirectory + "\\screenshots";
		File file = new File(cucumberreportdirectory);
		String[] directories = file.list(new FilenameFilter() {
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});
		int lastrunid = 0;
		for(int i=0;i<=directories.length-1;i++) {
			if(directories[i].contains("Run")) {
				int runID = Integer.parseInt(directories[i].split("_")[1]);
				
				if(lastrunid < runID) {
					lastrunid = runID;
				}
			}
		}
			int newrunid = lastrunid + 1;
			newrptfoldername = "Run_" + newrunid + "_" + new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss-SS").format(new GregorianCalendar().getTime());
			file = new File(cucumberreportdirectory + "\\" + newrptfoldername);
			file.mkdir();
			file = new File(screenshotsdirectory + "\\" + newrptfoldername);
			file.mkdir();
		}
		
	 public static void moveReportsAndScreenshots() throws IOException{
		 String userdirectory = System.getProperty("user.dir");
		 String cucumberreportdirectory = userdirectory + "\\target\\cucumber-html-reports";
		 String screenshotsdirectory = userdirectory + "\\screenshots";
		 
		 File sourceFolder = new File(cucumberreportdirectory + "\\css");
		 File destinationFolder = new File(cucumberreportdirectory + "\\" + newrptfoldername + "\\css");
		 copyFolder(sourceFolder, destinationFolder);
		 
		 sourceFolder = new File(cucumberreportdirectory + "\\fonts");
		 destinationFolder = new File(cucumberreportdirectory + "\\" + newrptfoldername + "\\fonts");
		 copyFolder(sourceFolder, destinationFolder);
		 
		 sourceFolder = new File(cucumberreportdirectory + "\\images");
		 destinationFolder = new File(cucumberreportdirectory + "\\" + newrptfoldername + "\\images");
		 copyFolder(sourceFolder, destinationFolder);
		 
		 sourceFolder = new File(cucumberreportdirectory + "\\js");
		 destinationFolder = new File(cucumberreportdirectory + "\\" + newrptfoldername + "\\js");
		 copyFolder(sourceFolder, destinationFolder);
		//move html reports
		 sourceFolder = new File(cucumberreportdirectory);
		 destinationFolder = new File(cucumberreportdirectory + "\\" + newrptfoldername);
		 copyFolder(sourceFolder, destinationFolder);
		//movescreenshots
		 sourceFolder = new File(screenshotsdirectory);
		 destinationFolder = new File(screenshotsdirectory + "\\" + newrptfoldername);
		 copyFolder(sourceFolder, destinationFolder);
		 
	 }
	 
	 public static void copyFolder(File sourceFolder, File destinationFolder) throws IOException{
		   if(sourceFolder.isDirectory()) {
			   if(!destinationFolder.exists()) {
				   destinationFolder.mkdir();
				   System.out.println("Directory created ::" + destinationFolder);
			   }
			   String files[] = sourceFolder.list();
			   for(String file :files) {
				   File srcFile = new File(sourceFolder, file);
				   File destFile = new File(destinationFolder, file);
				   copyFolder(srcFile, destFile);
			   }
		   } else {
			   Files.copy(sourceFolder.toPath(), destinationFolder.toPath(),StandardCopyOption.REPLACE_EXISTING);
			   System.out.println("File copied ::" + destinationFolder);
		   }
	 }
	 
	 public static void copyFiles(File sourceFolder, File destinationFolder) throws IOException{
		 String files[] = sourceFolder.list();
		 for(String file : files) {
			 if(file.endsWith("html") || file.endsWith("png")) {
				 File srcFile = new File(sourceFolder, file);
				 File destFile = new File(destinationFolder, file);
				 Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				 Files.deleteIfExists(srcFile.toPath());
			 }
		 }
	 }

}
