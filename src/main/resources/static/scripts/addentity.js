// This function is called from html
async function processForm(event, endpoint, formId){
    event.preventDefault();
    const formData = getFormData(formId);
    const requestBody = createRequestBody(formData);
    const response = await submitForm(requestBody, endpoint);
    let errorMsgContainer = document.getElementById("error-msg");

    if(response === null){
        errorMsgContainer.innerHTML = "Something went wrong, please try again";
    }else{
        if(response.status === "201"){
            document.location.href = `/locationlist`;
            errorMsgContainer = "";
        }else{
            errorMsgContainer = response.message;
        }
    }
}

function getFormData(formId){
    const form = document.getElementById(formId);
    const formData = new FormData(form);

    return formData;
}

function createRequestBody(formData){
    let body = {};

    for(let [key, value] of formData){
        if(value === ''){
            value = null;
        }

        body[key] = value;
    }

    const requestBody = JSON.stringify(body);
    return requestBody;
}

async function submitForm(postBody, endpoint){
    let data;
    let response;

    try{
        data = await fetch(`${baseUrl}${endpoint}`, {
            method: 'POST',
            body: postBody,
            headers: {
                'Content-Type': 'application/json'
            }
        });

        response = await data.json();
    }catch{
        response = null;
    }
    
    return response;
}

