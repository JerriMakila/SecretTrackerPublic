async function processForm(event){
    event.preventDefault();
    const locationId = Number(getLocationId());
    const form = document.getElementById("secret-form");
    const formData = new FormData(form);
    const imageUploaded = hasImages();

    if(imageUploaded){
        processImage(formData, locationId);
    }else{
        buildRequest(formData, locationId);
    }
}

async function buildRequest(formData, locationId){
    const requestBody = processFormData(formData, locationId);
    const response = await submitForm(JSON.stringify(requestBody));
    console.log(response);

    if(response.status === "201"){
        document.location.href = `../secretsbylocation/${locationId}`;
    }
}

function getLocationId(){
    const pathname = window.location.pathname;
    pathVariables = pathname.split("/");

    return pathVariables[2];
}

function processFormData(formData, locationId){
    let returnBody = {};

    for(let [key, value] of formData){
        if(value === ''){
            value = null;
        }
    
        returnBody[key] = value;
    }

    returnBody.locationId = locationId;

    return returnBody;
}

async function submitForm(postBody){
    const data = await fetch(`${baseUrl}secrets`, {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        body: postBody, // body data type must match "Content-Type" header
        headers: {
            'Content-Type': 'application/json'
        }
    });

    const response = await data.json();
    return response;
}

function hasImages(){
    return document.getElementById("image").files.length > 0;
}

// This bit of code copied from 'https://www.geeksforgeeks.org/how-to-convert-image-into-base64-string-using-javascript/'
async function processImage(formData, locationId){
    const image = document.getElementById("image").files[0];
    const reader = new FileReader();

    reader.onload = function(){
        const base64Str = reader.result.replace("data:", "").replace(/^.+,/, "");
        formData.append("image", base64Str)
        buildRequest(formData, locationId);
    }

    reader.readAsDataURL(image);
}