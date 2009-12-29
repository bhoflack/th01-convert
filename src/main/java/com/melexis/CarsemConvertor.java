/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis;

import com.melexis.th01.TH01Die;
import com.melexis.th01.TH01WaferMap;
import com.melexis.th01.exception.Th01Exception;
import org.apache.velocity.VelocityContext;

/**
 *
 * @author brh
 */
public class CarsemConvertor extends AbstractConvertor implements Convertor {

	private final String waferid;

	public CarsemConvertor(TH01WaferMap th01Wafermap, Die[] refdies, String waferid)
		throws Th01Exception, Exception {
		super(th01Wafermap, refdies);
		this.waferid = waferid;
	}

	@Override
	protected char getRefdieSymbol() {
		return 'T';
	}

	@Override
	protected char getNoneSymbol() {
		return '.';
	}

	@Override
	protected char getPassSymbol() {
		return '1';
	}

	@Override
	protected char getFailSymbol() {
		return '0';
	}

	@Override
	protected VelocityContext getContext() throws Th01Exception {
		VelocityContext context = super.getContext();

        int orient;
        if (super.th01Wafermap.getFlat().equals('O')) {
            orient = 3;
        } else {
            orient = 6;
        }

        context.put("tested", testedDies());
		context.put("orientation", orient);
		context.put("waferid", waferid);
		
		return context;
	}
	
	@Override
	protected String getTemplate() {
		return "carsem.vm";
	}

	private int testedDies() {
		int untested = 0;

		for (TH01Die d : getTh01Wafermap().getDies()) {
			if (d.getBincode() == 14) {
				untested++;
			}
		}

		return getTh01Wafermap().getDies().size() - untested;
	}

}
