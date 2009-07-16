/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melexis;

import com.melexis.th01.TH01WaferMap;
import com.melexis.th01.exception.Th01Exception;
import org.apache.velocity.VelocityContext;

/**
 *
 * @author brh
 */
public class ElmosAPConvertor extends AbstractConvertor implements Convertor {
	private static final char PASS = '1';
	private static final char FAIL = 'F';
	private static final char NONE = '.';
	private static final char REFDIE = 'T';

	public static class Builder {
		private final TH01WaferMap th01Wafermap;
		private Die[] refdies;

		public Builder(TH01WaferMap th01Wafermap) {
			this.th01Wafermap = th01Wafermap;
			refdies = new Die[]{};
		}

		public Builder setRefdies(Die[] refdies) {
			this.refdies = refdies;
			return this;
		}

		public ElmosAPConvertor build() throws Th01Exception, Exception {
			return new ElmosAPConvertor(this);
		}
	};

	public ElmosAPConvertor(Builder b) throws Th01Exception, Exception {
		super(b.th01Wafermap, b.refdies);
	}

	@Override
	public char getRefdieSymbol() {
		return REFDIE;
	}

	@Override
	protected char getNoneSymbol() {
		return NONE;
	}

	@Override
	protected char getPassSymbol() {
		return PASS;
	}

	@Override
	protected char getFailSymbol() {
		return FAIL;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ElmosAPConvertor)) {
			return false;
		}
		return super.equals(other);
	}

	@Override
	public String toString() {
		return "Elmos convertor for " + super.toString();
	}

	@Override
	protected VelocityContext getContext() throws Th01Exception {
		return new VelocityContext();
	}

	@Override
	protected String getTemplate() {
		return "elmos.vm";
	}
}
