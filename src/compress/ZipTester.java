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
					if (child.isDirectory()) {
						addDirectory(zipOS, childName, child);
					}
					else {
						writeToZipFile(childName, zipOS);
					}
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
		PrintWriter writer = new PrintWriter(path + "\\path.txt", "UTF-8");
		writer.println(path);
		writer.close();
		
	}
	
	public static String getRelativePath(String sourceDir, File file) {
	    // Trim off the start of source dir path...
	    String path = file.getPath().substring(sourceDir.length());
	    if (path.startsWith(File.pathSeparator)) {
	        path = path.substring(1);
	    }
	    return path;
	}
	
	private static void addDirectory(ZipOutputStream zout, String sourceDir, File fileSource) throws IOException {
	    if (fileSource.isDirectory()) {
	        // Add the directory to the zip entry...
	        String path = getRelativePath(sourceDir, fileSource);
	        if (path.trim().length() > 0) {
	            ZipEntry ze = new ZipEntry(getRelativePath(sourceDir, fileSource));
	            zout.putNextEntry(ze);
	            zout.closeEntry();
	        }

	        File[] files = fileSource.listFiles();
	        System.out.println("Adding directory " + fileSource.getName());
	        for (int i = 0; i < files.length; i++) {
	            if (files[i].isDirectory()) {
	                addDirectory(zout, sourceDir, files[i]);
	            } else {

	                System.out.println("Adding file " + files[i].getName());

	                //create byte buffer
	                byte[] buffer = new byte[1024];

	                //create object of FileInputStream
	                FileInputStream fin = new FileInputStream(files[i]);
	                zout.putNextEntry(new ZipEntry(getRelativePath(sourceDir, files[i])));

	                int length;

	                while ((length = fin.read(buffer)) > 0) {
	                    zout.write(buffer, 0, length);
	                }
	                zout.closeEntry();
	                //close the InputStream
	                fin.close();
	            }
	        }
	    }
	}

}
