package com.tyrese.library;

/**
 * Created by Tyrese on 2016/5/17.
 */
public class BaseItem {
    public static final int TYPE_FOOTER = 2;
    public static final int TYPE_ITEM = 1;

    protected int type;

    public int getCustomItemType() {
        return type;
    }
}
