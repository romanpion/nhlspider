package com.romao.nhlspider.ui.common;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public abstract class AbstractPresenter<E extends PresenterView> implements Presenter<E> {

    protected E view;

    @Override
    public final void attach(E view) {
        this.view = view;
        onViewAttached();
    }

    @Override
    public final void detach(E view) {
        this.view = null;
        onViewDetached();
    }

    protected abstract void onViewDetached();

    protected abstract void onViewAttached();
}
