/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis;

import com.melexis.th01.TH01WaferMap;
import com.melexis.th01.exception.Th01Exception;
import fitlibrary.DoFixture;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author brh
 */
public class CarsemConvertorFixture extends DoFixture {

	public CarsemConvertorFixture() {}

	public boolean convertWithWaferidAndRefdiesCompareWith(String thurl, String lotname, String refdies, String exurl) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, Th01Exception, Exception {
		TH01WaferMap th = new TH01WaferMap(download(new URL(thurl)));
		if (!th.getVersion().equals("TH01")) throw new AssertionError("Invalid th wafermap!");

		System.out.println(String.format("Converting a wafermap with %s rows and %s columns.", th.getNumberOfRows(), th.getNumberOfColumns()));
		String expected = new String(download(new URL(exurl)));
		CarsemConvertor c = new CarsemConvertor(th, refdies(refdies), lotname);

		String converted = new String(c.convert());
		if (!expected.equals(converted)) {
			System.out.println(String.format("Got %s while expected %s", converted, expected));
		}

		return expected.equals(new String(c.convert()));
	}

	private byte[] download(URL file) throws IOException {
		System.out.println("Downloading file " + file);

		BufferedInputStream is = new BufferedInputStream(file.openStream());
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		int c;
		while ((c = is.read()) != -1) {
			os.write(c);
		}

		os.flush();
		is.close();

		byte [] buf = os.toByteArray();
		System.out.println(String.format("Read %d bytes", buf.length));
		return buf;
	}

	/**
	 * Convert the refdies to the internal format
	 * @param refdies Refdies have the format [x1,y1];[x2,y2]
	 * @return
	 */
	private Die[] refdies(String refdies) {
		List<Die> dies = new ArrayList<Die>();

		for (String refdie : refdies.split(";")) {
			String[] pos = refdie.replace("[", "").replace("]", "").split(",");
			Integer x = Integer.parseInt(pos[0]);
			Integer y = Integer.parseInt(pos[1]);

			dies.add(new Die.Builder(x, y).build());
		}

		return dies.toArray(new Die[dies.size()]);
	}

}
