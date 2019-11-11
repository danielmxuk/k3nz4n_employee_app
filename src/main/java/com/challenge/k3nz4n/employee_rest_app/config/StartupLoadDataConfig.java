package com.challenge.k3nz4n.employee_rest_app.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import com.challenge.k3nz4n.employee_rest_app.exception.PreLoadedDataException;
import com.challenge.k3nz4n.employee_rest_app.model.entity.EmployeeEntity;
import com.challenge.k3nz4n.employee_rest_app.model.service.EmployeeService;
import com.challenge.k3nz4n.employee_rest_app.utility.AppUtils;

import ch.qos.logback.classic.Logger;

/**
 * <pre>
 * This class is executed once the Application is ready to use, then this class load the dummy data of the employees.
 * 
 * The dummy data is generated on the fly randomly from two source files.
 * 
 * The number of entries in the DB is defined in the application profiles (prod, dev, test, default), for these details please check the application properties.
 * 
 * The employee entries are created in memory using H2 DB, the setting of this DB is also defined in the application properties file.
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Nov 5, 2019
 */

@Configuration
public class StartupLoadDataConfig {
	
	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(StartupLoadDataConfig.class);
	
	@Value("${data-source.employee.list-names}")
	private String dataSourceListNames;
	
	@Value("${data-source.employee.list-last-names}")
	private String dataSourceListLastNames;
	
	@Value("${data-source.employee.entries-number-to-load}")
	private int dataSourceEntriesNumberToLoad;
	
	private Random randomObj = new Random();
	
	@Autowired
	EmployeeService employeeService;
	
	@EventListener(ApplicationReadyEvent.class)
	public void loadEmployeeDataAfterStartup() throws PreLoadedDataException {
		
		try (	BufferedReader dataSrcNamesBufferedReader     = new BufferedReader( new FileReader(dataSourceListNames) );
				BufferedReader dataSrcLastNamesBufferedReader = new BufferedReader( new FileReader(dataSourceListLastNames) ) ) {
				
				generateEmployeeList( dataSrcNamesBufferedReader.lines().toArray(String[]::new),
										dataSrcLastNamesBufferedReader.lines().toArray(String[]::new) ) ;
				
		} catch (IOException e) {
				throw new PreLoadedDataException( "Unable to load the data source file! : " + e.getMessage() );
		}
	}
	
	private void generateEmployeeList(String[] employeeNamesArray, String[] employeeLastNamesArray){
		
		for(int i = 1; i <= dataSourceEntriesNumberToLoad; i++) {
			EmployeeEntity employeeEntity = new EmployeeEntity();
			
			employeeEntity.setFirstName( getRandomNameFromArray(employeeNamesArray) );
			employeeEntity.setMiddleInitial( String.valueOf( getRandomNameFromArray(employeeNamesArray).charAt(0) ) );
			employeeEntity.setLastName( getRandomNameFromArray(employeeLastNamesArray) );
			employeeEntity.setUserActive(true);
			
			employeeService.saveEmployee(employeeEntity);
			LOGGER.debug("Saved employee #{}  as: {} {}. {}", i, employeeEntity.getFirstName(), employeeEntity.getMiddleInitial(), employeeEntity.getLastName() );
		}

		LOGGER.info("\n\n\n  -> Loaded Employee data: ({} generated) ({} saved)   -  App ready to use, have fun :) \n\n", dataSourceEntriesNumberToLoad, employeeService.getTotalNumberOfEmployeesInDB() );
	}
	
	private String getRandomNameFromArray(String[] sourceArray) {
		int limit = sourceArray.length;
		return sourceArray[randomObj.nextInt(limit)];
	}
}
