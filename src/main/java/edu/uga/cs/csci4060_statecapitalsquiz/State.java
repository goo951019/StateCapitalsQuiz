package edu.uga.cs.csci4060_statecapitalsquiz;

public class State {
    private String state, capital, scity, tcity;
    private int statehood, since, sizerank;
    private long id;

    public State() {
        this.state = null;
        this.capital = null;
        this.scity = null;
        this.tcity = null;
        this.statehood = -1;
        this.since = -1;
        this.sizerank = -1;
        this.id = -1;
    }

    public State(String state, String capital, String scity, String tcity, int statehood, int since, int sizerank) {
        this.id = -1;
        this.state = state;
        this.capital = capital;
        this.scity = scity;
        this.tcity = tcity;
        this.statehood = statehood;
        this.since = since;
        this.sizerank = sizerank;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getScity() {
        return scity;
    }

    public void setScity(String scity) {
        this.scity = scity;
    }

    public String getTcity() {
        return tcity;
    }

    public void setTcity(String tcity) {
        this.tcity = tcity;
    }

    public int getStatehood() {
        return statehood;
    }

    public void setStatehood(int statehood) {
        this.statehood = statehood;
    }

    public int getSince() {
        return since;
    }

    public void setSince(int since) {
        this.since = since;
    }

    public int getSizerank() {
        return sizerank;
    }

    public void setSizerank(int sizerank) {
        this.sizerank = sizerank;
    }

    @Override
    public String toString() {
        return "State{" +
                "state='" + state + '\'' +
                ", capital='" + capital + '\'' +
                ", scity='" + scity + '\'' +
                ", tcity='" + tcity + '\'' +
                ", statehood=" + statehood +
                ", since=" + since +
                ", sizerank=" + sizerank +
                ", id=" + id +
                '}';
    }
}
