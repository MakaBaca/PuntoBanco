package com.baca.boot.model;

public class PlayerWinRatio {
	
	boolean didWin;
	
	double ratio;

	public boolean isDidWin() {
		return didWin;
	}

	public void setDidWin(boolean didWin) {
		this.didWin = didWin;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public PlayerWinRatio(boolean didWin, double ratio) {
		super();
		this.didWin = didWin;
		this.ratio = ratio;
	}

	@Override
	public String toString() {
		return "PlayerWinRatio [didWin=" + didWin + ", ratio=" + ratio + "]";
	}
}
