package be.groupe18.common.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CityList implements Serializable {
    private int NbrCity;
    ArrayList<City> cityList = new ArrayList<>();
    
/**
 *  Reading the file and creating a list of cities.
 */
    
    public CityList(){
            String line;
            try(BufferedReader reader = new BufferedReader(new FileReader("Common/src/be/groupe18/common/models/mapInfo.txt"))){
                System.out.println("Fichier correctement chargé");

                while ((line = reader.readLine()) != null) {

                    String name = line;
                    String nameASCII = reader.readLine();
                    double latitude = Double.parseDouble(reader.readLine());
                    double longitude = Double.parseDouble(reader.readLine());
                    String country = reader.readLine();
                    String countryISO2 = reader.readLine();
                    String countryISO3 = reader.readLine();
                    String adminCapital = reader.readLine();
                    String capital = reader.readLine();
                    String population = reader.readLine();
                    String id = reader.readLine();


                    City city = new City(name,nameASCII,latitude,longitude,country,countryISO2,countryISO3,adminCapital,capital,population,id);
                    cityList.add(city);
                }

            }catch(IOException e){
                System.out.println("Erreur lors de la lecture du fichier.");
            }

    }


    /**
     * This function takes a list of cities and returns a list of the names of those cities
     * 
     * @return A list of strings.
     */
    private List<String> getNamesOfCity(){
        ArrayList<String> airports = new ArrayList<>();
        for (City city: this.cityList) {
            airports.add(city.getName());
        }
        return airports;
    }


/**
 * Return a list of cities that contain the search string.
 * 
 * @param search the search term
 * @return A list of cities that contain the search string.
 */
    public List<City> getSearchListObject(String search){
        return this.cityList.stream()
                .filter(x -> x.getName().toLowerCase().contains(search))
                .collect(Collectors.toList());
    }

    public List<City> getCityList() {
        return this.cityList;
    }
}

