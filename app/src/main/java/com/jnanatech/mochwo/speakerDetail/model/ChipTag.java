package com.jnanatech.mochwo.speakerDetail.model;


import com.plumillonforge.android.chipview.Chip;

public class ChipTag implements Chip {
    private String mName;
    private int mType = 0;

    public ChipTag(String name, int type) {
        this(name);
        mType = type;
    }

    public ChipTag(String name) {
        mName = name;
    }

    @Override
    public String getText() {
        return mName;
    }

    public int getType() {
        return mType;
    }
}
