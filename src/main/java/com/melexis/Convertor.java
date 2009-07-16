/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis;

/**
 *
 * @author brh
 */
public interface Convertor {

	/**
	 * convert a wafermap
	 * @return a byte[] containing the converted map
	 * @throws exception an Exception occured when converting the wafermap
	 
	 */
	byte[] convert() throws Exception;
}
