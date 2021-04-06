package no.hiof.henninsa.oblig5.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.hiof.henninsa.oblig5.model.Planet;
import no.hiof.henninsa.oblig5.model.PlanetSystem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class UniverseJSONRepository implements UniverseRepository{

    HashMap<String, PlanetSystem> planetSystemHashMap = new HashMap<>();
    /*ArrayList<PlanetSystem> systemListFromFile;*/

    public UniverseJSONRepository(String filename){
        readFromFile(filename);

        }

    public void readFromFile(String filename){
        ArrayList<PlanetSystem> tempList = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            PlanetSystem[] arraySystemList = objectMapper.readValue(new File(filename), PlanetSystem[].class);
            tempList.addAll(Arrays.asList(arraySystemList));

            for (PlanetSystem systems : tempList) {
                planetSystemHashMap.put(systems.getName(), systems);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<PlanetSystem> getAllPlanetSystems() {
        return new ArrayList<>(planetSystemHashMap.values());
    }

    @Override
    public PlanetSystem getPlanetSystem(String planetSystemName) {
        return planetSystemHashMap.get(planetSystemName);
    }

    @Override
    public ArrayList<Planet> getAllPlanets(String planetSystemName) {
        PlanetSystem planetSystem = getPlanetSystem(planetSystemName);

        if (planetSystem != null)
            return planetSystem.getPlanets();

        return new ArrayList<>();
    }

    @Override
    public Planet getPlanet(String planetSystemName, String planetName) {
        PlanetSystem planetSystem = getPlanetSystem(planetSystemName);

        if (planetSystem != null)
            return planetSystem.getPlanet(planetName);

        return null;
    }
}

