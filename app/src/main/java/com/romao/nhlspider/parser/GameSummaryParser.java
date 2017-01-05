package com.romao.nhlspider.parser;

import com.romao.nhlspider.model.GameSummary;
import com.romao.nhlspider.model.Period;

import org.jdom2.Document;
import org.jdom2.Element;

import java.util.List;
import java.util.StringTokenizer;

import timber.log.Timber;

/**
 * Created by rpiontkovsky on 1/3/2017.
 */

public class GameSummaryParser extends DocParser {

    public GameSummaryParser(Document doc) {
        super(doc);
    }

    public GameSummary parse() {
        if (this.document == null) {
            throw new NullPointerException("Document should not be null");
        }

        long start = System.currentTimeMillis();
        GameSummary gameSummary = new GameSummary();
        gameSummary.setAttendance(getAttendance());
        gameSummary.setHomeGoals(getHomeGoals());
        gameSummary.setAwayGoals(getAwayGoals());
        gameSummary.setArenaName(getArenaName());
        gameSummary.setHomeShots(getHomeShots());
        gameSummary.setHomePenalties(getHomePenalties());
        gameSummary.setHomePims(getHomePims());
        gameSummary.setHomePowerPlays(getHomePPNew());
        gameSummary.setHomePpGoals(getHomePPGoalsNew());
        gameSummary.setHomeRealGoals(getRealHomeGoals());
        gameSummary.setAwayShots(getAwayShots());
        gameSummary.setAwayPenalties(getAwayPenalties());
        gameSummary.setAwayPims(getAwayPims());
        gameSummary.setAwayPowerPlays(getVisitorPPNew());
        gameSummary.setAwayPpGoals(getVisitorPPGoalsNew());
        gameSummary.setAwayRealGoals(getRealAwayGoals());
        gameSummary.setFinalPeriod(getGameFinalPeriod());
        long finish = System.currentTimeMillis();
        Timber.v("parsing : " + (finish - start) + " ms");

        return gameSummary;
    }

    private int getHomePenalties() {
        String text = getPenaltyElement(true).getText();
        return Integer.parseInt(text.split("-")[0].trim());
    }

    private int getHomePims() {
        String text = getPenaltyElement(true).getText();
        return Integer.parseInt(text.split("-")[1].trim());
    }

    private int getAwayPenalties() {
        String text = getPenaltyElement(false).getText();
        return Integer.parseInt(text.split("-")[0].trim());
    }

    private int getAwayPims() {
        String text = getPenaltyElement(false).getText();
        return Integer.parseInt(text.split("-")[1].trim());
    }

    private Element getPenaltyElement(boolean home) {
        return selectFirst("//table[@id='PenaltySummary']/tr[2]/td/table/tr/td/table/tr[2]/td[" + (home ? 4 : 1) + "]/table/tr[1]/td[2]");
    }

    private int getAwayGoals() {
        if(this.document == null) {
            return 0;
        }
        Element visitorElem = selectFirst("//table[@id='Visitor']/tr[2]/td/table/tr/td[2]");
        int goals = 0;
        try {
            goals = Integer.parseInt(visitorElem.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return goals;
    }

    private Period getGameFinalPeriod() {
        if(this.document == null) {
            return null;
        }

        Element lastGoalPeriodElem = selectFirst("//table[@id='MainTable']/tr[4]/td/table/tr[last()]/td[2]");
        String lastGoalPeriod = lastGoalPeriodElem.getText();
        if (lastGoalPeriod.equals("SO")) {
            return Period.SHOOTOUT;
        } else if (lastGoalPeriod.equals("OT")) {
            return Period.OVERTIME;
        } else {
            return Period.REGULATION;
        }
    }

    @SuppressWarnings("unchecked")
    private int getRealAwayGoals() {
        if(this.document == null) {
            return 0;
        }
        List<Element> list = selectNodes("//table[@id='MainTable']/tr[9]/td/table/tr[2]/td/table/tr/td[1]/table/tr/td[1]");
        Element td = null;
        for(Element e : list) {
            if(e.getText().equals("TOT")) {
                td = e;
                break;
            }
        }

        String text = (((Element)td.getParent()).getChildren().get(1)).getText();
        int goals = 0;
        try {
            goals = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return goals;
    }

    @SuppressWarnings("unchecked")
    private int getAwayShots() {
        if(this.document == null) {
            return 0;
        }
        List<Element> list = selectNodes("//table[@id='MainTable']/tr[9]/td/table/tr[2]/td/table/tr/td[1]/table/tr/td[1]");
        Element td = null;
        for(Element e : list) {
            if(e.getText().equals("TOT")) {
                td = e;
                break;
            }
        }

        String text = (((Element)td.getParent()).getChildren().get(2)).getText();
        int shots = 0;
        try {
            shots = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return shots;
    }

    private int getHomeGoals() {
        if(this.document == null) {
            return 0;
        }
        Element visitorElem = selectFirst("//table[@id='Home']/tr[2]/td/table/tr/td[2]");
        int goals = 0;
        try {
            goals = Integer.parseInt(visitorElem.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return goals;
    }

    @SuppressWarnings("unchecked")
    private int getRealHomeGoals() {
        if(this.document == null) {
            return 0;
        }
        List<Element> list = selectNodes("//table[@id='MainTable']/tr[9]/td/table/tr[2]/td/table/tr/td[2]/table/tr/td[1]");
        Element td = null;
        for(Element e : list) {
            if(e.getText().equals("TOT")) {
                td = e;
                break;
            }
        }

        String text = (((Element)td.getParent()).getChildren().get(1)).getText();
        int goals = 0;
        try {
            goals = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return goals;
    }

    @SuppressWarnings("unchecked")
    private int getHomeShots() {
        if(this.document == null) {
            return 0;
        }
        List<Element> list = selectNodes("//table[@id='MainTable']/tr[9]/td/table/tr[2]/td/table/tr/td[2]/table/tr/td[1]");
        Element td = null;
        for(Element e : list) {
            if(e.getText().equals("TOT")) {
                td = e;
                break;
            }
        }

        String text = (((Element)td.getParent()).getChildren().get(2)).getText();
        int shots = 0;
        try {
            shots = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return shots;
    }

    protected String getVisitorPPSummaryString() {
        if(this.document == null) {
            return "-";
        }
        Element visitorPpElement = selectNodes("//table[@id='MainTable']/tr[11]/td/table/tr[2]/td/table/tr/td[1]/table/tr[2]/td[4]").get(0);
        return visitorPpElement.getText();
    }

    protected List<Element> getVisitorPPSummaryElems() {
        if(this.document == null) {
            return null;
        }
        List<Element> visitorPpElements = selectNodes("//table[@id='MainTable']/tr[11]/td/table/tr[2]/td/table/tr/td[1]/table/tr[2]/td");
        //List<Element> visitorPpElements = (List<Element>)this.doc.selectNodes("//table[@id='MainTable']/tr[6]/td/table/tr[1]/td/table/tr/td/table/tr[1]/td[0]/table/tr[1]/td[1]");
        return visitorPpElements;
    }

    public int getVisitorPPNew() {
        List<Element> elems = getVisitorPPSummaryElems();
        int pp = 0;
        for(Element e : elems) {
            pp += getVisitorPP(e.getText());
        }
        return pp;
    }

    public int getVisitorPP(String str) {
        String ppString = str;
        if(ppString.equals("-")) {
            return 0;
        }
        int minusIndex = ppString.indexOf("-");
        int slashIndex = ppString.indexOf("/");
        if(minusIndex<0 || slashIndex<0 || (minusIndex >= slashIndex)) {
            return 0;
        }
        return Integer.parseInt(ppString.substring(minusIndex+1, slashIndex));
    }

    public int getVisitorPPGoalsNew() {
        List<Element> elems = getVisitorPPSummaryElems();
        int pp = 0;
        for(Element e : elems) {
            pp += getVisitorPPGoals(e.getText());
        }
        return pp;
    }

    public int getVisitorPPGoals(String str) {
        String ppString = str; //getVisitorPPSummaryString();
        if(ppString.equals("-")) {
            return 0;
        }
        int minusIndex = ppString.indexOf("-");
        if(minusIndex<0) {
            return 0;
        }
        return Integer.parseInt(ppString.substring(0, minusIndex));
    }

    public int getVisitorPPTimeNew() {
        List<Element> elems = getVisitorPPSummaryElems();
        int pp = 0;
        for(Element e : elems) {
            pp += getVisitorPPTime(e.getText());
        }
        return pp;
    }

    public int getVisitorPPTime(String str) {
        String ppString = str; //getVisitorPPSummaryString();
        if(ppString.equals("-")) {
            return 0;
        }
        int slashIndex = ppString.indexOf("/");
        if(slashIndex<0) {
            return 0;
        }
        String timeString = ppString.substring(slashIndex+1);
        int minutes = Integer.parseInt(timeString.split(":")[0]);
        int seconds = Integer.parseInt(timeString.split(":")[1]);
        return minutes*60 + seconds;
    }

    public String getHomePPSummaryString() {
        if(this.document == null) {
            return "-";
        }
        Element visitorPpElement = selectNodes("//table[@id='MainTable']/tr[11]/td/table/tr[2]/td/table/tr/td[2]/table/tr[2]/td[4]").get(0);
        return visitorPpElement.getText();
    }

    public List<Element> getHomePPSummaryElems() {
        if(this.document == null) {
            return null;
        }
        List<Element> homePpElements = selectNodes("//table[@id='MainTable']/tr[11]/td/table/tr[2]/td/table/tr/td[2]/table/tr[2]/td");
        return homePpElements;
    }

    public int getHomePPNew() {
        List<Element> elems = getHomePPSummaryElems();
        int pp = 0;
        for(Element e : elems) {
            pp += getHomePP(e.getText());
        }
        return pp;
    }

    public int getHomePP(String str) {
        String ppString = str; //getHomePPSummaryString();
        if(ppString.equals("-")) {
            return 0;
        }
        int minusIndex = ppString.indexOf("-");
        int slashIndex = ppString.indexOf("/");
        if(minusIndex<0 || slashIndex<0 || (minusIndex >= slashIndex)) {
            return 0;
        }
        return Integer.parseInt(ppString.substring(minusIndex+1, slashIndex));
    }

    public int getHomePPGoalsNew() {
        List<Element> elems = getHomePPSummaryElems();
        int pp = 0;
        for(Element e : elems) {
            pp += getHomePPGoals(e.getText());
        }
        return pp;
    }

    public int getHomePPGoals(String str) {
        String ppString = str; //getHomePPSummaryString();
        if(ppString.equals("-")) {
            return 0;
        }
        int minusIndex = ppString.indexOf("-");
        if(minusIndex<0) {
            return 0;
        }
        return Integer.parseInt(ppString.substring(0, minusIndex));
    }

    public int getHomePPTimeNew() {
        List<Element> elems = getHomePPSummaryElems();
        int pp = 0;
        for(Element e : elems) {
            pp += getHomePPTime(e.getText());
        }
        return pp;
    }

    public int getHomePPTime(String str) {
        String ppString = str; //getHomePPSummaryString();
        if(ppString.equals("-")) {
            return 0;
        }
        int slashIndex = ppString.indexOf("/");
        if(slashIndex<0) {
            return 0;
        }
        String timeString = ppString.substring(slashIndex+1);
        int minutes = Integer.parseInt(timeString.split(":")[0]);
        int seconds = Integer.parseInt(timeString.split(":")[1]);
        return minutes*60 + seconds;
    }

    @SuppressWarnings("unchecked")
    private Element getGoalInfoElement(final int goalIndex) {
        if(this.document == null) {
            return null;
        }
        if(goalIndex > getHomeGoals() + getAwayGoals()) {
            return null;
        }
        List<Element> list = selectNodes("//table[@id='MainTable']/tr[4]/td/table/tr/td[1]");
        Element result = null;
        for(Element e : list) {
            try {
                int eInt = Integer.parseInt(e.getText().trim());
                if(eInt==goalIndex) {
                    result = (Element) e.getParent();
                    break;
                }
            } catch (NumberFormatException ex) {
                continue;
            }
        }
        return result;
    }

    private Integer getGoalPeriod(final int goalIndex) {
        Element elem = getGoalInfoElement(goalIndex);
        if(elem==null) {
            return null;
        }

        Element td = elem.getChildren().get(1);
        String str = td.getText().trim();
        if(str.equals("OT")) {
            return 4;
        }
        return Integer.parseInt(str);
    }

    private int getGoalTime(final int goalIndex) {
        Element elem = getGoalInfoElement(goalIndex);
        if(elem==null || getGoalPeriod(goalIndex).equals(5)) {
            return 0;
        }

        int period = getGoalPeriod(goalIndex);
        Element td = elem.getChildren().get(2);
        String timeString = td.getText();

        return ParserUtil.convertTimeStringToSeconds(timeString) + (period - 1) * 1200;
    }

    private int getAttendance() {
        if(this.document == null) {
            return 0;
        }
        Element elem = selectFirst("//table[@id='GameInfo']/tr[5]/td");
        StringTokenizer stTok = new StringTokenizer(elem.getText(), " ");
        stTok.nextToken();
        String attString = stTok.nextToken().replaceAll(",", "");
        int attendance = 0;
        try {
            attendance = Integer.parseInt(attString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return attendance;
    }

    private String getArenaName() {
        if(this.document == null) {
            return null;
        }

        Element elem = selectFirst("//table[@id='GameInfo']/tr[5]/td");
        if (elem == null) {
            return null;
        }

        StringTokenizer stTok = new StringTokenizer(elem.getText(), " ");
        if (stTok.countTokens() < 4) {
            return null;
        }

        stTok.nextToken();
        stTok.nextToken();
        stTok.nextToken();

        StringBuilder builder = new StringBuilder();
        while (stTok.hasMoreTokens()) {
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(stTok.nextToken());
        }
        return builder.toString();
    }
}
