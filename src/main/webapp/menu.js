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


function goToIndex(){
    window.location.href = "index.html";
}