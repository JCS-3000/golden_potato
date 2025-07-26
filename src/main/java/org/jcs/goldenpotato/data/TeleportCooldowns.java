package org.jcs.goldenpotato.data;

import net.minecraft.nbt.CompoundTag;

public class TeleportCooldowns {
    private long lastDeathTp = 0L;
    private long lastBedTp = 0L;
    private long lastRandomTp = 0L;

    public long getLastDeathTp() {
        return lastDeathTp;
    }

    public void setLastDeathTp(long time) {
        this.lastDeathTp = time;
    }

    public long getLastBedTp() {
        return lastBedTp;
    }

    public void setLastBedTp(long time) {
        this.lastBedTp = time;
    }

    public long getLastRandomTp() {
        return lastRandomTp;
    }

    public void setLastRandomTp(long time) {
        this.lastRandomTp = time;
    }

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putLong("LastDeathTp", lastDeathTp);
        tag.putLong("LastBedTp", lastBedTp);
        tag.putLong("LastRandomTp", lastRandomTp);
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        lastDeathTp = tag.getLong("LastDeathTp");
        lastBedTp = tag.getLong("LastBedTp");
        lastRandomTp = tag.getLong("LastRandomTp");
    }
}
