/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melexis;

import com.melexis.th01.TH01Die;
import com.melexis.th01.TH01WaferMap;
import com.melexis.th01.exception.Th01Exception;

import static java.lang.Math.abs;

/**
 *
 * @author brh
 */
public class Die {

	private final int x;
	private final int y;
	private final TH01WaferMap th01Wafermap;

	public static class Builder {

		private final int x;
		private final int y;
		private TH01WaferMap th01Wafermap;

		public Builder(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public Builder(TH01Die d) {
			x = d.getXCoord();
			y = d.getYCoord();
		}

		public Builder(Die o) {
			x = o.x;
			y = o.y;
		}

		public Builder setTh01Wafermap(TH01WaferMap th01Wafermap) {
			this.th01Wafermap = th01Wafermap;
			return this;
		}

		public Die build() {
			return new Die(this);
		}
	}

	protected Die(Builder b) {
		x = b.x;
		y = b.y;
		th01Wafermap = b.th01Wafermap;
	}

	/**
	 * Convert the die to the internal coordinates
	 * @return a die with the internal coordinates
	 * @throws com.melexis.th01.exception.Th01Exception
	 * @throws AssertionError method was called without th01wafermap
	 */
	public Die toInternalRefdie() throws Th01Exception {
		if (th01Wafermap == null) {
			throw new AssertionError("Can not convert a die to internal without the wafermap filled in!");
		}

        int ix, iy;

        if (th01Wafermap.getFlat().equals('E')) {
            ix = x - th01Wafermap.getMinX() - 1;
            iy = th01Wafermap.getMaxY() - y;
        } else {
		    ix = th01Wafermap.getNumberOfColumns() + x - 1;
		    iy = th01Wafermap.getNumberOfRows() + y - 1;
        }

		return new Builder(ix, iy).setTh01Wafermap(th01Wafermap).build();
	}

	/**
	 * Convert the die to the internal coordinates
	 * @return a die with the internal coordinates
	 * @throws com.melexis.th01.exception.Th01Exception
	 * @throws AssertionError method was called without th01wafermap
	 */
	public Die toInternal() throws Th01Exception {
		if (th01Wafermap == null) {
			throw new AssertionError("Can not convert a die to internal without the wafermap filled in!");
		}
        final int maxX = th01Wafermap.getMaxX();
        final int maxY = th01Wafermap.getMinY();

        int ix = abs(maxX - x);
        int iy = abs(y - maxY);

        return new Builder(ix, iy).setTh01Wafermap(th01Wafermap).build();
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Die)) {
			return false;
		}
		Die o = (Die) other;
		return o.getX() == getX() && o.getY() == getY();
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 83 * hash + this.x;
		hash = 83 * hash + this.y;
		hash = 83 * hash + (this.th01Wafermap != null ? this.th01Wafermap.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		return String.format("[%d,%d]", getX(), getY());
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
}
