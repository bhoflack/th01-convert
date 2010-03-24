/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author brh
 */
public class Utils {

	public Utils() {
	}

    protected static byte[] readFileFromResource(String name) throws IOException {
		InputStream is = Utils.class.getClassLoader().getResourceAsStream(name);
		byte[] buf = new byte[is.available()];
		is.read(buf);
		return buf;
	}

	protected static void toFile(byte[] data) throws FileNotFoundException, IOException {
		File f = File.createTempFile("wafermap", "mpd");
		f.createNewFile();
		FileOutputStream fos = new FileOutputStream(f);

		fos.write(data);
		fos.flush();
		fos.close();
	}

}
