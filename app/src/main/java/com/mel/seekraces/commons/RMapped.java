package com.mel.seekraces.commons;

import com.mel.seekraces.R;

/**
 * Created by moha on 18/01/17.
 */

public enum RMapped {
    ITEM_RACES_PUBLISHED(R.id.listEventsPublished),
    ITEM_RACES_MY_PUBLISHED(R.id.listMyEventsPublished),
    ITEM_RACES_FAVORITES(R.id.listEventsFavorites),
    ITEM_RACES_PREVIOUS(R.id.listPreviousEvents);

    private int resource_id;

    RMapped(int id) {
        resource_id = id;
    }

    public int getValue() {
        return resource_id;
    }
}
