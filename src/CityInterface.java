public interface CityInterface {

    // Return the unique ID of each city
    // Takes values from 1 to 999
    public int getID();

    // Return the name of the city. Doesn't exceed 50 characters, including possible spaces
    // Cities with the same name could exist
    public String getName();
    
    // Return the population of the city (0 < population <= 10.000.000)
    public int getPopulation();

    // Return the daily influenza cases. Can't be greater than population
    public int getInfluenzaCases();

    // Respecitve setters for the methods above
    public void setID(int ID);
    
    public void setName(String name);

    public void setPopulation(int population);

    public void setInfluenzaCases(int InfluenzaCases);
}