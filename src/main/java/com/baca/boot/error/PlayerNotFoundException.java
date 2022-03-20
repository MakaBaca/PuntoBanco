package com.baca.boot.error;

public class PlayerNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8348782;
	
	int playerId = 0;
	
	
	public PlayerNotFoundException(int playerId) {
		super();
		this.playerId = playerId;
	}


	@Override
	public String getMessage() {
		return "PlayerId:"+playerId+" Not found in Repository";
	}
}
