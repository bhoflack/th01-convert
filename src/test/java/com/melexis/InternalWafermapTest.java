/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melexis;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author brh
 */
public class InternalWafermapTest {

	class DummyInternal extends InternalWafermap {

		public DummyInternal() {
			super(new DIE[][]{new DIE[]{DIE.NONE, DIE.PASS, DIE.PASS},
					new DIE[]{DIE.REFDIE, DIE.REFDIE, DIE.FAIL}});
		}
	}

	private DummyInternal internal;

	@Before
	public void setUp() {
		internal = new DummyInternal();
	}

	@Test
	public void testRotate90Degrees() {
		InternalWafermap w = internal.rotate90Degrees();
		
		assertFalse("The wafermaps should not be equal" , w.equals(internal));
		assertEquals("RN\nRP\nFP", w.convert('P', 'F', 'N', 'R').trim());
	}

	@Test
	public void testRotate180Degrees() {
		InternalWafermap w = internal.rotate90Degrees().rotate90Degrees();
		
		assertEquals("FRR\nPPN", w.convert('P', 'F', 'N', 'R').trim());
	}

	public void testGetTotalDies() {
		assertEquals(new Integer(5), internal.getTotalDies());
	}

	public void testGetTestedDies() {
		assertEquals(new Integer(3), internal.getTestedDies());
	}

	public void testGetPassedDies() {
		assertEquals(new Integer(2), internal.getPassedDies());
	}
}
