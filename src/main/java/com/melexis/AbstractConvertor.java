/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melexis;

import com.melexis.th01.TH01Die;
import com.melexis.th01.TH01WaferMap;
import com.melexis.th01.exception.Th01Exception;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

/**
 *
 * @author brh
 */
public abstract class AbstractConvertor {

	private final TH01WaferMap th01Wafermap;
	private final InternalWafermap internalWafermap;

	public AbstractConvertor(TH01WaferMap th01Wafermap, Die[] refdies) throws Th01Exception, InvalidWafermapException, InvalidRefdieException {
		this.th01Wafermap = th01Wafermap;
		this.internalWafermap = new InternalWafermap(th01Wafermap, refdies);
	}

	protected String convertWafermap() throws Th01Exception, InvalidWafermapException, InvalidRefdieException {
		return internalWafermap.convert(getPassSymbol(), getFailSymbol(), getNoneSymbol(), getRefdieSymbol());
	}
	

	public byte[] convert() throws Exception {
		Properties p = new Properties();
		p.setProperty("resource.loader", "classpath");
		p.setProperty("classpath.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init(p);
		VelocityContext context = getContext();
		context.put("wafermap", convertWafermap());
		Template t = null;
		t = Velocity.getTemplate(getTemplate());
		StringWriter sw = new StringWriter();
		t.merge(context, sw);
		sw.flush();
		return sw.toString().getBytes();
	}

	protected char dieresult(short bincode) throws Th01Exception {
		return getTh01Wafermap().isPassCat((int) bincode) ? getPassSymbol() : getFailSymbol();
	}

	protected abstract char getRefdieSymbol();

	protected abstract char getNoneSymbol();

	protected abstract char getPassSymbol();

	protected abstract char getFailSymbol();

	protected abstract VelocityContext getContext() throws Th01Exception;

	protected abstract String getTemplate();

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof AbstractConvertor)) {
			return false;
		}
		AbstractConvertor o = (AbstractConvertor) other;

		return Arrays.equals(wafermap, o.wafermap) &&
			getTh01Wafermap().equals(o.getTh01Wafermap()) &&
			Arrays.equals(refdies, o.refdies);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 59 * hash + (this.getTh01Wafermap() != null ? this.getTh01Wafermap().hashCode() : 0);
		hash = 59 * hash + (this.wafermap != null ? this.wafermap.hashCode() : 0);
		hash = 59 * hash + (this.refdies != null ? this.refdies.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		return getTh01Wafermap().toString();
	}

	/**
	 * @return the th01Wafermap
	 */
	public TH01WaferMap getTh01Wafermap() {
		return th01Wafermap;
	}
}
