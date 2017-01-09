package com.romao.nhlspider.parser;

import com.romao.nhlspider.model.Goal;
import com.romao.nhlspider.model.enums.Period;
import com.romao.nhlspider.model.enums.Strength;
import com.romao.nhlspider.model.enums.Team;

import org.jdom2.Element;

/**
 * Created by roman on 09/01/2017.
 */

public class GoalRowParser {

    public Goal parse(Element elem) {
        if (elem == null) {
            return null;
        }

        String goalOrderNumStr = elem.getChildren().get(0).getText();
        if (goalOrderNumStr.equals("-")) {
            return null;
        }

        Goal goal = new Goal();

        String periodStr = elem.getChildren().get(1).getText();
        Period period = Period.getByStringCode(periodStr);
        goal.setPeriod(period);

        String[] parts = null;

        if (period != Period.SHOOTOUT) {
            String timeStr = elem.getChildren().get(2).getText();
            int time = GameTimeFormat.convertToSeconds(timeStr);
            goal.setTime(time);

            String strengthStr = elem.getChildren().get(3).getText();
            parts = strengthStr.split("-");
            Strength strength = Strength.getByShortCode(parts[0]);
            if (parts.length > 1) {
                goal.setEmptyNetter(parts[1].equals("EN"));
                goal.setPenaltyShot(parts[1].equals("PS"));
            }
            goal.setStrength(strength);
        }

        String teamStr = elem.getChildren().get(4).getText();
        Team team = Team.getByShortName(teamStr);
        goal.setTeam(team);

        String scorerStr = elem.getChildren().get(5).getText();
        parts = scorerStr.split("[()]");
        goal.setScorer(parts[0]);
        if (period != Period.SHOOTOUT) {
            goal.setGoalNumber(Integer.parseInt(parts[1]));
        }

        String primaryAssistStr = elem.getChildren().get(6).getText();
        if (primaryAssistStr.trim().equals("") || primaryAssistStr.equals("unassisted")) {
            // DO NOTHING goal is unassisted
        } else {
            parts = primaryAssistStr.split("[()]");
            goal.setPrimaryAssist(parts[0]);
            goal.setPrimaryAssistNumber(Integer.parseInt(parts[1]));
        }

        String secondaryAssistStr = elem.getChildren().get(7).getText();
        if (secondaryAssistStr.trim().equals("") || secondaryAssistStr.equals("unassisted")) {
            // DO NOTHING goal is unassisted
        } else {
            parts = secondaryAssistStr.split("[()]");
            goal.setSecondaryAssist(parts[0]);
            goal.setSecondaryAssistNumber(Integer.parseInt(parts[1]));
        }

        return goal;
    }
}
