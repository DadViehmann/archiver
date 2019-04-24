package compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipFile {
	public static void unzipFile(String pathString) throws IOException {
		//pathSelector zipFileSelector = new pathSelector();
		//String pathString = zipFileSelector.pathSelector();
		pathSelector zipFileDest = new pathSelector();
		File destDir = new File(zipFileDest.pathSelector());
		
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
		String[] entries = destDir.list();
		String f = destDir.toString() + "\\path.txt";
		System.out.println(f);
		for(@SuppressWarnings("unused") String s: entries) {
			File currentFile = new File(f);
			System.out.println(currentFile.toString());
			if (currentFile.toString() == f) {
				currentFile.delete();
				break;
			}
		}
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
