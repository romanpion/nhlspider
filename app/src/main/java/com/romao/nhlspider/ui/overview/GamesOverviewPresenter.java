package com.romao.nhlspider.ui.overview;

import com.romao.nhlspider.R;
import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.storage.LocalStorage;
import com.romao.nhlspider.ui.common.AbstractPresenter;
import com.romao.nhlspider.util.DateUtil;
import com.romao.nhlspider.util.OkResult;

import org.joda.time.DateTime;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class GamesOverviewPresenter extends AbstractPresenter<GameOverviewView> {

    private final LocalStorage storage;
    private DateTime startDate;
    private DateTime endDate;
    private int selectedPage = 0;
    private boolean gamesLoaded = false;

    public GamesOverviewPresenter(LocalStorage storage) {
        this.storage = storage;
    }

    @Override
    protected void onViewDetached() {
        // clean up if needed
    }

    @Override
    protected void onViewAttached() {
        // init/update model
        // pass data to view
        if (!gamesLoaded) {
            loadGames();
        }
    }

    private void loadGames() {
        Observable.create(new Observable.OnSubscribe<OkResult>() {
            @Override
            public void call(Subscriber<? super OkResult> subscriber) {
                subscriber.onNext(OkResult.INSTANCE);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<OkResult>() {
                    @Override
                    public void call(OkResult okResult) {
                        if (view != null) {
                            List<Game> games = storage.games().readAll();
                            startDate = DateUtil.toStartOfDay(games.get(0).getDate());
                            endDate = DateUtil.toStartOfDay(games.get(games.size() - 1).getDate());
                            view.setRange(startDate, endDate);

                            DateTime now = DateUtil.toStartOfDay(DateTime.now());
                            int currentPage = DateUtil.daysBetween(startDate, now);
                            view.setCurrentItem(currentPage);
                            gamesLoaded = true;
                        }
                    }
                });
    }

    public void onPageSelected(int position) {
        selectedPage = position;
        DateTime date = startDate.plusDays(position);
        view.setCurrentDate(DateUtil.toShortString(date));
        view.setPreviousEnabled(position > 0);
        view.setNextEnabled(date.isBefore(endDate));
    }

    public void onNextClicked() {
        view.setCurrentItem(selectedPage + 1);
    }

    public void onPreviousClicked() {
        view.setCurrentItem(selectedPage - 1);
    }

    public void onDateClicked() {
        DateTime date = startDate.plusDays(selectedPage);
        // months in joda time start from 1, but in android date picker first month is 0
        view.showDatePickerDialog(date.year().get(), date. monthOfYear().get() - 1, date.dayOfMonth().get());
    }

    public void onDatePicked(int year, int month, int day) {
        DateTime normalizedDate = DateUtil.toStartOfDay(DateTime.now()).withDate(year, month + 1, day);
        if (normalizedDate.isAfter(endDate)) {
            view.showErrorToast(R.string.i18n_message_date_out_of_range);
            return;
        }

        int position = DateUtil.daysBetween(startDate, normalizedDate);
        view.setCurrentItem(position);
    }
}
