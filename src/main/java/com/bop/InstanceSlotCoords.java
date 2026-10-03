package com.bop;

import net.runelite.api.coords.WorldPoint;

final class InstanceSlotCoords
{
    private static final int Y_WRAP = 16384;

    enum Type
    {
        LARGE(6528, 384, 152, (9984 - 6528) / 384 + 1),
        SMALL(10288, 192, 48, (13936 - 10288) / 192 + 1),
        BOAT(14144, 128, 64, (16192 - 14144) / 128 + 1);

        private final int firstX;
        private final int step;
        private final int offsetY;
        private final int columns;

        Type(int firstX, int step, int offsetY, int columns)
        {
            this.firstX = firstX;
            this.step = step;
            this.offsetY = offsetY;
            this.columns = columns;
        }
    }

    static final class Slot
    {
        final Type type;
        final int index;
        final int localX;
        final int localY;
        private final int originX;
        private final int originY;

        private Slot(Type type, int index, int localX, int localY, int originX, int originY)
        {
            this.type = type;
            this.index = index;
            this.localX = localX;
            this.localY = localY;
            this.originX = originX;
            this.originY = originY;
        }

        WorldPoint tileAt(int x, int y, int plane)
        {
            if (x < 0 || x >= type.step || y < 0 || y >= type.step)
            {
                return null;
            }

            return new WorldPoint(originX + x, Math.floorMod(originY + y, Y_WRAP), plane);
        }
    }

    static Slot from(int x, int y)
    {
        Type type;
        if (x >= Type.BOAT.firstX)
        {
            type = Type.BOAT;
        }
        else if (x >= Type.SMALL.firstX)
        {
            type = Type.SMALL;
        }
        else if (x >= Type.LARGE.firstX)
        {
            type = Type.LARGE;
        }
        else
        {
            return null;
        }

        int column = (x - type.firstX) / type.step;
        int originX = type.firstX + column * type.step;
        int localX = x - originX;

        int row = Math.floorMod(y - type.offsetY, Y_WRAP) / type.step;
        int originY = Math.floorMod(type.offsetY + row * type.step, Y_WRAP);
        int localY = Math.floorMod(y - originY, Y_WRAP);

        return new Slot(type, row * type.columns + column, localX, localY, originX, originY);
    }

    private InstanceSlotCoords()
    {
    }
}
