/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class MPDConvertorTest extends AbstractTestUtils {

	private TH01WaferMap wafermap;
	private MPDConvertor mpdc;

	@Before
	public void setUp() throws IOException, Th01Exception, Exception {
		wafermap = new TH01WaferMap(readFileFromResource("WC_A95518_20_07052008_011117.th01"));
		mpdc = new MPDConvertor(wafermap, new Die[] {});
	}

	/**
	 * Test that we can convert a th wafermap to the mpd format.
	 * @throws java.io.IOException
	 * @throws com.melexis.th01.exception.Th01Exception
	 */
	@Test
	public void testConvert() throws IOException, Th01Exception, InvalidRefdieException, InvalidWafermapException, Exception {
		String converted = new String(mpdc.convert());

		System.out.println(converted);
		assertEquals(new String(readFileFromResource("mpd.example")), converted);
	}

}
