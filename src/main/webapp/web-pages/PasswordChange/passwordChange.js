document.getElementById("changePasswordButton").addEventListener("click", function() {
    let currentPassword = document.getElementById("currentPassword").value;
    let newPassword = document.getElementById("newPassword").value;
    let confirmation = document.getElementById("confirmation").value;

    console.log("tentativa mudar password");

    fetch('/rest/password_change/v1', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify({
            currentPassword: currentPassword,
            newPassword: newPassword,
            confirmation: confirmation,
        })
    })
        .then(response => {
            if (!response.ok) {
                console.log(response.text());
                throw new Error('Network response was not ok');
            }else{
                console.log(response.text());
                console.log("password change successful")
            }
        })
        .catch(error => {
            // Handle login error
            console.error('Error during password change:', error);
            // Display error message to the user or perform other actions as needed
        });
});


document.getElementById("changePageButton").addEventListener("click", function() {
    window.location.href = "../menu.html";
});