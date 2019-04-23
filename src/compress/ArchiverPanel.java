package compress;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.apache.commons.io.FilenameUtils;


public class ArchiverPanel {

	  public static void main(String[] arguments) {
		  
		    JPanel panel = new JPanel();
		    panel.setLayout(new FlowLayout());
		    JButton zip = new JButton("ZIP File");
		    JButton unzip = new JButton("Unzip File");
		    JLabel label = new JLabel();
		 
		    // add a JButton to the JPanel.
		    
		    panel.add(zip);
		    
		    panel.add(unzip);
		    
		    panel.add(label);
		    
		    Archive store = new Archive();
		    
		    Extract pull = new Extract();
		    
		    ZipTester betterZip = new ZipTester();
		    
		    UnzipFile betterExtract = new UnzipFile();
		    
		    zip.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		pathSelector fileOrFolder = new pathSelector();
					String pathString = fileOrFolder.pathSelector();
					pathSelector outputPathSelector = new pathSelector();
					String outputPath = outputPathSelector.pathSelector();
					
		    		ZipTester.zipFile(pathString, outputPath);
		    	}
		    });
		    
		    unzip.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		try {
						UnzipFile.unzipFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		    	}
		    });		    		 
		 
		 
		    // main window
		    JFrame frame = new JFrame("CZIP Archiver");
		    frame.setSize(300,150);
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		    // add the Jpanel to the main window
		    frame.add(panel); 
		 
		    frame.setVisible(true);
		 
		  }
	
}
