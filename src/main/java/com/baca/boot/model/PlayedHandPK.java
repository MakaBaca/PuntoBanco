package com.baca.boot.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class PlayedHandPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	BacaShoeAndHandPK sh;

	int playerId;

	public BacaShoeAndHandPK getSh() {
		return sh;
	}

	public void setSh(BacaShoeAndHandPK sh) {
		this.sh = sh;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	@Override
	public String toString() {
		return "PlayedHandPK [sh=" + sh + ", playerId=" + playerId + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof PlayedHandPK))
			return false;
		PlayedHandPK that = (PlayedHandPK) obj;
		return Objects.equals(sh.getShoeNumber(), that.sh.getShoeNumber())
				&& Objects.equals(sh.getHandNumber(), that.sh.getHandNumber())
				&& Objects.equals(getPlayerId(), that.getPlayerId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(sh.getShoeNumber(), sh.getHandNumber(), getPlayerId());
	}
}
