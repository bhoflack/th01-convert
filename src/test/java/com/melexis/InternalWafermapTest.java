/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melexis;

import org.junit.Before;
import org.junit.Test;

import static java.util.EnumSet.of;
import static org.junit.Assert.*;

/**
 *
 * @author brh
 */
public class InternalWafermapTest {

	class DummyInternal extends InternalWafermap {

		public DummyInternal() {
			super(null, new SimpleConvertStrategy('P', 'F', 'N', 'R'), new InternalDie[][]{
                    new InternalDie[] {new InternalDie(null, of(DieStatus.NONE)),
                            new InternalDie(1, of(DieStatus.PASS)),
                            new InternalDie(1, of(DieStatus.PASS))},
                    new InternalDie[] {new InternalDie(1, of(DieStatus.REFDIE)),
                            new InternalDie(3, of(DieStatus.REFDIE)),
                            new InternalDie(16, of(DieStatus.FAIL))}}, Flat.NORTH);
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
		assertEquals("RN\nRP\nFP", w.convert().trim());
	}

	@Test
	public void testRotate180Degrees() {
		InternalWafermap w = internal.rotate90Degrees().rotate90Degrees();
		
		assertEquals("FRR\nPPN", w.convert().trim());
	}

    @Test
    public void testRotateSouth() {
        final InternalWafermap w = internal.rotateSouth();

        assertEquals("FRR\nPPN", w.convert().trim());
    }

    @Test
	public void testGetTotalDies() {
		assertEquals(new Integer(5), internal.getTotalDies());
	}

	@Test
    public void testGetTestedDies() {
		assertEquals(new Integer(3), internal.getTestedDies());
	}

    @Test
	public void testGetPassedDies() {
		assertEquals(new Integer(2), internal.getPassedDies());
	}
}
