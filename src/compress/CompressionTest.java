package compress;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.io.FilenameUtils;


public class CompressionTest{
	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.showSaveDialog(null);
		
		String path = chooser.getSelectedFile().getAbsolutePath();
		
		System.out.print("Arch to Archive a file, Ext to Extract a file: ");
		
		String choice = in.next();
		
		switch(choice) {
		
		case "Arch":
			String file = FilenameUtils.getBaseName(path);
			String gzipFile = file + "." + FilenameUtils.getExtension(path) + ".gz";
			
			Archive store = new Archive();
			
			String pathExt = FilenameUtils.getExtension(path);
			
			if (pathExt.equals("gz")) {
				System.out.println("This is a zip file, cannot be archived.");
			}
			else {
				System.out.println("Archiving...");
			
				store.compressGzipFile(path, gzipFile);
			
				System.out.println("Done!");
			}
			break;
			
		case "Ext":
			String newFile = FilenameUtils.getName(path);
			newFile = FilenameUtils.removeExtension(newFile);
		
			Extract extract = new Extract();
			
			System.out.println("Extracting...");
			
			extract.decompressGzipFile(path, newFile);
			
			System.out.println("Done!");
			break;
			
		default:
			System.out.println("Invalid Input, exiting");
			break;
		}
		in.close();
	}
	
}