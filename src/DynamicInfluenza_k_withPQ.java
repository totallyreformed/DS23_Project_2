/*
 * Author: NAPOLEON CHARALAMPIDIS (3220225)
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DynamicInfluenza_k_withPQ {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java DynamicInfluenza_k_withPQ <k> <fileName>");
            return;
        }

        int k = Integer.parseInt(args[0]);
        String file = args[1];

        // // Comparator to compare City objects based on influenza cases
        // Comparator<City> comparator = Comparator.comparingInt(City::getInfluenzaCases);

        // Priority queue that maintains top k cities with lowest Influenza cases
        PQ queue = new PQ(new CityComparator());
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineCount = 0;

            while ((line = br.readLine()) != null) {
                lineCount++;

                // Process the line and create a City object
                City city = processLine(line);

                // Insert city to the priority queue
                queue.insert(city);

                // Print the top k cities every 5 lines
                // For debugging purposes
                if (lineCount % 5 == 0 && lineCount >= k) {
                    printTopCitiesDebug(queue, k);
                }   
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print the top k cities in the end
        System.out.println("The top k cities are: ");
        for (int i = 0; i < k; i++) {
            City c = queue.getMin();
            if (c != null) {
                System.out.println(c.getName());
            }
        }
    }

    private static City processLine(String line) {

        // Process each part of the line separately and store values to a table
        // Then create a City object
        
        String[] parts = line.split("\\s+"); // Split using whitespace
        if (parts.length == 4) {
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            int population = Integer.parseInt(parts[2]);
            int influenzaCases = Integer.parseInt(parts[3]);
            return new City(id, name, population, influenzaCases);
        } else {
            throw new IllegalArgumentException("Invalid data format in the file");
        }
    } 

    public static void printTopCitiesDebug(PQ queue, int k) {
        System.out.println("Top " + k + " cities after 5 lines with lowest influenza cases:");
        for (int i = 0; i <= k; i++) {
            City c = queue.peekItem(i);
            if (c != null) {
                System.out.println(c.getName());
            }
        }
        System.out.println();
    }
}