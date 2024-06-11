public class City implements CityInterface, Comparable<City> {
    protected int ID;
    protected String name;
    protected int population;
    protected int InfluenzaCases;

    // Constructor method
    public City(int ID, String name, int population, int InfluenzaCases) {
        this.ID = ID;
        this.name = name;
        this.population = population;
        this.InfluenzaCases = InfluenzaCases;
    }

    // Getter methods
    @Override
    public int getID() {
        return ID;
    }

    @Override
    public String getName() {
        return name;
    }    

    @Override
    public int getPopulation() {
        return population;
    }

    @Override
    public int getInfluenzaCases() {
        return InfluenzaCases;
    }

    // Setter methods
    @Override
    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPopulation(int population) {
        this.population = population;
    }

    @Override
    public void setInfluenzaCases(int InfluenzaCases) {
        this.InfluenzaCases = InfluenzaCases;
    }

    // Create body for abstract methods in Comparable interface
    @Override
    public int compareTo(City otherCity) {
        double density1 = calculateDensity();
        double density2 = otherCity.calculateDensity();

        // Compare densities
        int densityComparison = Double.compare(density1, density2);
        if (densityComparison != 0) {
            return densityComparison;
        }

        // If densities are equal, compare city names alphabetically
        int nameComparison = this.getName().compareTo(otherCity.getName());
        if (nameComparison != 0) {
            return nameComparison;
        }

        // If city names are also equal, compare by ID
        return Integer.compare(this.getID(), otherCity.getID());
    }

    // Calculate density for each city per 50000 residents
    public double calculateDensity() {
        return (double)getInfluenzaCases() / getPopulation() * 50000;
    }
}