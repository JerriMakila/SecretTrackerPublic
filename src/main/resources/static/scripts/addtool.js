async function fetchToolTypes(){
    const response = await fetch(`${baseUrl}tooltypes`);
    const tooltypes = await response.json();

    return tooltypes;
}

function buildInnerHtmlStr(tooltypes){
    let returnStr = "";

    for(const tooltype of tooltypes){
        returnStr += `<option value="${tooltype.toolTypeId}">${tooltype.toolType}</option>`;
    }

    return returnStr;
}

async function showToolTypes(){
    const tooltypes = await fetchToolTypes();
    const tooltypesSelect = buildInnerHtmlStr(tooltypes);
    document.getElementById("tooltypeId").innerHTML = tooltypesSelect;
}

showToolTypes();