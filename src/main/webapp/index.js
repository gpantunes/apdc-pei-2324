//login button
document.getElementById("loginButton").addEventListener("click", function() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    console.log(username + " " + password);

    fetch('/rest/login/v1', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify({
            username: username,
            password: password
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            // Handle successful login response
            console.log('Login successful');
            console.log(response);
            goToMenu();
            // Redirect to another page or perform other actions as needed
        })
        .catch(error => {
            // Handle login error
            console.error('Error during login:', error);
            // Display error message to the user or perform other actions as needed
        });
});


//register v3 button
document.getElementById("registerButton").addEventListener("click", function() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;


    console.log(username + " " + password);

    fetch('/rest/register/v3', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify({
            username: username,
            password: password
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            // Handle successful login response
            console.log('Registration successful');
            console.log(response);
            // Redirect to another page or perform other actions as needed
        })
        .catch(error => {
            // Handle login error
            console.error('Error during registration:', error);
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


function goToMenu(){
    window.location.href = "web-pages/menu.html";
}
