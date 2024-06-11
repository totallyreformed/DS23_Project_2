/*
 * Author: NAPOLEON CHARALAMPIDIS (3220225)
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Influenza_k {
    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);
        String fileName = args[1];

        try {
            // Create "cities" StringDoubleEndedQueueImpl type object that takes values from txt file
            StringDoubleEndedQueueImpl<City> cities = readCities(fileName);
            if (k > cities.size()) {
                System.out.println("Error: k value cannot be greater than the number of cities");
                return;
            }

            // Sort the cities by density using heap
            heapSort(cities);

            System.out.println("Top " + k + " cities are:");

            for (int i = k; i > 0; i--) {
                City city = cities.removeLast();
                System.out.println(city.getName());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }


    private static void heapSort(StringDoubleEndedQueueImpl<City> cities) {
        int n = cities.size();

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(cities, n, i);
        }

        // Extract elements from the heap one by one
        for (int i = n - 1; i >= 0; i--) {
            // Move current root to the end
            City temp = cities.getFirst();
            cities.removeFirst();
            cities.addLast(temp);

            // call max heapify on the reduced heap
            heapify(cities, i, 0);
        }
    }

    public static void heapify(StringDoubleEndedQueueImpl<City> cities, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && cities.get(left).compareTo(cities.get(largest)) > 0) {
            largest = left;
        }

        if (right < n && cities.get(right).compareTo(cities.get(largest)) > 0) {
            largest = right;
        }

        if (largest != i) {
            City swap = cities.get(i);
            cities.set(i, cities.get(largest));
            cities.set(largest, swap);

            heapify(cities, n, largest);
        }
    }

    // Compare density ratio between two cities
    public static int compare(City city1, City city2) {
        double ratio1 = (double) city1.getInfluenzaCases() / city1.getPopulation();
        double ratio2 = (double) city2.getInfluenzaCases() / city2.getPopulation();
        return Double.compare(ratio1, ratio2);
    }

    // Read city data from the text file
    private static StringDoubleEndedQueueImpl<City> readCities(String fileName) throws FileNotFoundException, IllegalArgumentException {
        StringDoubleEndedQueueImpl<City> cities = new StringDoubleEndedQueueImpl<City>();
        Scanner fileScanner = new Scanner(new File(fileName));

        while (fileScanner.hasNext()) {
            try{
                int ID = fileScanner.nextInt();
                String name = fileScanner.next();
                int population = fileScanner.nextInt();
                int InfluenzaCases = fileScanner.nextInt();
                
                // Add validity checks to the input data
                if (ID < 1 || ID > 999) {
                    throw new IllegalArgumentException();
                }

                if (name.length() > 50) {
                    throw new IllegalArgumentException();
                }
                
                if (population < 0 || population > 10000000) {
                    throw new IllegalArgumentException();
                }

                if (InfluenzaCases > population) {
                    throw new IllegalArgumentException();
                }

                cities.addLast(new City(ID, name, population, InfluenzaCases));
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid data format in the file");
            }  
        }

        fileScanner.close();

        return cities;
    }
}