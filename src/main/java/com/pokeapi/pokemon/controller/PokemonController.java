package com.pokeapi.pokemon.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.pokeapi.entity.PokemonEntity;
import com.pokeapi.entity.PokemonMoveEntity;

@RestController
@RequestMapping("/pokeapi/v1")
public class PokemonController {

  @GetMapping("/main")
  public String getMaxMove() {
	final String pokemon_uri = "https://pokeapi.co/api/v2/pokemon/12";
	HttpHeaders headers = new HttpHeaders();
	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
	HttpEntity <String> entity = new HttpEntity<String>(headers); 
	RestTemplate restTemplate = new RestTemplate();
	PokemonEntity result = restTemplate.exchange(pokemon_uri, HttpMethod.GET, entity, PokemonEntity.class).getBody(); 	    
	List<PokemonMoveEntity> pokemonMoveEntity = result.getMoves().stream().map(moveEntity->{
	  	return restTemplate.exchange(moveEntity.getMove().getUrl(), HttpMethod.GET, entity, PokemonMoveEntity.class).getBody();
	}).collect(Collectors.toList());	    
	PokemonMoveEntity maxMove = Collections.max(pokemonMoveEntity,Comparator.comparing(s -> s.getPower()));
    return maxMove.getName() + " : " + maxMove.getPower() ;
  }
  
}  
