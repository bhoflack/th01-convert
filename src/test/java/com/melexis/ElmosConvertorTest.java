package com.melexis;

import com.melexis.th01.TH01WaferMap;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import static com.google.common.collect.ImmutableMap.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: brh
 * Date: Dec 29, 2009
 * Time: 1:46:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class ElmosConvertorTest extends AbstractTestUtils {

    private final static Map<String, Die[]> EXAMPLES = 
            of(
//                "A99394-25-A0", new Die[] {},
                "A99394-12-A0", new Die[] {});

    @Test
    public void testConvert() throws Exception {
        for (String example : EXAMPLES.keySet()) {
			final String thmap = String.format("elmos_%s.th01", example);
			final String output = String.format("elmos_%s.example", example);
			final TH01WaferMap t = new TH01WaferMap(readFileFromResource(thmap));
			final Die[] refdies = EXAMPLES.get(example);
			ElmosAPConvertor elmosAPConvertor = new ElmosAPConvertor.Builder(t).setRefdies(refdies).build();


			assertEquals(new String(readFileFromResource(output))
                    , new String(elmosAPConvertor.convert()));
		}
    }

}
