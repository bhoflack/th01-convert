/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melexis;

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
			put("B79929-25-F1", new Die[]{
					new Die.Builder(-7, -28).build(),
					new Die.Builder(-33, -7).build()
				});
			put("T26655-01-F5", new Die[]{
					new Die.Builder(-18, -17).build(),
					new Die.Builder(-19, -20).build()
				});
            put("A99394-25-A0", new Die[]{});
			put("B79929-25-F1", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79929-24-C6", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79929-23-A3", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79929-22-F3", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79929-21-D0", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79929-20-A5", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79929-19-D0", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79929-17-F5", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79929-16-D2", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79929-15-A7", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79929-14-F7", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79929-13-D4", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79929-12-B1", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79929-11-G1", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79929-10-D6", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79929-09-G1", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79929-08-D6", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79929-07-B3", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79929-06-G3", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79929-05-E0", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79929-04-B5", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79929-03-G5", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79929-02-E2", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("T26815-25-E7", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26815-24-C4", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26815-23-A1", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26815-22-F1", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26815-21-C6", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26815-20-A3", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26815-19-C6", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26815-18-A3", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26815-17-F3", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26815-16-D0", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26815-15-A5", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26815-14-F5", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26815-13-D2", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26815-12-A7", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26815-11-F7", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26815-10-D4", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26815-09-F7", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26815-08-D4", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26815-06-G1", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26815-04-B3", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26815-05-D6", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26815-03-G3", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26815-02-E0", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26752-25-G0", new Die[]{new Die.Builder(-28, -23).build(), new Die.Builder(-31, -27).build()});
			put("T26752-24-D5", new Die[]{new Die.Builder(-28, -23).build(), new Die.Builder(-31, -27).build()});
			put("T26752-23-B2", new Die[]{new Die.Builder(-28, -23).build(), new Die.Builder(-31, -27).build()});
			put("T26752-22-G2", new Die[]{new Die.Builder(-28, -23).build(), new Die.Builder(-31, -27).build()});
			put("T26752-21-D7", new Die[]{new Die.Builder(-28, -23).build(), new Die.Builder(-31, -27).build()});
			put("T26752-20-B4", new Die[]{new Die.Builder(-28, -23).build(), new Die.Builder(-31, -27).build()});
			put("T26752-19-D7", new Die[]{new Die.Builder(-28, -23).build(), new Die.Builder(-31, -27).build()});
			put("T26752-18-B4", new Die[]{new Die.Builder(-28, -23).build(), new Die.Builder(-31, -27).build()});
			put("T26752-16-E1", new Die[]{new Die.Builder(-28, -23).build(), new Die.Builder(-31, -27).build()});
			put("T26752-15-B6", new Die[]{new Die.Builder(-28, -23).build(), new Die.Builder(-31, -27).build()});
			put("T26752-14-G6", new Die[]{new Die.Builder(-28, -23).build(), new Die.Builder(-31, -27).build()});
			put("T26752-13-E3", new Die[]{new Die.Builder(-28, -23).build(), new Die.Builder(-31, -27).build()});
			put("T26752-12-C0", new Die[]{new Die.Builder(-28, -23).build(), new Die.Builder(-31, -27).build()});
			put("T26752-11-H0", new Die[]{new Die.Builder(-28, -23).build(), new Die.Builder(-31, -27).build()});
			put("T26752-10-E5", new Die[]{new Die.Builder(-28, -23).build(), new Die.Builder(-31, -27).build()});
			put("T26752-08-E5", new Die[]{new Die.Builder(-28, -23).build(), new Die.Builder(-31, -27).build()});
			put("T26752-07-C2", new Die[]{new Die.Builder(-28, -23).build(), new Die.Builder(-31, -27).build()});
			put("T26752-05-E7", new Die[]{new Die.Builder(-28, -23).build(), new Die.Builder(-31, -27).build()});
			put("T26752-04-C4", new Die[]{new Die.Builder(-28, -23).build(), new Die.Builder(-31, -27).build()});
			put("T26752-02-F1", new Die[]{new Die.Builder(-28, -23).build(), new Die.Builder(-31, -27).build()});
			put("T26752-01-C6", new Die[]{new Die.Builder(-28, -23).build(), new Die.Builder(-31, -27).build()});
			put("B79923-25-D0", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79923-24-A5", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79923-23-F5", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79923-22-D2", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79923-21-A7", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79923-20-F7", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79923-19-A7", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79923-18-F7", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79923-17-D4", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79923-16-B1", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79923-15-G1", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79923-14-D6", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79923-13-B3", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79923-12-G3", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79923-11-E0", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79923-10-B5", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79923-09-E0", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79923-08-B5", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79923-06-E2", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79923-05-B7", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79923-04-G7", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("B79923-03-E4", new Die[]{new Die.Builder(-7, -28).build(), new Die.Builder(-33, -7).build()});
			put("T26892-25-D1", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26892-24-A6", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26892-22-D3", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26892-21-B0", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26892-20-G0", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26892-19-B0", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26892-18-G0", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26892-17-D5", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
			put("T26892-16-B2", new Die[]{new Die.Builder(-18, -17).build(), new Die.Builder(-19, -20).build()});
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

			
			assertEquals(new String(readFileFromResource(output)), new String(carsemConvertor.convert()));
		}
	}
}
