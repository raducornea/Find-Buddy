import Theme from './Theme.js';

export default class Login {

    static checkLoginInputs() {

        const username = document.getElementById("login_username").value;
        const password = document.getElementById("login_password").value;
        const login_submit = document.getElementById("login_submit");
    
        if (password === "" || username === "")
            login_submit.disabled = true;
        else
            login_submit.disabled = false;
    }
    
    static switchMode() {
        Theme.switchMode();
    }
}

// event listeners
document.getElementById('switch_theme_button').addEventListener('click', () => {
    Login.switchMode();
});

document.getElementById('login_username').addEventListener('oninput', () => {
    Login.checkLoginInputs();
});

document.getElementById('login_password').addEventListener('oninput', () => {
    Login.checkLoginInputs();
});