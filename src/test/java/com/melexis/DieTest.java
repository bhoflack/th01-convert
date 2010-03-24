/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis;

import com.melexis.th01.TH01WaferMap;
import com.melexis.th01.exception.Th01Exception;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author brh
 */
public class DieTest extends Utils {

	private TH01WaferMap wafermap;
    private TH01WaferMap b81025;

	@Before
	public void setUp() throws IOException {
		wafermap = new TH01WaferMap(readFileFromResource("carsem_B79929-25-F1.th01"));
        b81025 = new TH01WaferMap(readFileFromResource("carsem_B81025-13-A0.th01"));
	}

	@Test
	public void testToInternalRefdie() throws Th01Exception {
		Die refdie1 = new Die.Builder(-7, -28).setTh01Wafermap(wafermap).build().toInternalRefdie();
		Die refdie2 = new Die.Builder(-33, -7).setTh01Wafermap(wafermap).build().toInternalRefdie();
		
		Die expected1 = new Die.Builder(33, 7).build();
		Die expected2 = new Die.Builder(7, 28).build();

		assertEquals(expected1, refdie1);
		assertEquals(expected2, refdie2);
	}

}
