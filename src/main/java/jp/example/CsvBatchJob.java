package jp.example;

import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * CsvBatchJob is responsile for reading data from a CSV file, storing it in a temporary table,
 * and processing it to save the final results in the current table.
 */
@Component
public class CsvBatchJob {

	@Autowired
	private TemporaryDataRepository temporaryDataRepository;
	
	@Autowired
	private CurrentDataRepository currentDataRepository;
	
	private static final String CSV_FOLDER = "C:\\work\\task_business\\csv";
	
	/**
	 * Processes the CSV files by reading them, inserting data into a  temporary table,
	 * and then moving processed data into the current table.
	 */
	@Transactional
	public void processCsvFiles() {
		try {
			Path filePath = Paths.get(CSV_FOLDER, "sample.csv");
			// Parse the CSV file
			try (CSVParser parser = CSVFormat.DEFAULT.parse(new FileReader(filePath.toFile()))) {
				List<TemporaryData> tempDataList = new ArrayList<>();
				
				// Loop through each CSV record and map it to TemporaryData entity
				for (CSVRecord record : parser) {
					TemporaryData tempData = new TemporaryData();
					tempData.setName(record.get(0));
					tempData.setAge(Integer.parseInt(record.get(1)));
					tempData.setAddress(record.get(2));
					tempDataList.add(tempData);
				}
				// Batch insert all temporary data into the database
				temporaryDataRepository.saveAll(tempDataList);
				
				// Process the temporary data and insert into the current table
				List<CurrentData> currentDataList = new ArrayList<>();
				for (TemporaryData tempData : tempDataList) {
					CurrentData currentData = new CurrentData();
					currentData.setName(tempData.getName());
					currentData.setAge(tempData.getAge());
					currentData.setProcessedData(tempData.getName() + " - " + tempData.getAge());
					currentDataList.add(currentData);
				}
				// Batch insert into the current table
				currentDataRepository.saveAll(currentDataList);
				
				// Clear the temporary table after processing
				temporaryDataRepository.deleteAll();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
