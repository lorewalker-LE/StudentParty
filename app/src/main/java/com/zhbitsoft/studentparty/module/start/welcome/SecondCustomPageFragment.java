package com.zhbitsoft.studentparty.module.start.welcome;


import android.support.annotation.NonNull;

import com.cleveroad.slidingtutorial.Direction;
import com.cleveroad.slidingtutorial.PageFragment;
import com.cleveroad.slidingtutorial.TransformItem;
import com.zhbitsoft.studentparty.R;

public class SecondCustomPageFragment extends PageFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragmentpage_second;
    }

    @NonNull
    @Override
    protected TransformItem[] getTransformItems() {
        return new TransformItem[]{
                TransformItem.create(R.id.ivFirstImage, Direction.RIGHT_TO_LEFT, 0.2f),
        };
    }
}

