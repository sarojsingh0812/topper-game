package com.topper.model;


import java.util.Date;

import reactor.core.publisher.Flux;

public class PlayEvent {

    private Flux<Player> player;
    private Date date;
    
    public PlayEvent() {
		
	}
    
	public PlayEvent(Flux<Player> player, Date date) {
		super();
		this.player = player;
		this.date = date;
	}
	public Flux<Player> getPlayer() {
		return player;
	}
	public void setPlayer(Flux<Player> player) {
		this.player = player;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

    

}
