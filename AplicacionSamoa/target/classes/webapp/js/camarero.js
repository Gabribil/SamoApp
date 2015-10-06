/**
 * 
 */

var tableData;
var productData;

var currentSelectedTable = 0;
var currentSelectedProduct = -1;
var showingErrorWindow = false;

function sendMessage(request, method, callback, data) {
	var xhr = new XMLHttpRequest();
	xhr.open(method, request, false);
	var response;

	// Track the state changes of the request.
	xhr.onreadystatechange = function () {
	    var DONE = 4; // readyState 4 means the request is done.
	    var OK = 200; // status 200 is a successful return.
	    if (xhr.readyState === DONE) {
	        if (xhr.status === OK) {
	        	if(showingErrorWindow == true){
	        		showingErrorWindow = false;
	        		hideConnectionError();
	        	}
	            callback(JSON.parse(xhr.response));
	        } else {
	            //alert('Error: ' + xhr.status); // An error occurred during the request.
	            showConnectionError();
	            showingErrorWindow = true;
	        }
	    }
	};

	
	xhr.send(data);
}

function showConnectionError() {
	document.getElementById("errorDiv").style.animationName="background-fade-in";
	document.getElementById("errorMessage").style.animationName="letter-fade-in";
	document.getElementById("errorDiv").style.display="";
	document.getElementById("errorMessage").style.display="";
}

function hideConnectionError() {
	document.getElementById("errorDiv").style.animationName="background-fade-out";
	document.getElementById("errorMessage").style.animationName="letter-fade-out";
	setTimeout('document.getElementById("errorDiv").style.display="none"', 500);
	setTimeout('document.getElementById("errorMessage").style.display="none"', 500);
}

function showDropDownList() {
	document.getElementById("tables").style.display="";
	document.getElementById("products").style.display="none";
}

function hideDropDownList() {
	document.getElementById("tables").style.display="none";
	document.getElementById("products").style.display="";
}

function refreshTableData() {
	sendMessage('getTables', 'get', function(receivedData) {
		var i;
		var parent = document.getElementById('tables');
		
		tableData = receivedData;
		
		document.getElementById('dropdownMenu1').innerHTML=tableData[currentSelectedTable].tableName+' <span class="caret-down"></span>';
		
		parent.innerHTML='';
		
		for(i = 0; i<currentSelectedTable; i++) {
			var node = document.createElement("div");
			node.setAttribute("class", "box");
			node.setAttribute("onclick", "changeToTable("+i+")")
			node.innerHTML=tableData[i].tableName;
			parent.appendChild(node);
		}
		
		for(i = i+1; i<tableData.length; i++){
			var node = document.createElement("div");
			node.setAttribute("class", "box");
			node.setAttribute("onclick", "changeToTable("+i+")")
			node.innerHTML=tableData[i].tableName;
			parent.appendChild(node);
		}
		
	});
	
	
}

function refreshDrinkingsData(){
	sendMessage('getDrinkings?tableId='+tableData[currentSelectedTable].tableId, 'get', function(receivedData) {
		// Reset all quantities
		for(var i = 0; i<productData.length; i++){
			document.getElementById("product"+productData[i].productId+"ActualQuantity").innerHTML = '0';
		}
		
		for(var i = 0; i<receivedData.length; i++){
			document.getElementById("product"+receivedData[i].productId+"ActualQuantity").innerHTML= receivedData[i].quantity;
		}
	
	});
}

function changeToTable(tableIndex){
	
	hideDropDownList();
	
	currentSelectedTable = tableIndex;
	
	refreshTableData();
	
	for(var i = 0;i<productData.length; i++){
		document.getElementById("product"+productData[i].productId+"DeltaQuantity").innerHTML = '+0';
		productData[i].deltaQuantity=0;
	}
	
	refreshDrinkingsData();
	
}

function changeSelectedProduct(newProductId){
	if(currentSelectedProduct != -1){
		var node = document.getElementById("product"+productData[currentSelectedProduct].productId);
		node.setAttribute("style", "background-color: white;");
	}
	
	if(newProductId != -1){
		var node = document.getElementById("product"+productData[newProductId].productId);
		node.setAttribute("style", "background-color: lightblue;");
	}
	
	currentSelectedProduct = newProductId;
}

function refreshProductTable(){
	sendMessage('getProducts', 'get', function(receivedData){
		
		productData = receivedData;
		
		// Refresh webpage
		var parent = document.getElementById("products");
		
		for(var i=0;i<productData.length;i++){
			var node = document.createElement("div");
			node.setAttribute("class", "box");
			node.setAttribute("id", "product"+productData[i].productId);
			node.setAttribute("onclick", "changeSelectedProduct("+productData[i].productId+")")
			node.innerHTML=productData[i].name+'<br>(<span id="product'+productData[i].productId+'ActualQuantity"></span>|<span id="product'+productData[i].productId+'DeltaQuantity"></span>)';
			parent.appendChild(node);
		}
	});
}

function sendNewDrinkingsData(){
	var dataToSend = {};
	
	dataToSend.tableId = tableData[currentSelectedTable].tableId;
	dataToSend.newDrinkings = []
	
	for(var i=0; i<productData.length; i++){
		if(productData[i].deltaQuantity!=0) dataToSend.newDrinkings.push({productId:productData[i].productId, quantity:productData[i].deltaQuantity})
	}
	
	console.log(JSON.stringify(dataToSend));
	
	sendMessage ('setDrinkings', 'post', function(receivedData){
		//if(response=OK){
		changeToTable(currentSelectedTable);
		//} else {
		//Mostrar_error();
		//}
	}, JSON.stringify(dataToSend))
	
	
}

function addDrinking(){
	if(currentSelectedProduct != -1){
		var node = document.getElementById("product"+productData[currentSelectedProduct].productId+"DeltaQuantity");
		productData[currentSelectedProduct].deltaQuantity = productData[currentSelectedProduct].deltaQuantity+1;
		if(productData[currentSelectedProduct].deltaQuantity < 0) node.innerHTML = productData[currentSelectedProduct].deltaQuantity;
		else node.innerHTML = '+'+productData[currentSelectedProduct].deltaQuantity;
	}
}

function deleteDrinking(){
	if(currentSelectedProduct != -1){
		var node = document.getElementById("product"+productData[currentSelectedProduct].productId+"DeltaQuantity");
		productData[currentSelectedProduct].deltaQuantity = productData[currentSelectedProduct].deltaQuantity-1;
		if(productData[currentSelectedProduct].deltaQuantity < 0) node.innerHTML = productData[currentSelectedProduct].deltaQuantity;
		else node.innerHTML = '+'+productData[currentSelectedProduct].deltaQuantity;
	}
}

function cleanTable() {
	sendMessage ('cleanTable?tableId='+tableData[currentSelectedTable].tableId, 'get', function(receivedData) {
		//if(response=OK){
		changeToTable(currentSelectedTable);
		//} else {
		//Mostrar_error();
		//}
	});
}

function init_camarero() {
	hideDropDownList();
	document.getElementById("errorDiv").style.display="none";
	refreshProductTable();
	refreshTableData();
	changeToTable(currentSelectedTable);
		
	setInterval(refreshDrinkingsData, 1000);
}