package com.melexis;

import com.google.common.base.Function;
import com.melexis.th01.TH01WaferMap;
import com.melexis.th01.exception.Th01Exception;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Iterables.transform;
import static java.lang.String.format;
import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: brh
 * Date: Mar 11, 2010
 * Time: 9:29:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class RtwmConvertorTest extends Utils {

    private RtwmConvertor rtwmConvertor;

    private byte[] th;
    private byte[] th2;

    private byte[] expected;
    private byte[] expected2;

    @Before
    public void setUp() throws IOException, Th01Exception, InvalidWafermapException, InvalidRefdieException {
        th = readFileFromResource("WC_M18955_01_08032010_215213.th01");
        th2 = readFileFromResource("WC_B82556_10_23032010_012809.th01");

        expected = readFileFromResource("rtwm_M18955-1.example");
        expected2 = readFileFromResource("rtwm_B82556-10.example");

    }

    @Test
    public void convertToRtwm() throws Exception {
        final RtwmConvertor m18955_convertor = new RtwmConvertor(new TH01WaferMap(th));
        verifyMapsAreEqual(m18955_convertor, expected);
        final RtwmConvertor b82556_convertor = new RtwmConvertor(new TH01WaferMap(th2));
        verifyMapsAreEqual(b82556_convertor, expected2);
    }

    private static void verifyMapsAreEqual(final RtwmConvertor _rtwmConvertor,
                                           final byte[] map) throws Exception {
        final String stringValue = new String(map);
        final Byte[] expected = toByteArray(stringValue);
        final byte[] converted = _rtwmConvertor.convert();

        final String c = new String(converted).replace("\n", "");
        final Byte[] cb = toByteArray(c);

        // The length of both maps is not equal as the rtwm map has more dies then the th map.
        for (int i=0; i<cb.length; i++) {
            if (!expected[i].equals(cb[i])) {
                System.out.println(format("Die %d,  expecting %d was %d", i, expected[i], cb[i]));
            }
            assertEquals(expected[i], cb[i]);
        }
    }

    private final static Byte[] toByteArray(final String expected) {
        final String[] results = expected.split(" ");
        final byte[] bincodes = new byte[results.length];
        final List<String> resultList = Arrays.asList(results);
        List<Byte> converted = copyOf(transform(resultList, new Function<String, Byte>() {

            public Byte apply(String from) {
                try {
                    return Byte.parseByte(from);
                } catch (NumberFormatException e) {
                    return from.getBytes()[0];
                }
            }
        }));

        return converted.toArray(new Byte[results.length]);
    }
}
