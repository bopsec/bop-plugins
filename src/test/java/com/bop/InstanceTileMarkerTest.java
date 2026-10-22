package com.bop;

import java.awt.Color;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InstanceTileMarkerTest
{
    @Test
    public void parsesMultipleMarkersAndColors()
    {
        List<InstanceTileMarker> markers = InstanceTileMarker.parse(
                "12, 34, #FF0000; 20,21,#00ff00\n5,6,#0000FF");

        assertEquals(3, markers.size());
        assertEquals(12, markers.get(0).x);
        assertEquals(34, markers.get(0).y);
        assertEquals(Color.RED, markers.get(0).color);
        assertEquals(Color.GREEN, markers.get(1).color);
        assertEquals(Color.BLUE, markers.get(2).color);
    }

    @Test
    public void skipsMalformedMarkersWithoutLosingValidOnes()
    {
        List<InstanceTileMarker> markers = InstanceTileMarker.parse(
                "bad; 1,2,#12345; 3,4,#112233; 999999999999,5,#FFFFFF");

        assertEquals(1, markers.size());
        assertEquals(3, markers.get(0).x);
        assertEquals(4, markers.get(0).y);
        assertEquals(new Color(0x112233), markers.get(0).color);
    }
}
