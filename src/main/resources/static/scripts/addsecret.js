async function processForm(event){
    event.preventDefault();
    const form = document.getElementById("location-form");
    const formData = new FormData(form);
    let body = {};

    for(const [key, value] of formData){
        body[key] = value;
    }

    const postBody = JSON.stringify(body);
    const response = await submitForm(postBody);

    if(response.status === "201"){
        document.location.href = "../locationlist";
    }
}

async function submitForm(postBody){
    const data = await fetch('http://localhost:8080/locations', {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        body: postBody, // body data type must match "Content-Type" header
        headers: {
            'Content-Type': 'application/json'
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
    });

    const response = await data.json();
    return response;
}