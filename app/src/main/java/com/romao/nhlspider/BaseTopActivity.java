package com.romao.nhlspider;

import com.romao.nhlspider.ui.common.Presenter;
import com.romao.nhlspider.ui.common.PresenterView;

/**
 * Created by rpiontkovsky on 1/4/2017.
 */

public abstract class BaseTopActivity<E extends Presenter, T extends PresenterView> extends PresenterActivity<E, T> {
}
