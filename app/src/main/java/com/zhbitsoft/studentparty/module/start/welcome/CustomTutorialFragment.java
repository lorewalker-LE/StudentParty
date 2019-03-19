package com.zhbitsoft.studentparty.module.start.welcome;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cleveroad.slidingtutorial.Direction;
import com.cleveroad.slidingtutorial.IndicatorOptions;
import com.cleveroad.slidingtutorial.OnTutorialPageChangeListener;
import com.cleveroad.slidingtutorial.PageOptions;
import com.cleveroad.slidingtutorial.TransformItem;
import com.cleveroad.slidingtutorial.TutorialFragment;
import com.cleveroad.slidingtutorial.TutorialOptions;
import com.cleveroad.slidingtutorial.TutorialPageOptionsProvider;
import com.cleveroad.slidingtutorial.TutorialPageProvider;
import com.cleveroad.slidingtutorial.TutorialSupportFragment;
import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.module.start.welcome.renderer.DrawableRenderer;

public class CustomTutorialFragment extends TutorialFragment
        implements OnTutorialPageChangeListener {

    private static final String TAG = "CustomTutorialFragment";
    private static final int TOTAL_PAGES = 3;
    private static final int ACTUAL_PAGES_COUNT = 3;

    private final View.OnClickListener mOnSkipClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "Skip button clicked", Toast.LENGTH_SHORT).show();
        }
    };

    private final TutorialPageOptionsProvider mTutorialPageOptionsProvider = new TutorialPageOptionsProvider() {
        @NonNull
        @Override
        public PageOptions provide(int position) {
            @LayoutRes int pageLayoutResId;
            TransformItem[] tutorialItems;
            position %= ACTUAL_PAGES_COUNT;
            switch (position) {
                case 0: {
                    pageLayoutResId = R.layout.fragmentpage_first;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.ivFirstImage, Direction.LEFT_TO_RIGHT, 0.50f),
                    };
                    break;
                }
                case 1: {
                    pageLayoutResId = R.layout.fragmentpage_third;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.ivFirstImage, Direction.RIGHT_TO_LEFT, 0.50f),
                    };
                    break;
                }
                case 2: {
                    pageLayoutResId = R.layout.fragmentpage_second;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.ivFirstImage, Direction.RIGHT_TO_LEFT, 0.7f),
                    };
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unknown position: " + position);
                }
            }

            return PageOptions.create(pageLayoutResId, position, tutorialItems);
        }
    };

    private final TutorialPageProvider<Fragment> mTutorialPageProvider = new TutorialPageProvider<Fragment>() {
        @NonNull
        @Override
        public Fragment providePage(int position) {
            position %= ACTUAL_PAGES_COUNT;
            switch (position) {
                case 0:
                    return new FirstCustomPageFragment();
                case 1:
                    return new SecondCustomPageFragment();
                case 2:
                    return new ThirdCustomPageFragment();
                default:
                    throw new IllegalArgumentException("Unknown position: " + position);
            }
        }
    };

    private int[] pagesColors;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (pagesColors == null) {
            pagesColors = new int[]{
                    ContextCompat.getColor(getActivity(), R.color.fPage1Blue),
                    ContextCompat.getColor(getActivity(), R.color.fPage2Grey),
                    ContextCompat.getColor(getActivity(), R.color.fPage3Green)
            };
        }
        addOnTutorialPageChangeListener(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.custom_tutorial_layout;
    }

    @Override
    protected int getIndicatorResId() {
        return R.id.indicatorCustom;
    }

    @Override
    protected int getSeparatorResId() {
        return R.id.separatorCustom;
    }

    @Override
    protected int getButtonSkipResId() {
        return R.id.tvSkipCustom;
    }

    @Override
    protected int getViewPagerResId() {
        return R.id.viewPagerCustom;
    }

    @Override
    protected TutorialOptions provideTutorialOptions() {
        return newTutorialOptionsBuilder(getActivity())
                .setUseAutoRemoveTutorialFragment(true)
                .setPagesColors(pagesColors)
                .setPagesCount(TOTAL_PAGES)
                //.setTutorialPageProvider(mTutorialPageOptionsProvider)
                .setIndicatorOptions(IndicatorOptions.newBuilder(getActivity())
                        .setElementSizeRes(R.dimen.indicator_size)
                        .setElementSpacingRes(R.dimen.indicator_spacing)
                        .setElementColorRes(android.R.color.darker_gray)
                        .setSelectedElementColor(Color.LTGRAY)
                        .setRenderer(DrawableRenderer.create(getActivity()))
                        .build())
                .setTutorialPageProvider(mTutorialPageProvider)
                .setOnSkipClickListener(mOnSkipClickListener)
                .build();
    }

    @Override
    public void onPageChanged(int position) {
        Log.i(TAG, "onPageChanged: position = " + position);
        if (position == TutorialSupportFragment.EMPTY_FRAGMENT_POSITION) {
            Log.i(TAG, "onPageChanged: Empty fragment is visible");
        }
    }
}
