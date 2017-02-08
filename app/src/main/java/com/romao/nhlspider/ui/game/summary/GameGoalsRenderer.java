package com.romao.nhlspider.ui.game.summary;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.romao.nhlspider.model.Goal;
import com.romao.nhlspider.model.enums.Period;
import com.romao.nhlspider.model.enums.Strength;
import com.romao.nhlspider.util.Goals;
import com.romao.nhlspider.util.TeamImageResolver;

import java.util.List;

/**
 * Created by roman on 13/01/2017.
 */

public class GameGoalsRenderer {

    private final LinearLayout viewGoalsList;

    public GameGoalsRenderer(LinearLayout layout) {
        viewGoalsList = layout;
    }

    public void applyGoals(List<Goal> goals) {
        viewGoalsList.removeAllViews();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        for (Goal goal : goals) {
            GameGoalView view = new GameGoalView(viewGoalsList.getContext());
            viewGoalsList.addView(view, layoutParams);

            view.setTeamImage(TeamImageResolver.getTeamLogoResource(goal.getTeam()));
            view.setGoalScorer(Goals.getGoalScorerString(goal));

            if (goal.getPeriod() != Period.SHOOTOUT) {
                view.setAssists(Goals.getAssistsString(goal));
                view.setTextTime(Goals.getGoalTime(goal));

                view.setIsPp(goal.getStrength() == Strength.POWER_PLAY);
                view.setIsSh(goal.getStrength() == Strength.SHORTHANDED);
                view.setIsEn(goal.isEmptyNetter());
            } else {
                view.setTextTime("SO");
                view.setAssists("-");
            }
        }
    }
}
