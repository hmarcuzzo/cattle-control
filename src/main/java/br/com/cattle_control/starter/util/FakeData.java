package br.com.cattle_control.starter.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.model.Farm;
import br.com.cattle_control.starter.model.People;
import br.com.cattle_control.starter.model.Place;
import br.com.cattle_control.starter.service.FarmService;
import br.com.cattle_control.starter.service.PeopleService;
import br.com.cattle_control.starter.service.PlaceService;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class FakeData {

    @Autowired
    FarmService farmService;

    @Autowired
    PeopleService peopleService;

    @Autowired
    PlaceService placeService;

    @EventListener
    public void appReady(ApplicationReadyEvent event) throws EntityAlreadyExistsException, AnyPersistenceException,
             IOException, ParseException {        

        JSONParser parser = new JSONParser();

        try {
            JSONArray peopleArray = (JSONArray) parser.parse(new FileReader("src/main/resources/peopleData.json"));
            JSONArray placeArray = (JSONArray) parser.parse(new FileReader("src/main/resources/placeData.json"));
            JSONArray farmArray = (JSONArray) parser.parse(new FileReader("src/main/resources/farmData.json"));

            for (int i = 0; i < peopleArray.size(); i++)
            {
                JSONObject personObject = (JSONObject) peopleArray.get(i);
                
                People person = People.builder()
                                .name((String) personObject.get("name"))
                                .email((String) personObject.get("email"))
                                .type(1)
                                .idType((String) personObject.get("cpf"))
                                .phone((String) personObject.get("phone"))
                                .info("Uma informação qualquer")
                                .deleted(false)
                                .build();
                
                peopleService.create(person);
                
                JSONObject placeObject = (JSONObject) placeArray.get(i);
                
                Place place = Place.builder()
                .cep((String) placeObject.get("cep"))
                .city((String) placeObject.get("city"))
                .deleted(false)
                .build();
                
                placeService.create(place);
                
                JSONObject farmObject = (JSONObject) farmArray.get(i);

                Farm farm = Farm.builder()
                                .registerNumber((String) farmObject.get("registerNumber"))
                                .name((String) farmObject.get("name"))
                                .info("Uma informação qualquer")
                                .reference((String) farmObject.get("reference"))
                                .number((Integer) (int) (long) farmObject.get("number"))
                                .deleted(false)
                                .people(person)
                                .place(place)
                                .build();

                farmService.create(farm);          
         
            }
            
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
    }    
}
