package com.zhbitsoft.studentparty.module.start.welcome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.cleveroad.slidingtutorial.Direction;
import com.cleveroad.slidingtutorial.IndicatorOptions;
import com.cleveroad.slidingtutorial.PageOptions;
import com.cleveroad.slidingtutorial.TransformItem;
import com.cleveroad.slidingtutorial.TutorialFragment;
import com.cleveroad.slidingtutorial.TutorialOptions;
import com.cleveroad.slidingtutorial.TutorialPageOptionsProvider;
import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.config.Const;
import com.zhbitsoft.studentparty.main.Main2Activity;
import com.zhbitsoft.studentparty.module.login.LoginActivity;
import com.zhbitsoft.studentparty.utils.SPUtils;

public class WelcomeActivity extends Activity implements View.OnClickListener {

    private static final int TOTAL_PAGES = 3;
    private static final int ACTUAL_PAGES_COUNT = 3;
    private int[] mPagesColors;

    public static void start(Context context) {
        SPUtils.put(context, Const.FIRST_OPEN, true);
        context.startActivity(new Intent(context, WelcomeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bRetry).setOnClickListener(this);
        mPagesColors = new int[]{
                ContextCompat.getColor(this, R.color.fPage1Blue),
                ContextCompat.getColor(this, R.color.fPage2Grey),
                ContextCompat.getColor(this, R.color.fPage3Green),
        };
        if (savedInstanceState == null) {
            replaceTutorialFragment();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bRetry:
                replaceTutorialFragment();
                break;
        }
    }

    public void replaceTutorialFragment() {
        final IndicatorOptions indicatorOptions = IndicatorOptions.newBuilder(this)
                .build();
        final TutorialOptions tutorialOptions = TutorialFragment.newTutorialOptionsBuilder(this)
                .setUseAutoRemoveTutorialFragment(true)
                .setUseInfiniteScroll(true)
                .setPagesColors(mPagesColors)
                .setPagesCount(TOTAL_PAGES)
                .setIndicatorOptions(indicatorOptions)
                .setTutorialPageProvider(new TutorialPagesProvider())
                .setOnSkipClickListener(new OnSkipClickListener(this))
                .build();
        final TutorialFragment tutorialFragment = TutorialFragment.newInstance(tutorialOptions);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_welcome, tutorialFragment)
                .commit();
    }

    private static final class TutorialPagesProvider implements TutorialPageOptionsProvider {

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
                            TransformItem.create(R.id.ivFirstImage, Direction.LEFT_TO_RIGHT, 0.20f),
                    };
                    break;
                }
                case 1: {
                    pageLayoutResId = R.layout.fragmentpage_third;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.ivFirstImage, Direction.RIGHT_TO_LEFT, 0.20f),
                    };
                    break;
                }
                case 2: {
                    pageLayoutResId = R.layout.fragmentpage_second;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.ivFirstImage, Direction.RIGHT_TO_LEFT, 0.2f),
                    };
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unknown position: " + position);
                }
            }

            return PageOptions.create(pageLayoutResId, position, tutorialItems);
        }
    }

    private  final class OnSkipClickListener implements View.OnClickListener {

        @NonNull
        private final Context mContext;

        OnSkipClickListener(@NonNull Context context) {
            mContext = context.getApplicationContext();
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
}
