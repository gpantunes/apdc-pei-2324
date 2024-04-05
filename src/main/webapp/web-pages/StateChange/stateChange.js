document.getElementById("stateChangeButton").addEventListener("click", function() {
    let usernameToChange = document.getElementById("username").value;
    let checkbox = document.getElementById("newState").value;
    let state = checkbox.checked;

    console.log("tentativa mudar o estado");

    fetch('/rest/state_change/v1', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify({
            usernameToChange: usernameToChange,
            state: state
        })
    })
        .then(response => {
            if (!response.ok) {
                console.log(response.text());
                throw new Error('Network response was not ok');
            }else{
                console.log(response.text());
                console.log("state change successful")
            }
        })
        .catch(error => {
            // Handle login error
            console.error('Error during state change:', error);
            // Display error message to the user or perform other actions as needed
        });
});



document.getElementById("changePageButton").addEventListener("click", function() {
    window.location.href = "../menu.html";
});