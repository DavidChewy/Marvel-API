package com.davidchiu.Service;

import com.davidchiu.models.Character;
import com.davidchiu.models.MarvelResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class MarvelApiService {

    private static final String baseUri = "http://gateway.marvel.com/v1/public/";

    @Value("${userKey.publicKey}")
    private String publicKey;

    @Value("${userKey.privateKey}")
    private String privateKey;

    public List<Integer> getCharacterIds() throws Exception {
        String uri = baseUri + "characters?" + generateAuthInfo();
        log.info(uri);
        RestTemplate restTemplate = new RestTemplate();
        List<Integer> ids = new ArrayList<>();
        List<String> description = new ArrayList<>();


        try {
            MarvelResponse response = new MarvelResponse();
            response = restTemplate.getForObject(uri, MarvelResponse.class);

            for (Character character : response.data.results) {
                ids.add(character.getId());
                description.add(character.getDescription());
            }
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return ids;
    }

    public Character getCharacter(Integer id) throws Exception {
        String uri = baseUri + "characters/" + id + "?" + generateAuthInfo();
        RestTemplate restTemplate = new RestTemplate();
        Character character = new Character();

        try {
            MarvelResponse response = new MarvelResponse();
            response = restTemplate.getForObject(uri, MarvelResponse.class);

            character = response.data.results.get(0);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return character;
    }

    //Method to generate authentication to access the api
    public String generateAuthInfo() throws Exception {
        String publicKey = this.publicKey;
        String privateKey = this.privateKey;
        String timeStamp = String.valueOf(new Date().getTime());
        String stringToHash = timeStamp + privateKey + publicKey;

        return "ts=" + timeStamp + "&apikey=" + publicKey + "&hash=" + getMd5(stringToHash);

    }

    public String getMd5(String input) {
        return DigestUtils.md5DigestAsHex(input.getBytes(Charset.defaultCharset()));
    }
}
