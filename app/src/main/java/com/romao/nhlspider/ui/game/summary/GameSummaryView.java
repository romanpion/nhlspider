package com.romao.nhlspider.ui.game.summary;

import android.content.Context;

import com.romao.nhlspider.R;
import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.ui.common.AbstractPresenterView;
import com.romao.nhlspider.ui.view.GameCardView;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class GameSummaryView extends AbstractPresenterView<GameSummaryPresenter> {

    private GameCardView viewGameCard;

    public GameSummaryView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_game_summary, this);
        viewGameCard = (GameCardView) findViewById(R.id.viewGameCard);
    }

    public void setGame(Game game) {
        viewGameCard.applyGame(game);
    }
}
