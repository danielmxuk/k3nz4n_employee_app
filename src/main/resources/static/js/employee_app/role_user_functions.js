function getAllActiveEmployeesRequest(){
	
	printInRequestDataTag("");
	ajaxWithJWT("GET", "/rest-api/v1/employee/list-active", "", printInResponseDataTag);
}

function getFindEmployeeGUI(){
	
	var htmlContent = "<h4>Find Employee parameters:</h4>" + 
		"<span class='ui-widget'>" +
		"<label for='employeeId'>Employee Id: </label>" +
		"<input id='employeeId'>" +
		"</span>" +  
		"<span>" +
		"<button class='ui-button ui-widget ui-corner-all tab-left' onclick='getFindEmployeeRequest()'>Find Employee by Id</button>" + 
		"</span>";

	printInRequestDataTag(htmlContent);
}

function getFindEmployeeRequest(){
	
	var id = document.getElementById("employeeId").value;
	ajaxWithJWT("GET", "/rest-api/v1/employee/" + id, "", printInResponseDataTag);
}


function postEmployeeGUI(){
	
	var htmlContent = "<h4>Create New Employee parameters:</h4>" + 
	"<span>" +
	"<textarea rows=10 cols=50 id='employeeInfo'>" +
	'{' +
	'\n\t"firstName": "",' +
	'\n\t"middleInitial": "",' +
	'\n\t"lastName": "",' +
	'\n\t"dateOfBirth": "1980-12-19",' +
	'\n\t"dateOfEmployment": "2019-11-10"' +
	'\n}' +
	"</textarea>" +  
	"<span style='vertical-align: top'>" +
	"<button class='ui-button ui-widget ui-corner-all tab-left' onclick='postEmployeeRequest()'>Submit request</button>" + 
	"</span>";

	printInRequestDataTag(htmlContent);
}

function postEmployeeRequest(){
	
	var JSONdata = JSON.parse(document.getElementById("employeeInfo").value);
	ajaxWithJWT("POST", "/rest-api/v1/employee", JSONdata, printInResponseDataTag);
}


function putUpdateEmployeeGUI(){
	var htmlContent = "<h4>Update Employee parameters:</h4>" + 
	"<span>" +
	"<textarea rows=10 cols=50 id='employeeInfo'>" +
	'{' +
	'\n\t"id": "",' +
	'\n\t"firstName": "",' +
	'\n\t"middleInitial": "",' +
	'\n\t"lastName": "",' +
	'\n\t"dateOfBirth": "1980-12-19",' +
	'\n\t"dateOfEmployment": "2019-11-10"' +
	'\n}' +
	"</textarea>" +  
	"<span style='vertical-align: top'>" +
	"<button class='ui-button ui-widget ui-corner-all tab-left' onclick='putUpdateEmployeeRequest()'>Submit request</button>" + 
	"</span>";

	printInRequestDataTag(htmlContent);
}

function putUpdateEmployeeRequest(){
	
	var JSONdata = JSON.parse(document.getElementById("employeeInfo").value);
	ajaxWithJWT("PUT", "/rest-api/v1/employee", JSONdata, printInResponseDataTag);
}
