package com.romao.nhlspider.ui.common;

import android.support.annotation.StringRes;
import android.view.View;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public interface PresenterView<E extends Presenter> {

    void setPresenter(E presenter);

    E getPresenter();

    View asView();

    void showErrorToast(String text);

    void showErrorToast(@StringRes int textResId);
}
