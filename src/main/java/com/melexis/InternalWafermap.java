/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melexis;

import com.melexis.th01.TH01Die;
import com.melexis.th01.TH01WaferMap;
import com.melexis.th01.exception.Th01Exception;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    public final int rows;
    public final int cols;

    public InternalWafermap(TH01WaferMap th01Wafermap, Die[] refdies) throws Th01Exception, InvalidWafermapException, InvalidRefdieException {
        // Calculate our own max rows and columns
        // as our internal representation is not
        // dependant of the orientation.
        rows = th01Wafermap.getMaxY() - th01Wafermap.getMinY() + 1;
        cols = th01Wafermap.getMaxX() - th01Wafermap.getMinX() + 1;
		wafermap = new DIE[rows][cols];
		initMap(th01Wafermap, refdies, rows, cols);
	}

	protected InternalWafermap(DIE[][] wafermap) {
		this.wafermap = wafermap;
        rows = wafermap.length;
        cols = (wafermap.length > 0)? wafermap[0].length : 0;
	}

	private void initMap(TH01WaferMap th01Wafermap, Die[] refdies, final int rows, final int cols)
            throws Th01Exception, InvalidWafermapException, InvalidRefdieException {

		for (int y = 0; y < rows; y++) {
			wafermap[y] = new DIE[cols];
			for (int x = 0; x < cols; x++) {
				wafermap[y][x] = DIE.NONE;
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
                if (d == null) {
                    die = none;
                } else {
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
                }
				l.append(die);
			}
			b.append(l).append('\n');
		}
		return b.toString();
	}

	private Map<DIE, Integer> diecategories() {
		Map<DIE,Integer> map = new HashMap<DIE,Integer>();
		map.put(DIE.NONE, 0);
		map.put(DIE.PASS, 0);
		map.put(DIE.FAIL, 0);
		map.put(DIE.REFDIE, 0);

		for (DIE[] line : wafermap) {
			for (DIE d : line) {
                if (d == null) {
                    map.put(DIE.NONE, map.get(DIE.NONE) + 1);
                } else {
				    map.put(d, map.get(d) + 1);
                }
			}
		}
		return map;
	}

	public Integer getTotalDies() {
		Map<DIE, Integer> dies = diecategories();
		return dies.get(DIE.REFDIE) + dies.get(DIE.PASS) + dies.get(DIE.FAIL);
	}

	public Integer getTestedDies() {
		Map<DIE, Integer> dies = diecategories();
		return dies.get(DIE.PASS) + dies.get(DIE.FAIL);
	}

	public Integer getPassedDies() {
		Map<DIE, Integer> dies = diecategories();
		return dies.get(DIE.PASS);
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
