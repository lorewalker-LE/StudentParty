package com.zhbitsoft.studentparty.module.start;

import android.os.Handler;

public class SplashInteractorImpl implements SplashInteractor {

        @Override
        public void enterInto(boolean isFirstOpen, final OnEnterIntoFinishListener listener) {
            if (!isFirstOpen){
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        listener.isFirstOpen();
                    }
                }, 2000);
            }else {
               // listener.showContentView();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        listener.isNotFirstOpen();
                    }
                }, 2000);
            }
        }

}
