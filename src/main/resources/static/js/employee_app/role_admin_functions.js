function getAllInactiveEmployeesRequest(){
	
	printInRequestDataTag("");
	ajaxWithJWT("GET", "/rest-api/v1/employee/list-inactive", "", printInResponseDataTag);
}


function patchEmployeeActiveStatusGUI(activeStatus){
	
	var selectedOption = (activeStatus) ? "ACTIVATE" : "DEACTIVATE";
	
	var htmlContent = "<h4>" + selectedOption + " Employee parameters:</h4>" + 
	"<span class='ui-widget'>" +
	"<label for='employeeId'>Employee Id: </label>" +
	"<input id='employeeId'>" +
	"</span>" +  
	"<span>" +
	"<button class='ui-button ui-widget ui-corner-all tab-left' onclick='patchEmployeeActiveStatusRequest(" + activeStatus + ")'>" + selectedOption + " Employee</button>" + 
	"</span>";

	printInRequestDataTag(htmlContent);
}

function patchEmployeeActiveStatusRequest(activeStatus){
	
	var id = document.getElementById("employeeId").value;
	ajaxWithJWT("PATCH", "/rest-api/v1/employee/" + id + "/" + activeStatus, "", printInResponseDataTag);
}


function deleteEmployeeGUI(){
	
	var htmlContent = "<h4>Delete Employee parameters:</h4>" + 
	"<span class='ui-widget'>" +
	"<label for='employeeId'>Employee Id: </label>" +
	"<input id='employeeId'>" +
	"</span>" +  
	"<span>" +
	"<button class='ui-button ui-widget ui-corner-all tab-left' onclick='deleteEmployeeGUIRequest()'>Delete Employee</button>" + 
	"</span>";

printInRequestDataTag(htmlContent);
}

function deleteEmployeeGUIRequest(){
	
	var id = document.getElementById("employeeId").value;
	ajaxWithJWT("DELETE", "/rest-api/v1/employee/" + id, "", printInResponseDataTag);
}
