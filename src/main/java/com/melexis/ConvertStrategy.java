package com.melexis;

import com.melexis.th01.TH01WaferMap;

/**
 * Created by IntelliJ IDEA.
 * User: brh
 * Date: Mar 22, 2010
 * Time: 2:01:43 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ConvertStrategy {

    /**
     * Convert a die to the partner format.
     * @param wafermap the th01 wafermap.
     * @param die the die result.
     * @return the result.
     */
    String convertDie(final TH01WaferMap wafermap,
                      final InternalDie die);
}
