package com.melexis;

import java.util.EnumSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: brh
 * Date: Mar 22, 2010
 * Time: 1:29:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class InternalDie {

    private final Integer bincode;
    private final EnumSet<DieStatus> diestatuses;

    public InternalDie(final Integer _bincode,
                       final EnumSet<DieStatus> _diestatuses) {
        bincode = _bincode;
        diestatuses = _diestatuses;
    }

    public Integer getBincode() {
        return bincode;
    }

    public Set<DieStatus> getDiestatuses() {
        return diestatuses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InternalDie that = (InternalDie) o;

        if (bincode != null ? !bincode.equals(that.bincode) : that.bincode != null) return false;
        if (diestatuses != that.diestatuses) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bincode != null ? bincode.hashCode() : 0;
        result = 31 * result + (diestatuses != null ? diestatuses.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return bincode.toString();
    }
}
