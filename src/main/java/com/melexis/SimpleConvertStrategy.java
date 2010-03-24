package com.melexis;

import com.melexis.th01.TH01WaferMap;

import java.util.Set;

import static java.lang.String.valueOf;

/**
 * Created by IntelliJ IDEA.
 * User: brh
 * Date: Mar 22, 2010
 * Time: 4:24:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleConvertStrategy implements ConvertStrategy {

    private final char pass;
    private final char fail;
    private final char none;
    private final char refdie;

    public SimpleConvertStrategy(char _pass, char _fail, char _none, char _refdie) {
        pass = _pass;
        fail = _fail;
        none = _none;
        refdie = _refdie;
    }

    public String convertDie(final TH01WaferMap wafermap,
                             final InternalDie die) {
        if (die == null) {
            return valueOf(none);
        }

        final Set<DieStatus> statuses = die.getDiestatuses();

        if (statuses.contains(DieStatus.REFDIE)) {
            return valueOf(refdie);
        }

        if (statuses.contains(DieStatus.FAIL)) {
            return valueOf(fail);
        }

        if (statuses.contains(DieStatus.PASS)) {
            return valueOf(pass);
        }

        return valueOf(none);
    }
}
