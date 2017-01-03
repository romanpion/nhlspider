package com.romao.nhlspider.parser;

import com.romao.nhlspider.model.GameSummary;

import org.jdom2.Document;
import org.jdom2.Element;

import java.util.List;
import java.util.StringTokenizer;

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

        GameSummary gameSummary = new GameSummary();
        gameSummary.setAttendance(getAttendance());
        gameSummary.setHomeGoals(getHomeGoals());
        gameSummary.setAwayGoals(getAwayGoals());
        gameSummary.setArenaName(getArenaName());

        return gameSummary;
    }

    /*private Element getVisitorPenaltyElement() {
        Element visitorPenaltyElem = selectFirst("//table[@id='PenaltySummary']/tr[2]/td/table/tr/td[1]");
        return visitorPenaltyElem;
    }

    public Collection<PenaltyType> collectPenaltyTypes() {
        Collection<PenaltyType> pTypes = new HashSet<PenaltyType>();
        final Element pElem = getVisitorPenaltyElement();
        List<?> elements = pElem.element("table").elements();

        for(int i=1; i<elements.size()-1; i++) {
            Element trElem = (Element)elements.get(i);
            String penType = ((Element)trElem.elements().get(5)).getText();
            pTypes.add(PenaltyTypeDeterminer.get(penType));
        }

        final Element hpElem = getHomePenaltyElement();
        elements = hpElem.element("table").elements();

        for(int i=1; i<elements.size()-1; i++) {
            Element trElem = (Element)elements.get(i);
            String penType = ((Element)trElem.elements().get(5)).getText();
            pTypes.add(PenaltyTypeDeterminer.get(penType));
        }
        return pTypes;
    }

    public Collection<Penalty> getVisitorPenalties(final Game game) {
        return getPenalties(game, false);
    }

    private Element getHomePenaltyElement() {
        if(this.doc == null) {
            return null;
        }
        Element homePenaltyElem = (Element)this.doc.selectNodes("//table[@id='PenaltySummary']/tr[2]/td/table/tr/td[2]").get(0);
        return homePenaltyElem;
    }

    public Collection<Penalty> getHomePenalties(final Game game) {
        return getPenalties(game, true);
    }

    private Collection<Penalty> getPenalties(final Game game, final Boolean homeTeam) {
        final Collection<Penalty> resultCollection = new ArrayList<Penalty>();
        final Team team = homeTeam ? game.getHomeTeam() : game.getVisitorTeam();
        final Team oppositeTeam = homeTeam ? game.getVisitorTeam() : game.getHomeTeam();
        final Element pElem = homeTeam ? getHomePenaltyElement() : getVisitorPenaltyElement();
        final Integer pathToNameInt = homeTeam ? 2 : 1;

        List<?> elements = pElem.element("table").elements();

        System.out.println("penalty count:" + (elements.size()-2));

        for(int i=1; i<elements.size()-1; i++) {
            Element trElem = (Element)elements.get(i);

            String periodString = ((Element)trElem.elements().get(1)).getText();
            int period = periodString.equals("OT") ? 4 : Integer.parseInt(periodString);

            String timeString = ((Element)trElem.elements().get(2)).getText();
            int fullTime = (period-1)*1200 + ParsingUtils.convertTimeStringToSeconds(timeString);

            int penaltyMinutes = Integer.parseInt(((Element)trElem.elements().get(4)).getText());

            String penType = ((Element)trElem.elements().get(5)).getText();
            PenaltyType type = PenaltyTypeDeterminer.get(penType);

            if(type==PenaltyType.PS) {
                continue;
            }

            String pathToName = "//table[@id='PenaltySummary']/tr[2]/td/table/tr/td["+pathToNameInt+"]/table/tr[" + (i+1) + "]/td[4]/table/tr/td[4]";
            String shortName = this.doc.selectSingleNode(pathToName).getText();
            Player p = null;
            if(!shortName.equals("TEAM")) {
                p = PlayerDao.INSTANCE.findByShortName(shortName, team);
            }

            Penalty penalty = new Penalty();
            penalty.setGame(game);
            penalty.setMin(penaltyMinutes);
            penalty.setPlayer(p);
            penalty.setType(type);
            penalty.setTeam(team);
            penalty.setOpponent(oppositeTeam);
            penalty.setTime(fullTime);

            resultCollection.add(penalty);
        }
        System.out.println("added " + resultCollection.size() + " penalties");
        return resultCollection;
    }

    public Team getVisitorTeam() {
        if(this.doc == null) {
            return null;
        }
        Element visitorElem = (Element)this.doc.selectNodes("//table[@id='Visitor']/tr[3]/td").get(0);
        String fullName = new StringTokenizer(visitorElem.getText(), "\n").nextToken().trim();
        return Team.findByFullUpperName(fullName);
    }*/

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

    /*@SuppressWarnings("unchecked")
    private int getRealVisitorGoals() {
        if(this.doc == null) {
            return 0;
        }
        List<Element> list = this.doc.selectNodes("//table[@id='MainTable']/tr[9]/td/table/tr[2]/td/table/tr/td[1]/table/tr/td[1]");
        Element td = null;
        for(Element e : list) {
            if(e.getText().equals("TOT")) {
                td = e;
                break;
            }
        }

        String text = ((Element)td.getParent().elements().get(1)).getText();
        int goals = 0;
        try {
            goals = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return goals;
    }*/

    /*@SuppressWarnings("unchecked")
    private int getVisitorShotsTotal() {
        if(this.doc == null) {
            return 0;
        }
        List<Element> list = this.doc.selectNodes("//table[@id='MainTable']/tr[9]/td/table/tr[2]/td/table/tr/td[1]/table/tr/td[1]");
        Element td = null;
        for(Element e : list) {
            if(e.getText().equals("TOT")) {
                td = e;
                break;
            }
        }

        String text = ((Element)td.getParent().elements().get(2)).getText();
        int goals = 0;
        try {
            goals = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return goals;
    }

    public Team getHomeTeam() {
        if(this.doc == null) {
            return null;
        }
        Element homeElem = (Element)this.doc.selectNodes("//table[@id='Home']/tr[3]/td").get(0);

        String fullName = new StringTokenizer(homeElem.getText(), "\n").nextToken().trim();

        return Team.findByFullUpperName(fullName);
    }*/

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

    /*@SuppressWarnings("unchecked")
    private int getRealHomeGoals() {
        if(this.doc == null) {
            return 0;
        }
        List<Element> list = this.doc.selectNodes("//table[@id='MainTable']/tr[9]/td/table/tr[2]/td/table/tr/td[2]/table/tr/td[1]");
        Element td = null;
        for(Element e : list) {
            if(e.getText().equals("TOT")) {
                td = e;
                break;
            }
        }

        String text = ((Element)td.getParent().elements().get(1)).getText();
        int goals = 0;
        try {
            goals = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return goals;
    }

    @SuppressWarnings("unchecked")
    private int getHomeShotsTotal() {
        if(this.doc == null) {
            return 0;
        }
        List<Element> list = this.doc.selectNodes("//table[@id='MainTable']/tr[9]/td/table/tr[2]/td/table/tr/td[2]/table/tr/td[1]");
        Element td = null;
        for(Element e : list) {
            if(e.getText().equals("TOT")) {
                td = e;
                break;
            }
        }

        String text = ((Element)td.getParent().elements().get(2)).getText();
        int goals = 0;
        try {
            goals = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return goals;
    }

    protected String getVisitorPPSummaryString() {
        if(this.doc == null) {
            return "-";
        }
        Element visitorPpElement = (Element)this.doc.selectNodes("//table[@id='MainTable']/tr[11]/td/table/tr[2]/td/table/tr/td[1]/table/tr[2]/td[4]").get(0);
        return visitorPpElement.getText();
    }

    protected List<Element> getVisitorPPSummaryElems() {
        if(this.doc == null) {
            return null;
        }
        List<Element> visitorPpElements = (List<Element>)this.doc.selectNodes("//table[@id='MainTable']/tr[11]/td/table/tr[2]/td/table/tr/td[1]/table/tr[2]/td");
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

    public int getVisitorPP() {
        if(getIsNewPPFormat()) {
            return getVisitorPPNew();
        }
        return getVisitorPP(getVisitorPPSummaryString());
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

    public int getVisitorPPGoals() {
        if(getIsNewPPFormat()) {
            return getVisitorPPGoalsNew();
        }
        return getVisitorPPGoals(getVisitorPPSummaryString());
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

    public int getVisitorPPTime() {
        if(getIsNewPPFormat()) {
            return getVisitorPPTimeNew();
        }
        return getVisitorPPTime(getVisitorPPSummaryString());
    }

    public String getHomePPSummaryString() {
        if(this.doc == null) {
            return "-";
        }
        Element visitorPpElement = (Element)this.doc.selectNodes("//table[@id='MainTable']/tr[11]/td/table/tr[2]/td/table/tr/td[2]/table/tr[2]/td[4]").get(0);
        return visitorPpElement.getText();
    }

    public List<Element> getHomePPSummaryElems() {
        if(this.doc == null) {
            return null;
        }
        List<Element> homePpElements = (List<Element>)this.doc.selectNodes("//table[@id='MainTable']/tr[11]/td/table/tr[2]/td/table/tr/td[2]/table/tr[2]/td");
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

    public int getHomePP() {
        if(getIsNewPPFormat()) {
            return getHomePPNew();
        }
        return getHomePP(getHomePPSummaryString());
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

    public int getHomePPGoals() {
        if(getIsNewPPFormat()) {
            return getHomePPGoalsNew();
        }
        return getHomePPGoals(getHomePPSummaryString());
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

    public int getHomePPTime() {
        if(getIsNewPPFormat()) {
            return getHomePPTimeNew();
        }
        return getHomePPTime(getHomePPSummaryString());
    }*/

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
            builder.append(stTok.nextToken());
        }
        return builder.toString();
    }
}
