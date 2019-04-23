package compress;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FilenameUtils;

public class ArchiveUpdate {
	
	public static void main(String[] args) throws IOException{
		
		pathSelector zipFileSelector = new pathSelector();
		String pathString = zipFileSelector.pathSelector();
		File destDir = new File("temp");
		destDir.mkdirs();
		System.out.println(FilenameUtils.getBaseName(pathString));
		
		byte[] buffer = new byte[1024];
		ZipInputStream zis = new ZipInputStream(new FileInputStream(pathString));
		ZipEntry zipEntry = zis.getNextEntry();
		
		while (zipEntry != null) {
			File newFile = newFile(destDir, zipEntry);
			FileOutputStream fos = new FileOutputStream(newFile);
			int len;
			while ((len = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			fos.close();
			zipEntry = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();
		
		String f = destDir.toString() + "\\path.txt";
		String storedPath = new String();
		
		File infile = new File(f);
		Scanner sc = new Scanner(infile);
		
		while (sc.hasNext()){
			storedPath = sc.nextLine();
		}
		
		sc.close();
		
		String[] entries = destDir.list();
		for(String s: entries) {
			File currentFile = new File(destDir.getPath(), s);
			currentFile.delete();
		}
		Files.deleteIfExists(Paths.get("temp"));
		System.out.println(storedPath);
		
		System.out.println(FilenameUtils.getFullPath(pathString));
		
		ZipTester.zipFile(storedPath, FilenameUtils.getFullPath(pathString));
	}
	
	public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException{
		File destFile = new File(destinationDir, zipEntry.getName());
		
		String destDirPath = destinationDir.getCanonicalPath();
		String destFilePath = destFile.getCanonicalPath();
		
		if (!destFilePath.startsWith(destDirPath + File.separator)) {
			throw new IOException("Entry is outside of the target directory: " + zipEntry.getName());
		}
		
		return destFile;
	}

}
