const baseUrl = "http://localhost:8080/";

function getPathVariable(){
    const pathname = window.location.pathname;
    const pathArray = pathname.split("/");
    const locationId = pathArray[2];

    return locationId;
}

async function getSecretsByLocation(locationId){
    const data = await fetch(`${baseUrl}locations/${locationId}/secrets`);
    const secrets = await data.json();
    console.log(secrets);

    return secrets;
}

async function setAsCleared(secretId){
    const data = await fetch(`${baseUrl}secrets/${secretId}`, {
        method: 'PATCH'
    });

    const response = await data.json();

    if(response.status === "200"){
        document.getElementById("error-msg").innerHTML = "";
        showSecrets();
    }else{
        document.getElementById("error-msg").innerHTML = "Something went wrong, please try again";
    }
}

async function showSecrets(){
    const locationId = getPathVariable();
    let secretTable = "";

    if(isNaN(Number(locationId))){
        secretTable = "<tr><td colspan='4'>Tietoja ei voitu hakea</td></tr>";
    }else{
        const secrets = await getSecretsByLocation(locationId);

        secrets.forEach(secret => {
            console.log(secret);
            const cleared = secret.cleared === false ? "No" : "Yes";
            const tool = secret.toolId !== null ? `<td>${secret.toolId.tool}</td>` : `<td>Unknown</td>`;
            const setAsCleared = secret.cleared === false ? `<td><button class="btn btn-success" onclick='setAsCleared(${secret.secretId})'>Set as cleared</button></td>` : `<td></td>`;
            const hasImage = secret.image === null ? `<td></td>` : `<td><a class="btn btn-primary" href="/showsecret/${secret.secretId}">Show</a></td>`;
            secretTable += `<tr><td>${secret.secret}</td>`
                + `<td>${cleared}</td>`
                + `${tool}`
                + `${setAsCleared}`
                + `${hasImage}`
                + `<td><a class="btn btn-warning" href="/modifysecret/${secret.secretId}">Modify</a></td>`
                + `<td><button class="btn btn-danger" onClick='deleteSecret(${secret.secretId})'>Delete</button</td></tr>`;
        });
    }
   
    document.getElementById("secret-table").innerHTML = secretTable;
}

function setHref(){
    const locationId = getPathVariable();
    document.getElementById("add-secret").href += `/${locationId}`
}

async function deleteSecret(secretId){
    const data = await fetch(`${baseUrl}secrets/${secretId}`, {
        method: 'DELETE'
    });

    showSecrets();
}



showSecrets();
setHref();

