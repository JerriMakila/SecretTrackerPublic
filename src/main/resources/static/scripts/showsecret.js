async function showsecret(){
    const secretId = getSecretId();
    const secret = await fetchSecret(secretId);
    const imageBase64 = secret.image;
    setImage(imageBase64);
    setLinkHref(secret.locationId.locationId);
}

function getSecretId(){
    const pathname = window.location.pathname;
    const pathArray = pathname.split("/");
    const secretId = pathArray[2];

    return secretId;
}

async function fetchSecret(secretId){
    const response = await fetch(`${baseUrl}secrets/${secretId}`);
    const secret = await response.json();
    return secret;
}

function setImage(imageBase64){
    let imgElement = document.getElementById("secret-image");
    imgElement.src = `data:image/png;base64, ${imageBase64}`;
}

function setLinkHref(locationId){
    const aElement = document.getElementById("link-to-secrets");
    aElement.href = `${baseUrl}secretsbylocation/${locationId}`;
}

showsecret();