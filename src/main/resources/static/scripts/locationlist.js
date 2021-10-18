async function getLocations(){
    const data = await fetch(`${baseUrl}locations`)
    const locations = await data.json();
    return locations;
}

async function showLocations(){
    const locations = await getLocations();
    let locationsTable = "";

    locations.forEach(location => {
        locationName = location.location;
        locationsTable += `<tr><td>${location.location}</td><td><a class="btn btn-primary" href=${baseUrl}secretsbylocation/${location.locationId}>secrets</a></td></tr>`
    });
    document.getElementById("location-table").innerHTML = locationsTable;

}

//getLocations();
showLocations();