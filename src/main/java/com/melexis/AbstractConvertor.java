/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melexis;

import com.melexis.th01.TH01WaferMap;
import com.melexis.th01.exception.Th01Exception;
import java.io.StringWriter;
import java.util.Properties;
import java.util.logging.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

/**
 *
 * @author brh
 */
public abstract class AbstractConvertor {

	public final TH01WaferMap th01Wafermap;
	public final InternalWafermap internalWafermap;

	public AbstractConvertor(TH01WaferMap th01Wafermap, Die[] refdies) throws Th01Exception, InvalidWafermapException, InvalidRefdieException {
		this.th01Wafermap = th01Wafermap;
		this.internalWafermap = new InternalWafermap(th01Wafermap, refdies);
	}

	protected String convertWafermap() throws Th01Exception, InvalidWafermapException, InvalidRefdieException {
		// rotate 180 degrees
		InternalWafermap i = internalWafermap.rotate90Degrees().rotate90Degrees();
		return i.convert(getPassSymbol(), getFailSymbol(), getNoneSymbol(), getRefdieSymbol());
	}

	public byte[] convert() throws Exception {
		BasicConfigurator.configure();
		Logger log = Logger.getLogger("AbstractConvertor");
		log.info("Log4jLoggerExample: ready to start velocity");

		/*
		 *  now create a new VelocityEngine instance, and
		 *  configure it to use the category
		 */

		VelocityEngine ve = new VelocityEngine();

		ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
			"org.apache.velocity.runtime.log.Log4JLogChute");

		ve.setProperty("runtime.log.logsystem.log4j.logger",
			"AbstractConvertor");
		ve.setProperty("resource.loader", "classpath");
		ve.setProperty("classpath.resource.loader.class",
			"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

		ve.init();
		VelocityContext context = getContext();
		context.put("wafermap", convertWafermap());
		Template t = null;
		t = ve.getTemplate(getTemplate());
		StringWriter sw = new StringWriter();
		t.merge(context, sw);
		sw.flush();
		return sw.toString().getBytes();
	}

	protected void rotate90Degrees() {
		internalWafermap.rotate90Degrees();
	}

	protected abstract char getRefdieSymbol();

	protected abstract char getNoneSymbol();

	protected abstract char getPassSymbol();

	protected abstract char getFailSymbol();

	protected VelocityContext getContext() throws Th01Exception {
		VelocityContext ctx = new VelocityContext();
		ctx.put("info", th01Wafermap);
		ctx.put("int", this);
        ctx.put("rows", internalWafermap.rows);
        ctx.put("cols", internalWafermap.cols);

		return ctx;
	}

	protected abstract String getTemplate();

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof AbstractConvertor)) {
			return false;
		}
		AbstractConvertor o = (AbstractConvertor) other;

		return internalWafermap != null && internalWafermap.equals(o.internalWafermap);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 59 * hash + (this.getTh01Wafermap() != null ? this.getTh01Wafermap().hashCode() : 0);
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

	public Integer getTotalDies() {
		return internalWafermap.getTotalDies();
	}

	public Integer getTestedDies() {
		return internalWafermap.getTestedDies();
	}

	public Integer getPassedDies() {
		return internalWafermap.getPassedDies();
	}
}
