function viewJWTForSession(){

	printInRequestDataTag("");
	printInResponseDataTag(getTokenForSession());
}

function printInRequestDataTag(content){
	
	document.getElementById("request-data-tag").innerHTML = content;
}

function printInResponseDataTag(data){
	
	var formattedData = JSON.stringify(data, null, '\t');
	document.getElementById("response-data-tag").innerHTML = formattedData;
	console.log(formattedData);
}

function logoutApp(){
	
	$(location).attr('href', getcurrentURL() + '/login?logout');
}

function getcurrentURL(){
	
	var appContext   = document.getElementById("app-uri").value.split("/")[3];
	return "http://" + location.hostname + ":" + location.port + "/" + appContext;
}

function getTokenForSession(){
	
	var tokenJWT = null;
	var username = document.getElementById("client-id").value;
	var password = document.getElementById("client-secret").value;
	var appURI   = document.getElementById("app-uri").value;
	var params   = {"redirect_uri": appURI};
	
	printInResponseDataTag("Sending request for JWT, please wait...");
	
	$.ajax({
		headers: { 
			'Accept': 'application/json',
			'Content-Type': 'application/json' 
		},
		beforeSend: function (xhr) {
			xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
		},
		url: getcurrentURL() + '/oauth/token?grant_type=client_credentials',
		type: "POST",
		cache: false,
		async: false,
		data: JSON.stringify(params),
		dataType: "json",
		success: function( response ) {
			tokenJWT = response;
			console.log("Got new JWT as: " + tokenJWT);
		},
		error: function( response ) {
			tokenJWT = JSON.parse("{\"error\" : \"Failed Getting token from Backend\"}");
			console.log(tokenJWT.error);
		}

	});
	
	return tokenJWT;
}

function ajaxWithJWT(requestType, url, jsonObject, callBackFunction) {

	var tokenJWT = getTokenForSession();
	
	printInResponseDataTag("Sending " + requestType + " request, please wait...");
	
	console.log("  -> The JWT is: " + tokenJWT.access_token);

	$.ajax({
		beforeSend : function (xhr) { 
			xhr.setRequestHeader("Authorization", 'Bearer '+ tokenJWT.access_token);
		},
		complete : function () { $('.nav-button').removeAttr("disabled"); $('#content-table-div').scrollTop(0); },
		url: getcurrentURL() + url,
		headers: { "originator-id": tokenJWT.auth_username, "Content-Type": "application/json" },
		type: requestType,
		cache: false,
		async:false,
		dataType: "json",
		data: JSON.stringify(jsonObject),
		success: function(response) {
			callBackFunction(response);
		},
		error: function($xhr) {
			printInResponseDataTag( $xhr.responseJSON );
		}
	});
}
