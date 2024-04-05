document.getElementById("deleteUserButton").addEventListener("click", function() {
    let usernameToChange = document.getElementById("username").value;

    console.log("tentativa de apagar user");

    fetch('/rest/remove_user/v1', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify({
            usernameToChange: usernameToChange,
        })
    })
        .then(response => {
            if (!response.ok) {
                console.log(response.text());
                throw new Error('Network response was not ok');
            }else{
                console.log(response.text());
                console.log("user removal successful")
            }
        })
        .catch(error => {
            // Handle login error
            console.error('Error during user removal:', error);
            // Display error message to the user or perform other actions as needed
        });
});



document.getElementById("changePageButton").addEventListener("click", function() {
    window.location.href = "../menu.html";
});