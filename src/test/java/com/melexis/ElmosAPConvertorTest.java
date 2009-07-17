package com.melexis;

import com.melexis.th01.TH01WaferMap;
import com.melexis.th01.exception.Th01Exception;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author brh
 */
public class ElmosAPConvertorTest extends AbstractTestUtils {

	private TH01WaferMap wafermap;

	@Before
	public void setUp() throws IOException {
		wafermap = new TH01WaferMap(readFileFromResource("WC_A95518_20_07052008_011117.th01"));
	}

	/**
	 * Test that we can convert a th wafermap to the elmos format.  
	 * @throws java.io.IOException
	 * @throws com.melexis.th01.exception.Th01Exception
	 */
	@Test
	public void testConvert() throws IOException, Th01Exception, InvalidRefdieException, InvalidWafermapException, Exception {
		ElmosAPConvertor elmosAPConvertor = new ElmosAPConvertor.Builder(wafermap).build();
		byte[] map = elmosAPConvertor.convert();
		assertArrayEquals(readFileFromResource("elmos.example"), map);
	}


	/**
	 * Test that we can add refdies to the wafermap.
	 * @throws com.melexis.th01.exception.Th01Exception
	 */
	@Test
	public void testRefdies() throws Th01Exception, InvalidRefdieException, InvalidWafermapException, Exception {
		// Create the refdies
		Die[] refdies = new Die[] {new Die.Builder(12, -72).build(),
						new Die.Builder(4, -46).build()};

		// Create the convertor
		ElmosAPConvertor elmosAPConvertor = new ElmosAPConvertor.Builder(wafermap).setRefdies(refdies).build();
		String w = new String(elmosAPConvertor.convert());

		// Assert that the converted wafermaps contains refdies
		assertTrue(w.contains("T"));

		String[] lines = w.split("\n");

		// Assert that the refdies have been added at the correct location
		assertEquals('T', lines[46].charAt(52));
		assertEquals('T', lines[72].charAt(44));
	}

	/**
	 * Test the behaviour when a refdie is defined outside of the wafermap
	 * @throws com.melexis.th01.exception.Th01Exception
	 */
	@Test(expected=InvalidRefdieException.class)
	public void testRefdiesOutsideWafermap() throws Th01Exception, InvalidRefdieException, InvalidWafermapException, Exception {
		// Create the refdies
		Die[] refdies = new Die[] { new Die.Builder(1000,1000).build(),
						new Die.Builder(2500, 2500).build()};

		// Create the convertor
		ElmosAPConvertor elmosAPConvertor = new ElmosAPConvertor.Builder(wafermap).setRefdies(refdies).build();
		elmosAPConvertor.convert();
	}

	@Test(expected=InvalidWafermapException.class)
	public void testWafermapDiesOutsideWafermap() throws Th01Exception, IOException, InvalidRefdieException, InvalidWafermapException, Exception {
		wafermap = new InvalidTh01Wafermap(readFileFromResource("WC_A95518_20_07052008_011117.th01"));
		ElmosAPConvertor elmosAPConvertor = new ElmosAPConvertor.Builder(wafermap).build();
		
		elmosAPConvertor.convert();
	}
}
