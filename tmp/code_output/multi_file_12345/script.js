// script.js
document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');

    loginForm.addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent form from submitting normally

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        // Simulate form validation or API call
        if (username === 'user' && password === 'pass') {
            alert('Login successful!');
            // Redirect or update UI as needed
        } else {
            alert('Invalid username or password');
        }
    });
});
