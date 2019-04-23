package compress;

import javax.swing.JFileChooser;

public class pathSelector {

    public String pathSelector() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.showSaveDialog(null);
		
		String path = chooser.getSelectedFile().getAbsolutePath();
		
		return path;
    }
	
}
