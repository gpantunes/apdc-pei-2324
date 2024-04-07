document.getElementById("stateChangeButton").addEventListener("click", function() {
    let usernameToChange = document.getElementById("username").value;
    let attributeToChange = document.getElementById("attributeToChange").value;
    let attributeValue = document.getElementById("attributeValue").value;

    console.log("tentativa mudar o atributo: " + attributeName);

    fetch('/rest/attribute_change/v1', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify({
            usernameToChange: usernameToChange,
            attributeToChange: attributeToChange,
            attributeValue: attributeValue
        })
    })
        .then(response => {
            if (!response.ok) {
                console.log(response.text() + " " + attributeValue);
                throw new Error('Network response was not ok');
            }else{
                console.log(response.text() + " " + attributeValue);
                console.log("attribute change successful")
            }
        })
        .catch(error => {
            // Handle login error
            console.error('Error during attribute change:', error);
            // Display error message to the user or perform other actions as needed
        });
});



document.getElementById("changePageButton").addEventListener("click", function() {
    window.location.href = "../menu.html";
});