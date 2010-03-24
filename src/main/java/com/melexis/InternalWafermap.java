/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melexis;

import com.melexis.th01.TH01Die;
import com.melexis.th01.TH01WaferMap;
import com.melexis.th01.exception.Th01Exception;

import java.util.*;

import static java.lang.String.format;
import static java.util.EnumSet.copyOf;
import static java.util.EnumSet.of;

/**
 * @author brh
 */
public class InternalWafermap {

    private final TH01WaferMap th01wafermap;
    private final ConvertStrategy convertStrategy;

    private final InternalDie[][] wafermap;

    private final Flat flat;

    public final int rows;
    public final int cols;

    private final static Map<Flat, Flat> FLATS = new HashMap<Flat, Flat>() {
        {
            put(Flat.NORTH, Flat.EAST);
            put(Flat.EAST, Flat.SOUTH);
            put(Flat.SOUTH, Flat.WEST);
            put(Flat.WEST, Flat.NORTH);
        }
    };

    public InternalWafermap(final ConvertStrategy _convertStrategy,
                            final TH01WaferMap _th01Wafermap,
                            final Die[] _refdies) throws Th01Exception, InvalidWafermapException, InvalidRefdieException {
        th01wafermap = _th01Wafermap;
        convertStrategy = _convertStrategy;
        // Calculate our own max rows and columns
        // as our internal representation is not
        // dependent of the orientation.
        rows = _th01Wafermap.getMaxY() - _th01Wafermap.getMinY() + 1;
        cols = _th01Wafermap.getMaxX() - _th01Wafermap.getMinX() + 1;

        switch(_th01Wafermap.getFlat()) {
            case 'N':
                flat = Flat.NORTH;
                break;
            case 'E':
                flat = Flat.EAST;
                break;
            case 'S':
                flat = Flat.SOUTH;
                break;
            default:
                flat = Flat.WEST;
        }
        
        wafermap = new InternalDie[rows][cols];
        initMap(_th01Wafermap, _refdies, rows, cols);
    }

    protected InternalWafermap(final TH01WaferMap _th01waferMap,
                               final ConvertStrategy _convertStrategy,
                               final InternalDie[][] _wafermap,
                               final Flat _flat) {
        th01wafermap = _th01waferMap;
        convertStrategy = _convertStrategy;

        wafermap = _wafermap;
        rows = _wafermap.length;
        cols = (_wafermap.length > 0) ? _wafermap[0].length : 0;
        flat = _flat;
    }

    private void initMap(TH01WaferMap th01Wafermap, Die[] refdies, final int rows, final int cols)
            throws Th01Exception, InvalidWafermapException, InvalidRefdieException {

        for (int y = 0; y < rows; y++) {
            wafermap[y] = new InternalDie[cols];
            for (int x = 0; x < cols; x++) {
                wafermap[y][x] = new InternalDie(null, of(DieStatus.NONE));
            }
        }

        for (TH01Die d : th01Wafermap.getDies()) {
            Die id = null;
            try {
                id = new Die.Builder(d).setTh01Wafermap(th01Wafermap).build().toInternal();
                wafermap[id.getY()][id.getX()] = dieresult(th01Wafermap, d);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new InvalidWafermapException(String.format(
                        "The wafermap contains a die %s which is outside the wafermap. " +
                                "Regions for x: [%d,%d] y: [%d,%d]."
                        , d, th01Wafermap.getMinX(), th01Wafermap.getMaxX()
                        , th01Wafermap.getMinY(), th01Wafermap.getMaxY()));
            }
        }

        if (refdies != null) {
            for (Die refdie : refdies) {
                Die r = null;
                try {
                    r = new Die.Builder(refdie).setTh01Wafermap(th01Wafermap).build().toInternalRefdie();
                    wafermap[r.getY()][r.getX()] = new InternalDie(null, of(DieStatus.REFDIE));
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(format("Wafermap boundaries x: %s %s y: %s %s", th01Wafermap.getMinX(), th01Wafermap.getMaxX(), th01Wafermap.getMinY(), th01Wafermap.getMaxY()));
                    System.out.println(format("Die %s", r));
                    throw new InvalidRefdieException(String.format("The coordinates of refdie %s are outside the wafermap.  Please correct!", refdie));
                }
            }
        }
    }

    private static InternalDie dieresult(final TH01WaferMap wafermap,
                                         final TH01Die die) throws Th01Exception {
        final int bincode = (int) die.getBincode();

        final Set<DieStatus> statuses = new HashSet<DieStatus>();
        final DieStatus passStatus = wafermap.isPassCat(bincode) ? DieStatus.PASS : DieStatus.FAIL;
        statuses.add(passStatus);
        if (die.getStatus().equals(com.melexis.th01.DieStatus.INK_ONLY)) {
            statuses.add(DieStatus.INKED);
        }
        
        if (die.getStatus().equals(com.melexis.th01.DieStatus.SKIPPED)) {
            statuses.add(DieStatus.SKIPPED);
        }

        if (die.getStatus().equals(com.melexis.th01.DieStatus.INVALID)) {
            statuses.add(DieStatus.INVALID);
        }
        return new InternalDie(bincode, copyOf(statuses));
    }

    public InternalWafermap rotate90Degrees() {
        final InternalDie[][] w = new InternalDie[wafermap[0].length][wafermap.length];
        final Flat newFlat = FLATS.get(flat);

        for (int y = 0; y < wafermap.length; y++) {
            for (int x = 0; x < wafermap[y].length; x++) {
                w[x][wafermap.length - (y + 1)] = wafermap[y][x];
            }
        }

        return new InternalWafermap(th01wafermap, convertStrategy, w, newFlat);
    }

    public final InternalWafermap rotateSouth() {
        return rotate(this, Flat.SOUTH);
    }

    public final InternalWafermap rotateNorth() {
        return rotate(this, Flat.NORTH);
    }

    private final static InternalWafermap rotate(final InternalWafermap wafermap,
                                                 final Flat target) {
        InternalWafermap w = wafermap;
        while (w.flat != target) {
            w = w.rotate90Degrees();
        }
        return w;
    }

    public String convert() {
        final StringBuffer b = new StringBuffer();

        for (InternalDie[] line : wafermap) {
            for (InternalDie d : line) {
                b.append(convertStrategy.convertDie(th01wafermap, d));
            }
            b.append('\n');
        }

        return b.toString();
    }

    private Map<DieStatus, Integer> diecategories() {
        Map<DieStatus, Integer> map = new HashMap<DieStatus, Integer>();
        map.put(DieStatus.NONE, 0);
        map.put(DieStatus.PASS, 0);
        map.put(DieStatus.FAIL, 0);
        map.put(DieStatus.REFDIE, 0);

        for (InternalDie[] line : wafermap) {
            for (InternalDie d : line) {
                final Set<DieStatus> s = d.getDiestatuses();
                if (s == null) {
                    map.put(DieStatus.NONE, map.get(DieStatus.NONE) + 1);
                } else {
                    if (s.contains(DieStatus.NONE)) {
                        increaseCount(map, DieStatus.NONE);
                    }
                    if (s.contains(DieStatus.PASS)) {
                        increaseCount(map, DieStatus.PASS);
                    }
                    if (s.contains(DieStatus.FAIL)) {
                        increaseCount(map, DieStatus.FAIL);
                    }
                    if (s.contains(DieStatus.REFDIE)) {
                        increaseCount(map, DieStatus.REFDIE);
                    }                    
                }
            }
        }
        return map;
    }

    private static void increaseCount(final Map<DieStatus, Integer> map,
                                      final DieStatus status) {
        map.put(status, map.get(status) + 1);
    }

    public Integer getTotalDies() {
        Map<DieStatus, Integer> dies = diecategories();
        return dies.get(DieStatus.REFDIE) + dies.get(DieStatus.PASS) + dies.get(DieStatus.FAIL);
    }

    public Integer getTestedDies() {
        Map<DieStatus, Integer> dies = diecategories();
        return dies.get(DieStatus.PASS) + dies.get(DieStatus.FAIL);
    }

    public Integer getPassedDies() {
        Map<DieStatus, Integer> dies = diecategories();
        return dies.get(DieStatus.PASS);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof InternalWafermap)) {
            return false;
        }

        InternalWafermap o = (InternalWafermap) other;
        return wafermap != null && wafermap.equals(o.wafermap);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Arrays.deepHashCode(this.wafermap);
        return hash;
    }

    @Override
    public String toString() {
        return "Internal wafermap";
    }
}
