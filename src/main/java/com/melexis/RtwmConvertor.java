package com.melexis;

import com.melexis.th01.TH01WaferMap;
import com.melexis.th01.exception.Th01Exception;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: brh
 * Date: Mar 11, 2010
 * Time: 9:55:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class RtwmConvertor implements Convertor {

    private final TH01WaferMap thmap;
    private final InternalWafermap internal;

    private final static ConvertStrategy rtwmConvertStrategy = new ConvertStrategy() {

        public String convertDie(final TH01WaferMap wafermap,
                                 final InternalDie die) {
            final Integer bincode = die.getBincode();
            final Set<DieStatus> statuses = die.getDiestatuses();

            final String result;
            if (bincode == null) {
                result = "20";
            } else {

                if (bincode == 16) {
                    result = "69";
                } else if (bincode == 10) {
                    result = "65";
                } else if (bincode == 11) {
                    result = "66";
                } else if (bincode == 18) {
                    result = "73";
                } else if (bincode == 14) {
                    result = "69";
                } else {
                    result = bincode.toString();
                }
            }
            return result + " ";
        }
    };

    public RtwmConvertor(final TH01WaferMap _thmap) throws Th01Exception, InvalidWafermapException, InvalidRefdieException {
        thmap = _thmap;
        internal = new InternalWafermap(rtwmConvertStrategy, thmap, null);
    }

    public byte[] convert() throws Exception {
        final String converted = internal
                .rotateNorth()
                .convert();
        return converted.getBytes();
    }
}
