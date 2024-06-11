public interface Comparable<City> {

    // Useful method to compare two cities about certain attributes
    public int compareTo(City otherCity);

    // Abstract method for density calculation
    public double calculateDensity();
}
