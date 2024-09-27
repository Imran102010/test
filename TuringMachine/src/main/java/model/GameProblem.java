package model;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameProblem {

    private List<Problem> problemes;

    /**
     * Constructs a new `GameProblem` instance and loads known problems from a CSV file.
     * The CSV file should have a specific format with columns for problem number, secret code, and validators.
     * The CSV file path is assumed to be "src/main/resources/known_problems.csv".
     */
    public GameProblem() {
        String csvFile = "src/main/resources/known_problems.csv";

        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            List<String[]> records = reader.readAll();

            problemes = new ArrayList<>();

            for (int i = 1; i < records.size(); i++) {
                String[] record =records.get(i);
                int numeroProbleme = Integer.parseInt(record[0]);
                String codeSecret = record[3];

                List<Integer> validateurs = new ArrayList<>();
                for (int j = 4; j < record.length; j++) {
                    validateurs.add(Integer.parseInt(record[j]));
                }
                Problem probleme = new Problem(numeroProbleme, codeSecret, validateurs);
                problemes.add(probleme);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a copy of the list of known problems.
     *
     * @return A copy of the list of known problems.
     */
    public List<Problem> getProblemes() {
        return new ArrayList<>(problemes);
    }
}
