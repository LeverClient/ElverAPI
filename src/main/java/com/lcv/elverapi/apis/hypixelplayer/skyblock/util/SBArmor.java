package com.lcv.elverapi.apis.hypixelplayer.skyblock.util;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;

public class SBArmor implements Iterable<SBItem>
{
    private final SBItem[] armor;

    public SBArmor(SBItem[] armor)
    {
        this.armor = armor;
    }

    public SBItem get(int index)
    {
        return armor[index];
    }
    public SBItem getHelmet()
    {
        return armor[0];
    }
    public SBItem getChestplate()
    {
        return armor[1];
    }
    public SBItem getLeggings()
    {
        return armor[2];
    }
    public SBItem getBoots()
    {
        return armor[3];
    }

    public SBItem[] toArray()
    {
        return Arrays.copyOf(armor, armor.length);
    }

    @NotNull
    @Override
    public Iterator<SBItem> iterator() {
        return Arrays.asList(armor).iterator();
    }

    @Override
    public String toString()
    {
        return Arrays.toString(armor);
    }
}
