/*
 * Copyright 2015 Carlos Parker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.unique.yyz.tilelistview.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;


/**
 * A ListView to display all its items.
 */
public class TileListView extends ListView {

    public TileListView(Context context) {
        super(context);
    }

    public TileListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TileListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);

        //anonymous object ensures this cannot be removed.
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                fitHeight();
            }
        });
        fitHeight();
    }

    /**
     * adjust height of the view to contain all the items
     */
    private void fitHeight() {
        ListAdapter adapter = getAdapter();

        int heightSum = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, this);
            listItem.measure(0, 0);
            heightSum += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = this.getLayoutParams();
        int dividerHeights = getDividerHeight() * (adapter.getCount() == 0 ? 0 : adapter.getCount() - 1);
        params.height = heightSum + dividerHeights;
        setLayoutParams(params);
    }
}
