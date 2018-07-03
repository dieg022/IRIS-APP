package com.nexwrfc.iris.iris.ListView;

/**
 * Created by diego on 14/03/2018.
 */

public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
