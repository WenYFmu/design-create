// script.js
document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();
    
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    
    // Simple validation
    if (username === '' || password === '') {
        alert('Please enter both username and password.');
        return;
    }
    
    // Here you would typically send the data to the server for authentication
    // For this example, we'll just log the values to the console
    console.log('Username:', username);
    console.log('Password:', password);
    
    // Clear the form
    this.reset();
});
