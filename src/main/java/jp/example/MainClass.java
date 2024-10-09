package jp.example;

import java.util.List;

public class MainClass {
	public static void main(String[] args) {
		String csvFilePath = "src/main/resources/sample.csv";
		
		CSVReaderExample csvReader = new CSVReaderExample();
		List<String[]> data = csvReader.readCSV(csvFilePath);
		
		DatabaseInserter inserter = new DatabaseInserter();
		inserter.insertData(data);
		
	}
}
