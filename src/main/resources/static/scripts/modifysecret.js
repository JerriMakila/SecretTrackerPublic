const baseUrl = 'http://localhost:8080/';
let secretDto = {};

async function getLocations(){
    const response = await fetch(`${baseUrl}/locations`);
    const locations = await response.json();
    let locationsSelect = "";

    for(const location of locations){
        locationsSelect += `<option value=${location.locationId}>${location.location}</option>`;
    }

    document.getElementById("locationId").innerHTML = locationsSelect;
}

async function getTools(){
    const response = await fetch(`${baseUrl}/tools`);
    const tools = await response.json();
    let toolsSelect = "<option>Unknown</option>";

    for(const tool of tools){
        toolsSelect += `<option value="${tool.toolId}">${tool.tool}</option`;
    }

    document.getElementById("toolId").innerHTML = toolsSelect;
}

async function getSecret(){
    const id = getPathVariable();

    const response = await fetch(`${baseUrl}secrets/${id}`);
    const secret = await response.json();

    console.log(secret);

    secretDto.secret = secret.secret;
    secretDto.locationId = secret.locationId.locationId.toString();

    if(secret.toolId){
        secretDto.toolId = secret.toolId.toolId.toString();
    }else{
        secretDto.toolId = '';
    }

    document.getElementById("secret").value = secretDto.secret;
    document.getElementById("locationId").value = secretDto.locationId;
    document.getElementById("toolId").value = secretDto.toolId;
}

async function processForm(event){
    event.preventDefault();
    const id = getPathVariable();
    const form = document.getElementById("secret-modification");
    const formData = new FormData(form);
    let body = {};

    for(const [key, value] of formData){
        if(key === "locationId" || key === "toolId"){
            body[key] = Number(value);
        }else{
            body[key] = value;
        }
    }

    const request = JSON.stringify(body);
    const response = await submitForm(request, id);
}

async function submitForm(request, id){
    const data = await fetch(`${baseUrl}secrets/${id}`, {
        method: 'PUT', // *GET, POST, PUT, DELETE, etc.
        body: request, // body data type must match "Content-Type" header
        headers: {
            'Content-Type': 'application/json'
        }
    });

    const response = await data.json();
    
    if(response.status === "200"){
        document.location.href = `/secretsbylocation/${secretDto.locationId}`;
    }
}

function getPathVariable(){
    const pathname = window.location.pathname;
    pathArr = pathname.split("/");
    return pathArr[2];
}

getLocations();
getTools();
getSecret();