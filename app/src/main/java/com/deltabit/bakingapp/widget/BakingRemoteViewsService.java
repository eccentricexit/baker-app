package com.deltabit.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by rigel on 27/05/17.
 */

public class BakingRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return(new BakingRemoteViewsFactory(this.getApplicationContext(), intent));
    }
}