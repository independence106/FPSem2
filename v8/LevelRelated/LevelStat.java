package LevelRelated;

import Stats.LLList;
import Stats.LList;

public class LevelStat {
    public int deaths;
    public LLList<Integer> scores;

    public LevelStat() {
        scores = new LList<Integer>();
        deaths = 0;
    }
    
    public void setDeaths(int newDeaths) {
        deaths = newDeaths;
    }

    public void addScore(int newScore) {
        scores.add(newScore);
    }

    
}
