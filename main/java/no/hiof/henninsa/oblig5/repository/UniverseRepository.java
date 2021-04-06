package no.hiof.henninsa.oblig5.repository;

import no.hiof.henninsa.oblig5.model.Planet;
import no.hiof.henninsa.oblig5.model.PlanetSystem;

import java.util.ArrayList;

public interface UniverseRepository {
    ArrayList<PlanetSystem> getAllPlanetSystems();
    PlanetSystem getPlanetSystem(String planetSystemName);

    ArrayList<Planet> getAllPlanets(String planetSystemName);
    Planet getPlanet(String planetSystemName, String planetName);
}
