package compress;

import java.io.File;

import javax.swing.JFileChooser;

public class pathSelector {

    public String pathSelector() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		String userDir = System.getProperty("user.home");
		
		chooser.setCurrentDirectory(new File(userDir + "\\Desktop"));
		chooser.showSaveDialog(null);
		
		String path = chooser.getSelectedFile().getAbsolutePath();
		
		return path;
    }
	
}
