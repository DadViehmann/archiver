package compress;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.JFileChooser;

public class Zip {

	public void zipFolder(String path) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("Test String");

		File f = new File("test.zip");
		//File n = new File(path);
		ZipOutputStream out;
		try {
			out = new ZipOutputStream(new FileOutputStream(f));
			ZipEntry e = new ZipEntry(path);
			out.putNextEntry(e);

			byte[] data = sb.toString().getBytes();
			out.write(data, 0, data.length);
			out.closeEntry();

			out.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	public void zipfsTest(String path) {
		Map<String, String> env = new HashMap<>(); 
		env.put("create", "true");

		URI uri = URI.create("jar:file:/zipfstest.zip");

		try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
		    Path externalTxtFile = Paths.get(path);
		    Path pathInZipfile = zipfs.getPath("/SomeTextFile.txt");          
		    // copy a file into the zip file
		    Files.copy(externalTxtFile, pathInZipfile, StandardCopyOption.REPLACE_EXISTING); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public String pathSelector() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.showSaveDialog(null);
		
		String path = chooser.getSelectedFile().getAbsolutePath();
		
		return path;
    }
}
