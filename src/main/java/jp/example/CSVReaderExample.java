package jp.example;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class CSVReaderExample {
	public List<String[]> readCSV(String filePath) {
		List<String[]> data = new ArrayList<>();
		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
			String[] line;
			while ((line = reader.readNext()) != null) {
				data.add(line);
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
		return data;
	}
}
