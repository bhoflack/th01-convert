/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melexis;

import com.melexis.th01.TH01WaferMap;
import com.melexis.th01.exception.Th01Exception;
import org.apache.velocity.VelocityContext;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brh
 */
public class MPDConvertor extends AbstractConvertor implements Convertor {

	private final static Map<Character, Integer> FLAT = new HashMap<Character, Integer>() {

		{
			put('N', 0);
			put('E', 90);
			put('S', 180);
			put('W', 270);
		}
	};

	public MPDConvertor(TH01WaferMap th01Wafermap, Die[] refdies) throws Th01Exception, Exception {
		super(new SimpleConvertStrategy('1', '0', '#', 'T'), th01Wafermap, refdies);
	}

	@Override
	protected String convertWafermap() throws Th01Exception, InvalidWafermapException, InvalidRefdieException {
		return addLineNumbers(super.convertWafermap()).trim();
	}

	private static String addLineNumbers(String map) {
		String[] lines = map.split("\n");
		StringBuffer buf = new StringBuffer();

		for (int i = 1; i < lines.length; i++) {
			String number = String.format("%4d", i).replace(" ", "0");
			buf.append(String.format("%s %s\n", number, lines[i - 1]));
		}
		return buf.toString();
	}

	@Override
	protected char getRefdieSymbol() {
		return 'T';
	}

	@Override
	protected char getNoneSymbol() {
		return '#';
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
	/**
	 * Product-ID:     $device
	 * Lot number:     $lotname
	 * Wafer number:   $waferid
	 * Test date:      $testdate
	 * Flat angle:     $flat
	 * Columns:        $columns
	 * Rows:           $rows
	 *
	 * Pass bin code:  $passchar
	 * Fail bin code:  $failchar
	 * Skip die:       $skipchar
	 *
	 * Tested dies:    $tested
	 * Passed dies:    $passed
	 * Failed dies:    $failed
	 *
	 *      $topnumbers
	 *      $numbers
	 */
	protected VelocityContext getContext() throws Th01Exception {
		try {
			StringBuffer ten = new StringBuffer();
			for (int i = 0; i < getTh01Wafermap().getNumberOfColumns() / 10; i++) {
				ten.append(String.format("%10d", i + 1));
			}
			StringBuffer numbers = new StringBuffer();
			for (int i = 1; i <= getTh01Wafermap().getNumberOfColumns(); i++) {
				numbers.append(i % 10);
			}
			VelocityContext context = super.getContext();
			context.put("flat", FLAT.get(getTh01Wafermap().getFlat()));
			context.put("testdate", String.format("%1$tY-%1$tm-%1$td", getTh01Wafermap().getDate()));
			context.put("passchar", getPassSymbol());
			context.put("failchar", getFailSymbol());
			context.put("skipchar", getNoneSymbol());
			context.put("topnumbers", ten.toString());
			context.put("numbers", numbers);
			return context;
		} catch (ParseException ex) {
			Logger.getLogger(MPDConvertor.class.getName()).log(Level.SEVERE, null, ex);
			throw new Th01Exception(ex);
		}

	}

	@Override
	protected String getTemplate() {
		return "mpd.vm";
	}
}
