/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis;

import com.melexis.semichecksum.Checksum;
import com.melexis.semichecksum.ChecksumException;
import com.melexis.th01.TH01WaferMap;
import com.melexis.th01.exception.Th01Exception;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author brh
 */
public class CarsemConvertorTest extends AbstractTestUtils {

	private final static Map<String, Die[]> EXAMPLES = new HashMap<String, Die[]>() {
		{
			put("B79929-25-F1", new Die[] {
				new Die.Builder(-7, -28).build(),
				new Die.Builder(-33, -7).build()
			});
			put("T26655-01-F5", new Die[] {
				new Die.Builder(-18, -17).build(),
				new Die.Builder(-19, -20).build()
			});
		}
	};

	@Test
	public void testConvert() throws IOException, Th01Exception, InvalidRefdieException, InvalidWafermapException, Exception {
		for (String example : EXAMPLES.keySet()) {
			String thmap = String.format("carsem_%s.th01", example);
			String output = String.format("carsem_%s.example", example);

			TH01WaferMap t = new TH01WaferMap(readFileFromResource(thmap));
			Die[] refdies = EXAMPLES.get(example);
			CarsemConvertor carsemConvertor = new CarsemConvertor(t, refdies, example);
			
			toFile(carsemConvertor.convert());

			System.out.println(new String(carsemConvertor.convert()));
			assertEquals(new String(readFileFromResource(output)), new String(carsemConvertor.convert()));
		}
	}

	
}
