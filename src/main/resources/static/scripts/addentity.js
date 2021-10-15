const baseUrl = "http://localhost:8080/";
let pathVariables;

function getPathVariable(){
    const pathname = window.location.pathname;
    pathVariables = pathname.split("/");
}

async function processForm(event, endpoint, formId){
    event.preventDefault();
    getPathVariable();
    const form = document.getElementById(formId);
    const formData = new FormData(form);
    let body = {};

    for(const [key, value] of formData){
        body[key] = value;
    }

    if(pathVariables.length > 2){
        body.locationId = Number(pathVariables[2]);
    }

    const postBody = JSON.stringify(body);
    const response = await submitForm(postBody, endpoint);
    console.log(response);

    if(response.status === "201"){
        document.location.href = `/locationlist`;
    }
}

async function submitForm(postBody, endpoint){
    const data = await fetch(`${baseUrl}${endpoint}`, {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        body: postBody, // body data type must match "Content-Type" header
        headers: {
            'Content-Type': 'application/json'
        }
    });

    const response = await data.json();
    return response;
}

