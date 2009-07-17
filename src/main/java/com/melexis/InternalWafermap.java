/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melexis;

import com.melexis.th01.TH01Die;
import com.melexis.th01.TH01WaferMap;
import com.melexis.th01.exception.Th01Exception;
import java.util.Arrays;

/**
 *
 * @author brh
 */
public class InternalWafermap {

	protected enum DIE {

		NONE,
		PASS,
		FAIL,
		REFDIE;
	};
	private final DIE[][] wafermap;

	public InternalWafermap(TH01WaferMap th01Wafermap, Die[] refdies) throws Th01Exception, InvalidWafermapException, InvalidRefdieException {
		wafermap = new DIE[th01Wafermap.getNumberOfRows()][th01Wafermap.getNumberOfColumns()];
		initMap(th01Wafermap, refdies);
	}

	protected InternalWafermap(DIE[][] wafermap) {
		this.wafermap = wafermap;
	}

	private void initMap(TH01WaferMap th01Wafermap, Die[] refdies) throws Th01Exception, InvalidWafermapException, InvalidRefdieException {
		for (int y = 0; y < th01Wafermap.getNumberOfRows(); y++) {
			this.wafermap[y] = new DIE[th01Wafermap.getNumberOfColumns()];
			for (int x = 0; x < th01Wafermap.getNumberOfColumns(); x++) {
				this.wafermap[y][x] = DIE.NONE;
			}
		}

		for (TH01Die d : th01Wafermap.getDies()) {
			Die id = null;
			try {
				id = new Die.Builder(d).setTh01Wafermap(th01Wafermap).build().toInternal();
				wafermap[id.getY()][id.getX()] = dieresult(th01Wafermap, d.getBincode());
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new InvalidWafermapException(String.format("The wafermap contains a die %s which is outside the wafermap.", id));
			}
		}
		for (Die refdie : refdies) {
			Die r = null;
			try {
				r = new Die.Builder(refdie).setTh01Wafermap(th01Wafermap).build().toInternalRefdie();
				wafermap[r.getY()][r.getX()] = DIE.REFDIE;
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println(String.format("Wafermap boundaries x: %s %s y: %s %s", th01Wafermap.getMinX(), th01Wafermap.getMaxX(), th01Wafermap.getMinY(), th01Wafermap.getMaxY()));
				throw new InvalidRefdieException(String.format("The coordinates of refdie %s are outside the wafermap.  Please correct!", refdie));
			}
		}
	}

	private DIE dieresult(TH01WaferMap wafermap, short bincode) throws Th01Exception {
		return wafermap.isPassCat((int) bincode) ? DIE.PASS : DIE.FAIL;
	}

	public InternalWafermap rotate90Degrees() {
		DIE[][] w = new DIE[wafermap[0].length][wafermap.length];

		for (int y=0; y<wafermap.length; y++) {
			for (int x=0; x<wafermap[y].length; x++) {
				w[x][wafermap.length - (y +1)] = wafermap[y][x];
			}
		}

		return new InternalWafermap(w);
	}

	public String convert(char pass, char fail, char none, char refdie) {
		StringBuffer b = new StringBuffer();

		for (DIE[] line : wafermap) {
			StringBuffer l = new StringBuffer();
			for (DIE d : line) {
				char die;
				switch (d) {
					case PASS:
						die = pass;
						break;
					case FAIL:
						die = fail;
						break;
					case REFDIE:
						die = refdie;
						break;
					default:
						die = none;
				}
				l.append(die);
			}
			b.append(l).append('\n');
		}
		return b.toString();
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof InternalWafermap)) {
			return false;
		}
		
		InternalWafermap o = (InternalWafermap) other;
		return wafermap != null && wafermap.equals(o.wafermap);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 97 * hash + Arrays.deepHashCode(this.wafermap);
		return hash;
	}

	@Override
	public String toString() {
		return "Internal wafermap \n" + convert('1', '0', '.', 'R');
	}
}
