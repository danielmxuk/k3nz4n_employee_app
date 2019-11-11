
/*
 * It is pending put how works the logic of the default values
 * 
 */

CREATE TABLE Employee (
	id					UUID DEFAULT RANDOM_UUID(), 
	first_name 			VARCHAR(50),
	middle_initial 		CHAR(1),
	last_name 			VARCHAR(50),
	date_of_birth 		DATE  AS CASEWHEN( date_of_birth      IS NULL, (CURRENT_DATE - ( (18 * 365) + RAND() * (47*365) ) ), date_of_birth ),
	date_of_employment 	DATE  AS CASEWHEN( date_of_employment IS NULL, (CURRENT_DATE - ( (1 * 365)  + RAND() * (9*365)  ) ), date_of_employment ),
	is_user_active 		BOOLEAN
);
