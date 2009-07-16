/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis;

import com.melexis.th01.TH01WaferMap;
import com.melexis.th01.exception.Th01Exception;
import java.util.Properties;
import org.apache.velocity.VelocityContext;

/**
 *
 * @author brh
 */
public class CarsemConvertor extends AbstractConvertor implements Convertor {

	private final String waferid;

	public CarsemConvertor(TH01WaferMap th01Wafermap, Die[] refdies, String waferid) throws Th01Exception, Exception {
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
		VelocityContext context = new VelocityContext();
		context.put("waferid", waferid);
		context.put("orientation", 6);
		context.put("maxX", getTh01Wafermap().getNumberOfColumns());
		context.put("maxY", getTh01Wafermap().getNumberOfRows());
		context.put("dies", getTh01Wafermap().getNeff());
		context.put("tested", getTh01Wafermap().getTotalCnt());
		context.put("passed", getTh01Wafermap().getPassCnt());

		return context;
	}
	
	@Override
	protected String getTemplate() {
		return "carsem.vm";
	}

}
