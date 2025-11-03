package com.lcv.elverapi.apis.hypixelplayer.skyblock.util;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;

public class SBContainer implements Iterable<SBItem>
{
    private final SBItem[] items;

    public SBContainer(SBItem[] items)
    {
        this.items = items;
    }

    public SBItem get(int index)
    {
        return items[index];
    }

    public int size()
    {
        return items.length;
    }

    public SBItem[] toArray()
    {
        return Arrays.copyOf(items, items.length);
    }

    @NotNull
    @Override
    public Iterator<SBItem> iterator()
    {
        return Arrays.asList(items).iterator();
    }

    @Override
    public String toString() {
        return Arrays.toString(items);
    }
}
