package com.topper.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.topper.model.Player;

import reactor.core.publisher.Flux;

public interface PlayerRepository extends ReactiveMongoRepository<Player, Integer> {
	
	public Flux<Player> findTop10ByOrderByScoreDesc();
}
