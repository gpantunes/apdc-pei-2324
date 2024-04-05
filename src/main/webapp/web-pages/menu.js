//logout button
document.getElementById("logoutButton").addEventListener("click", function() {

    console.log("tentativa de logout");

    fetch('/rest/logout/v1', {
        method: 'POST'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            // Handle successful login response
            console.log('Logout successful');
            console.log(response);
            goToIndex();
            // Redirect to another page or perform other actions as needed
        })
        .catch(error => {
            // Handle login error
            console.error('Error during logout:', error);
            // Display error message to the user or perform other actions as needed
        });
});



//role change button
document.getElementById("roleChangeButton").addEventListener("click", function() {
    let usernameToChange = document.getElementById("username").value;
    let role = document.getElementById("newRole").value;

    console.log("tentativa de mudar role");

    fetch('/rest/role_change/v1', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify({
            usernameToChange: usernameToChange,
            role: role
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            // Handle successful login response
            console.log(response.text());
        })
        .catch(error => {
            // Handle login error
            console.error('Error during print:', error);
            // Display error message to the user or perform other actions as needed
        });
});



//print token button
document.getElementById("stateChangeButton").addEventListener("click", function() {

    console.log("tentativa de mudar estado");

    fetch('/rest/state_change/v1', {
        method: 'POST'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            // Handle successful login response
            console.log(response.text());
        })
        .catch(error => {
            // Handle login error
            console.error('Error during print:', error);
            // Display error message to the user or perform other actions as needed
        });
});



//print token button
document.getElementById("printTokenButton").addEventListener("click", function() {

    console.log("tentativa de print");

    fetch('/rest/print_token/v1', {
        method: 'POST'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            // Handle successful login response
            console.log(response.text());
        })
        .catch(error => {
            // Handle login error
            console.error('Error during print:', error);
            // Display error message to the user or perform other actions as needed
        });
});


//move to the password change page
document.getElementById("passwordChangeButton").addEventListener("click", function() {
    window.location.href = "PasswordChange/passwordChange.html";
});

//move to the state change page
document.getElementById("stateChangeButton").addEventListener("click", function() {
    window.location.href = "StateChange/stateChange.html";
});

//move to the user removal page
document.getElementById("deleteUserButton").addEventListener("click", function() {
    window.location.href = "UserRemoval/userRemoval.html";
});


function goToIndex(){
    window.location.href = "../index.html";
}