package com.topper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.topper.model.PlayRequest;
import com.topper.model.Player;
import com.topper.repository.PlayerRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/player")
public class PlayerController {

	@Autowired
	PlayerRepository playerRepository;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public void register(@RequestBody Player player) {
		player.setScore(0);
		System.out.println(player.getName());
		playerRepository.save(player).subscribe();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Mono<Player>> findById(@PathVariable("id") Integer id) {
		Mono<Player> e = playerRepository.findById(id);
		HttpStatus status = e != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<Mono<Player>>(e, status);
	}

	@RequestMapping(value = "/play", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public Mono<Player> play(@RequestBody PlayRequest requestDto) {
		String text = requestDto.getText();
		System.out.println(text);
		if (text.equalsIgnoreCase("I am topper")) {
			System.out.println("step 1");
			System.out.println(requestDto.getId());
			Integer id = requestDto.getId();
			 return playerRepository
					.findById(id)
					.map(p -> new Player(p.getId(),p.getName(), p.getScore()+1))
					.flatMap(playerRepository::save);
					
		}
		return null;

	}



	@GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Player> getEvents() {
		
        return  playerRepository.findTop10ByOrderByScoreDesc();
                

    }
	



}
