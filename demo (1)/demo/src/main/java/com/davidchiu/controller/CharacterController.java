package com.davidchiu.controller;

import com.davidchiu.Service.MarvelApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class CharacterController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MarvelApiService marvelApiService;

    @GetMapping(value = "/characters")
    public List<Integer> getCharacterIds() throws Exception {
        return marvelApiService.getCharacterIds();
    }

}
