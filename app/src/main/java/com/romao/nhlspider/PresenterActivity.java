package com.romao.nhlspider;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.romao.nhlspider.ui.common.Presenter;
import com.romao.nhlspider.ui.common.PresenterView;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public abstract  class PresenterActivity<E extends Presenter, T extends PresenterView> extends ToolbarActivity {

    protected E presenter;
    protected T view;

    private void initView() {
        this.view = createView();
        setContentView(view.asView());
    }

    protected abstract T createView();

    private void initPresenter() {
        this.presenter = createPresenter();
    }

    protected abstract E createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        initView();
        initPresenter();
    }

    private void bind(E presenter, T view) {
        presenter.attach(view);
        view.setPresenter(presenter);
    }

    private void unbind(E presenter, T view) {
        presenter.detach(view);
        view.setPresenter(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bind(presenter, view);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbind(presenter, view);
    }
}
