/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis;

import com.melexis.th01.TH01WaferMap;

/**
 *
 * @author brh
 */
public class InvalidTh01Wafermap extends TH01WaferMap {

	public InvalidTh01Wafermap(byte[] map) {
		super(map);
	}

	@Override
	public Integer getNumberOfRows() {
		return 5;
	}

	@Override
	public Integer getNumberOfColumns() {
		return 5;
	}

}
