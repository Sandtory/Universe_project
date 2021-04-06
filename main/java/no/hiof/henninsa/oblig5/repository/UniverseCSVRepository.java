package no.hiof.henninsa.oblig5.repository;

import no.hiof.henninsa.oblig5.model.Planet;
import no.hiof.henninsa.oblig5.model.PlanetSystem;
import no.hiof.henninsa.oblig5.model.Star;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class UniverseCSVRepository implements UniverseRepository{
    private File filename;
    public HashMap<String, PlanetSystem> planetSystems = new HashMap<>();

    public UniverseCSVRepository(String filename) {
        writeToCsvFile(filename);
    }

    private static void writeToCsvFile(String filename) {
        ArrayList<PlanetSystem> planetSystemCsvList = new ArrayList<>();

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename))){

            for (PlanetSystem systems : planetSystemCsvList){
                bufferedWriter.write(systems.getName());
            }

        }
        catch (FileNotFoundException fileNotFoundException) {
            System.out.println("FNFE:" + fileNotFoundException.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public HashMap<String, PlanetSystem> readFromCsvFile(String filename) {
        ArrayList<PlanetSystem> tempList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;

            while((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(";");

                Star star = new Star(values[2], Double.parseDouble(values[3]), Double.parseDouble(values[4]),
                        Double.parseDouble(values[5]), values[6]);
                Planet planet = new Planet(values[7], Double.parseDouble(values[8]), Double.parseDouble(values[9]),
                        Double.parseDouble(values[10]), Double.parseDouble(values[11]),
                        Double.parseDouble(values[12]), star, values[13]);

                PlanetSystem planetSystem = new PlanetSystem(values[0], star, values[1]);

                if (planetSystems.containsKey(planetSystem.getName())) {
                    planetSystems.put(planetSystem.getName(), planetSystem);
                }
                else if (!planetSystems.containsKey(planetSystem.getName())) {
                    planetSystem.addPlanet(planet);
                }
                }


            }
        catch (FileNotFoundException fileNotFoundException) {
            System.out.println(fileNotFoundException.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return planetSystems;
    }


    @Override
    public ArrayList<PlanetSystem> getAllPlanetSystems() {
        return new ArrayList<>(planetSystems.values());
    }

    @Override
    public PlanetSystem getPlanetSystem(String planetSystemName) {
        for (PlanetSystem aPlanetSystem : planetSystems.values())
            if (aPlanetSystem.getName().equalsIgnoreCase(planetSystemName))
                return aPlanetSystem;

        return null;
    }

    @Override
    public ArrayList<Planet> getAllPlanets(String planetSystemName) {
        PlanetSystem planetSystem = getPlanetSystem(planetSystemName);

        if (planetSystem != null)
            return planetSystem.getPlanets();

        return new ArrayList<>();

        //return getPlanetSystem(planetSystemName).getPlanets();
    }

    @Override
    public Planet getPlanet(String planetSystemName, String planetName) {
        PlanetSystem planetSystem = getPlanetSystem(planetSystemName);

        if (planetSystem != null)
            return planetSystem.getPlanet(planetName);

        return null;

        //return getPlanetSystem(planetSystemName).getPlanet(planetName);
    }
}
