package com.romao.nhlspider.ui.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public abstract class AbstractPresenterView<E extends Presenter> extends FrameLayout implements PresenterView<E> {

    protected E presenter;

    public AbstractPresenterView(Context context) {
        super(context);
    }

    public AbstractPresenterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbstractPresenterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setPresenter(E presenter) {
        this.presenter = presenter;
    }

    public E getPresenter() {
        return presenter;
    }

    @Override
    public View asView() {
        return this;
    }
}
