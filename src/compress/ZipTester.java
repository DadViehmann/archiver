package compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FilenameUtils;

public class ZipTester {
	
	public static void zipFile(String pathString, String outputPath) {
		
		try {		
			File path = new File(pathString);
			
			String zipName = FilenameUtils.getBaseName(pathString);
			
			System.out.println(outputPath);
			
			if (path.isDirectory()) {
				writeStringToFile(pathString);
				FileOutputStream fos = new FileOutputStream(outputPath + "\\" + zipName + ".zip");
				ZipOutputStream zipOS = new ZipOutputStream(fos);
				for(final File child : path.listFiles()) {
					String childName = child.toString();
					writeToZipFile(childName, zipOS);
					System.out.println(path);
				}
				zipOS.close();
				fos.close();
			}
			else if (path.isFile()) {
				writeStringToFile(FilenameUtils.getFullPath(pathString));
				String pathFile = FilenameUtils.getFullPath(pathString) + "\\path.txt";
				FileOutputStream fos = new FileOutputStream(outputPath + "\\" + zipName + ".zip");
				ZipOutputStream zipOS = new ZipOutputStream(fos);
				writeToZipFile(pathString, zipOS);
				writeToZipFile(pathFile, zipOS);
				zipOS.close();
				fos.close();
				Files.deleteIfExists(Paths.get(pathFile));
			}
			Files.deleteIfExists(Paths.get(pathString + "\\path.txt"));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void writeToZipFile(String path, ZipOutputStream zipOS)
		throws FileNotFoundException, IOException {
		
		System.out.println("Writing file : " + FilenameUtils.getBaseName(path) + " to zip file.");
		
		File aFile = new File(path);
		String pathShort = FilenameUtils.getName(path);
		FileInputStream fis = new FileInputStream(path);
		ZipEntry zipEntry = new ZipEntry(pathShort);
		zipOS.putNextEntry(zipEntry);
		
		byte[] bytes = new byte[1024];
		int length;
		
		while ((length = fis.read(bytes)) >= 0) {
			zipOS.write(bytes, 0, length);
		}
		
		zipOS.closeEntry();
		fis.close();
	}
	
	public static void writeStringToFile(String path) throws IOException{
		PrintWriter writer = new PrintWriter(path + "/path.txt", "UTF-8");
		writer.println(path);
		writer.close();
		
	}

}
