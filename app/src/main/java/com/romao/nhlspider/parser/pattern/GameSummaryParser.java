package com.romao.nhlspider.parser.pattern;

public class GameSummaryParser {
	
	/*public static final Set<String> notFoundPlayers = new HashSet<String>();
	
	private Season season = Season.SEASON_11_12;
	
	private final Game game;
	
	public GameSummaryParser(final File summaryFile) {
		super(summaryFile);
		this.game = createGame();
	}
	
	private GameSummaryParser(final String url) {
		super(url);
		this.game = createGame();
		if(url.contains("20082009")) {
			this.season = Season.SEASON_08_09;
		} else if(url.contains("20092010")) {
			this.season = Season.SEASON_09_10;
		} else if(url.contains("20102011")) {
			this.season = Season.SEASON_10_11;
		} else if(url.contains("20112012")) {
			this.season = Season.SEASON_11_12;
		}
	}
	
	public GameSummaryParser(final Season season, final int gameNumber) {
		this(season, gameNumber, false);
	}
	
	public GameSummaryParser(final Season season, final int gameNumber, final Boolean isPO) {
		super(season, gameNumber, isPO);
		this.season = season;
		this.game = createGame();
	}
	
	public Game getGame() {
		return this.game;
	}
	
	private Element getVisitorPenaltyElement() {
		if(this.doc == null) {
			return null;
		}
		Element visitorPenaltyElem = (Element)this.doc.selectNodes("//table[@id='PenaltySummary']/tr[2]/td/table/tr/td[1]").get(0);
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
	}
	
	private int getVisitorGoals() {
		if(this.doc == null) {
			return 0;
		}
		Element visitorElem = (Element)this.doc.selectNodes("//table[@id='Visitor']/tr[2]/td/table/tr/td[2]").get(0);
		int goals = 0;
		try {
			goals = Integer.parseInt(visitorElem.getText());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return goals;
	}
	
	@SuppressWarnings("unchecked")
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
	}
	
	@SuppressWarnings("unchecked")
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
	}
	
	private int getHomeGoals() {
		if(this.doc == null) {
			return 0;
		}
		Element visitorElem = (Element)this.doc.selectNodes("//table[@id='Home']/tr[2]/td/table/tr/td[2]").get(0);
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
	}
	
	@SuppressWarnings("unchecked")
	private Element getGoalInfoElement(final int goalIndex) {
		if(this.doc == null) {
			return null;
		}
		if(goalIndex > getHomeGoals() + getVisitorGoals()) {
			return null;
		}
		List<Element> list = this.doc.selectNodes("//table[@id='MainTable']/tr[4]/td/table/tr/td[1]");
		Element result = null;
		for(Element e : list) {
			try {
				int eInt = Integer.parseInt(e.getText().trim());
				if(eInt==goalIndex) {
					result = e.getParent();
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
		
		Element td = (Element)elem.elements().get(1);
		String str = td.getText().trim();
		if(str.equals("OT")) {
			return 4;
		}
		return Integer.parseInt(str);
	}
	
	private int getGoalTime(final int goalIndex) {
		Element elem = getGoalInfoElement(goalIndex);
		if(elem==null || (!getIsPO() && getGoalPeriod(goalIndex).equals(Period.SHOOTOUT))) {
			return 0;
		}
		
		int period = getGoalPeriod(goalIndex);
		Element td = (Element)elem.elements().get(2);
		String timeString = td.getText();
		
		return ParsingUtils.convertTimeStringToSeconds(timeString) + (period - 1) * 1200;
	}
	
	private int getAttendance() {
		if(this.doc == null) {
			return 0;
		}
		Element list = (Element)(this.doc.selectNodes("//table[@id='GameInfo']/tr[5]/td")).get(0);
		StringTokenizer stTok = new StringTokenizer(list.getText(), " ");
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
	
	public String getDate() {
		if(this.doc == null) {
			return "";
		}
		Element list = (Element)(this.doc.selectNodes("//table[@id='GameInfo']/tr[4]/td")).get(0);
		return list.getText();
	}
	
	@SuppressWarnings("deprecation")
	private Game createGame() {
		Game g = new Game();
		g.setIsPO(getIsPO());
		try {
			g.setAttendance(getAttendance());
			g.setDate(new Date(getDate()));
			
			g.setHomeTeam(getHomeTeam());
			g.setHomeGoals(getHomeGoals());
			g.setHomeRealGoals(getRealHomeGoals());
			g.setHomeShots(getHomeShotsTotal());
			g.setHomePP(getHomePP());
			g.setHomePPGoals(getHomePPGoals());
			g.setHomePPTime(getHomePPTime());
			
			g.setVisitorTeam(getVisitorTeam());
			g.setVisitorGoals(getVisitorGoals());
			g.setVisitorRealGoals(getRealVisitorGoals());
			g.setVisitorShots(getVisitorShotsTotal());
			g.setVisitorPP(getVisitorPP());
			g.setVisitorPPGoals(getVisitorPPGoals());
			g.setVisitorPPTime(getVisitorPPTime());
			
			g.setGameFinal(getGameFinal());
			g.setGameLength(getGameLength());
			g.setHomePoints(getHomePoints());
			g.setVisitorPoints(getVisitorPoints());
			g.setWinner(getWinner());
			g.setLoser(g.getOpponent(g.getWinner()));
			
			g.setSeason(this.season);
			g.setOrderNumber(this.gameOrderNumber);
		} catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Invalid game report!!!");
			return null;
		}
		
		RosterParser rosterParser = new RosterParser(g, this.season, this.gameOrderNumber, getIsPO());
		TeamGameRoster hRoster = rosterParser.createHomeTeamRoster();
		TeamGameRoster vRoster = rosterParser.createVisitorTeamRoster();
		
		g.sethRoster(hRoster);
		g.setvRoster(vRoster);
		
		return g;
	}
	
	public Boolean getIsPO() {
		return false;
	}
	
	private Team getWinner() {
		if(getHomeGoals() > getVisitorGoals()) {
			return getHomeTeam();
		}
		return getVisitorTeam();
	}
	
	private int getHomePoints() {
		Team winner = getWinner();
		Team home = getHomeTeam();
		if(home==winner) {
			return 2;
		}
		if(getGameFinal()==GameFinal.FINAL) {
			return 0;
		}
		return 1;
	}
	
	private int getVisitorPoints() {
		Team winner = getWinner();
		Team visitor = getVisitorTeam();
		if(visitor==winner) {
			return 2;
		}
		if(getGameFinal()==GameFinal.FINAL) {
			return 0;
		}
		return 1;
	}
	
	private int getGameLength() {
		GameFinal gf = getGameFinal();
		if(gf==GameFinal.SHOOTOUT) {
			return 65 * 60;
		}
		int goalIndex = getHomeGoals() + getVisitorGoals();
		int time = getGoalTime(goalIndex);
		
		if(time>3600) {
			return time;
		}
		return 60 * 60;
	}
	
	private GameFinal getGameFinal() {
		Element table = (Element)this.doc.selectNodes("//table[@id='MainTable']/tr[4]/td/table").get(0);
		Element tr = (Element)table.elements().get(table.elements().size()-1);
		Element td = (Element)tr.elements().get(1);
		String str = td.getText();
		if(str.equals("OT")) {
			return GameFinal.OVERTIME;
		}
		if(str.equals("SO")) {
			return GameFinal.SHOOTOUT;
		}
		return GameFinal.FINAL;
	}
	
	public Element findGoalieTableElement() {
		int trNumber = 16;
		Element goalieTableElement = null;
		
		try {
			goalieTableElement = (Element)this.doc.selectNodes("//table[@id='MainTable']/tr[" + trNumber + "]/td/table").get(0);
		} catch (IndexOutOfBoundsException ex) {
			trNumber = 15;
			goalieTableElement = (Element)this.doc.selectNodes("//table[@id='MainTable']/tr[" + trNumber + "]/td/table").get(0);
		}
//		System.out.println("goalie table:" + goalieTableElement.elements().size());
		
		return goalieTableElement;
	}
	
	public Collection<GameGoalieDetails> createGameGoalieDetails() {
		Collection<GameGoalieDetails> list = new ArrayList<GameGoalieDetails>();
		
		Team currentTeam = null;
		Team oppositeTeam = null;
		Element goalieTableElement = findGoalieTableElement();
		if(goalieTableElement==null) {
			System.out.println("no goalie info for game #" + this.game.getOrderNumber());
			return list;
		}
		for(Object o : goalieTableElement.elements()) {
			Element tr = (Element)o;
			
			Element firstTd = (Element)tr.elements().get(0); 
			String text = (firstTd).getTextTrim();
			
			Team team = Team.findByFullUpperName(text);
			if(team!=null) {
				currentTeam = team;
				oppositeTeam = team==this.game.getHomeTeam() ? this.game.getVisitorTeam() : this.game.getHomeTeam();
				continue;
			}
			
			GameGoalieDetails ggd = GameGoalieDetailsCreator.createGGD(tr, this.game, currentTeam, oppositeTeam);
			
			if(ggd==null) {
				continue;
			}
			
			list.add(ggd);
		}
		
		return list;
	}
	
	private Boolean getIsNewPPFormat() {
		return this.season.compareTo(Season.SEASON_09_10) > 0;
	}


	@Override
	public String getSummaryTemplate() {
		return NHLConstants.GAME_SUMMARY_TEMPLATE;
	}
	
	public static void main(String[] args) {
		File testFile = new File("D:/GS020003.HTM");
		GameSummaryParser parser = new GameSummaryParser("http://www.nhl.com/scores/htmlreports/20112012/GS020391.HTM");
		
		System.out.println("V-PP:" + parser.getVisitorPP());
		System.out.println("V-PPG:" + parser.getVisitorPPGoals());
		System.out.println("V-PPT:" + parser.getVisitorPPTime());
		System.out.println("H-PP:" + parser.getHomePP());
		System.out.println("H-PPG:" + parser.getHomePPGoals());
		System.out.println("H-PPT:" + parser.getHomePPTime());
	}*/
}