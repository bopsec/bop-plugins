package com.bop;

import net.runelite.api.coords.WorldPoint;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InstanceSlotCoordsTest
{
    @Test
    public void marksPositionWithinCurrentSmallSlot()
    {
        InstanceSlotCoords.Slot slot = InstanceSlotCoords.from(10300, 70);

        assertEquals(InstanceSlotCoords.Type.SMALL, slot.type);
        assertEquals(12, slot.localX);
        assertEquals(22, slot.localY);
        assertEquals(new WorldPoint(10352, 112, 0), slot.tileAt(64, 64, 0));
        assertNull(slot.tileAt(192, 64, 0));
    }

    @Test
    public void wrapsSlotYAtWorldBoundary()
    {
        InstanceSlotCoords.Slot slot = InstanceSlotCoords.from(10288, 10);

        assertEquals(new WorldPoint(10352, 48, 0), slot.tileAt(64, 64, 0));
    }
}
