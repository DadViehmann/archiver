package compress;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class Extract {
	
    public void decompressGzipFile(String gzipFile, String newFile) {
    	try {
    		FileInputStream fis = new FileInputStream(gzipFile);
    		GZIPInputStream gis = new GZIPInputStream(fis);
    		FileOutputStream fos = new FileOutputStream(newFile);
    		byte[] buffer = new byte[1024];
    		int len;
    		while((len = gis.read(buffer)) != -1) {
    			fos.write(buffer, 0, len);
    		}
    		
    		fos.close();
    		gis.close();
    	}
    	catch(IOException e) {
    		e.printStackTrace();
    	}
    }

}
