package com.romao.nhlspider.ui.common;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public interface Presenter<E extends PresenterView> {

    void attach(E view);
    void detach(E view);
}
