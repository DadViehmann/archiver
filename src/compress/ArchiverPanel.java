package compress;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.io.FilenameUtils;

import javax.swing.SwingUtilities;



public class ArchiverPanel {

	  public static void main(String[] arguments) {
		  
		  	try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (ClassNotFoundException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			} catch (InstantiationException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			} catch (IllegalAccessException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			} catch (UnsupportedLookAndFeelException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
		   
		  	JPanel panel = new JPanel();
		  	
		    panel.setPreferredSize(new Dimension(200, 200));
		    panel.setLayout(new FlowLayout());
		    
		    JButton zip = new JButton("Zip Selected File");
		    JButton unzip = new JButton("Unzip File/Directory");
		    JButton update = new JButton("Update Existing Archive");
		    JLabel label = new JLabel();
		    
		    String userDir = System.getProperty("user.home");
		    
		    final JFileChooser fileChooser = new JFileChooser();
		    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		    fileChooser.setCurrentDirectory(new File(userDir + "\\Desktop"));
		    panel.add(fileChooser, BorderLayout.NORTH);
		 
		    // add a JButton to the JPanel.
		    
		    panel.add(zip);
		    
		    panel.add(unzip);
		    
		    panel.add(update);	
		    
		    panel.add(label);
		    
		    zip.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		//pathSelector fileOrFolder = new pathSelector();
					String pathString = fileChooser.getSelectedFile().getAbsolutePath();
					System.out.println(pathString);
					
					if (FilenameUtils.getExtension(pathString).equals("zip")) {
						label.setText("Cannot archive zip files");
					}
					else {
						pathSelector outputPathSelector = new pathSelector();
						String outputPath = outputPathSelector.pathSelector();
						System.out.println(FilenameUtils.getFullPath(outputPath));
						
						if (outputPath == pathString) {
							outputPath = FilenameUtils.getFullPath(outputPath);
						}
						ZipTester.zipFile(pathString, outputPath);
					}
		    	}
		    });
		    
		    unzip.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		try {
		    			File selectedFile = fileChooser.getSelectedFile();
		    			String pathString = selectedFile.toString();
		    			if (FilenameUtils.getExtension(pathString).contentEquals("zip")) {
		    				UnzipFile.unzipFile(pathString);
		    			}
		    			else {
		    				label.setText("Selected file is not a zip, cannot be extracted");
		    			}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		    	}
		    });	
		    
		    update.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		try {
		    			File selectedFile = fileChooser.getSelectedFile();
		    			String pathString = selectedFile.toString();
		    			if (FilenameUtils.getExtension(pathString).equals("zip")) {
		    				ArchiveUpdate.updateZipArchive(pathString);
		    			}
		    			else {
		    				label.setText("Selected File is not a zip");
		    			}
		    		} catch (IOException e2) {
		    			e2.printStackTrace();
		    		}
		    	}
		    });	 
		 
		    // main window
		    JFrame frame = new JFrame("VZIP Archiver Menu");
		    frame.setSize(710, 500);
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setLocationRelativeTo(null);
		 
		    // add the Jpanel to the main window
		    frame.add(panel); 
//		    frame.add(panel3);
		    
		 
		    frame.setVisible(true);
		 
		  }
	
}
