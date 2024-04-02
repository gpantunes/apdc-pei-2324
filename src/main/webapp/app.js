function login() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    let loginData = {
        username: username,
        password: password
    };

    console.log(loginData.username + " " + loginData.password);

    fetch('/rest/login/v1', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
                console.log("não ok")
            }
            // Handle successful login response
            console.log('Login successful');
            // Redirect to another page or perform other actions as needed
        })
        .catch(error => {
            // Handle login error
            console.error('Error during login:', error);
            // Display error message to the user or perform other actions as needed
        });
}



document.getElementById("loginButton").addEventListener("click", function() {
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
            // Redirect to another page or perform other actions as needed
        })
        .catch(error => {
            // Handle login error
            console.error('Error during registration:', error);
            // Display error message to the user or perform other actions as needed
        });
});



document.getElementById("registerButton").addEventListener("click", function() {
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
            // Redirect to another page or perform other actions as needed
        })
        .catch(error => {
            // Handle login error
            console.error('Error during login:', error);
            // Display error message to the user or perform other actions as needed
        });
});