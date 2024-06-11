import java.util.Comparator;

public class CityComparator implements Comparator<City>{
    @Override
    public int compare(City t1, City t2) {
        return t2.compareTo(t1);
    }
}
