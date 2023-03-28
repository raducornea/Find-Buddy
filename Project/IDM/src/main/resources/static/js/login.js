import Theme from './Theme.js';
import Cookie from './Cookie.js';


export default class Login {

    static checkLoginInputs() {

        const username = document.getElementById("login_username").value;
        const password = document.getElementById("login_password").value;
        const login_submit = document.getElementById("login_submit");
        
        const theme = Cookie.getCookie('darkThemeCookie');
        
        if (theme === "" || theme === "white") {
            if (password === "" || username === "") {
                login_submit.disabled = true;
                login_submit.classList.remove("login-input-with-cursor-dark-theme");
                login_submit.classList.add("login-input-with-cursor-light-theme");
            } else {
                login_submit.classList.remove("login-input-with-cursor-light-theme");
                login_submit.classList.remove("login-input-with-cursor-dark-theme");
                login_submit.disabled = false;
            }
        } else {
            if (password === "" || username === "") {
                login_submit.disabled = true;
                login_submit.classList.remove("login-input-with-cursor-light-theme");
                login_submit.classList.add("login-input-with-cursor-dark-theme");
            } else {
                login_submit.classList.remove("login-input-with-cursor-light-theme");
                login_submit.classList.remove("login-input-with-cursor-dark-theme");
                login_submit.disabled = false;
            }
        }

    }
    
    static setLightTheme() {
        const containers = document.querySelectorAll(".login_container input[type='submit']");
        containers.forEach(container => {
            container.classList.remove("login-input-submit-dark-theme");
            container.classList.add("login-input-submit-light-theme");
        });

        const containerNotAMember = document.querySelector(".not_a_member");
        containerNotAMember.classList.remove("not_a_member-dark-theme");
        containerNotAMember.classList.add("not_a_member-light-theme");

        const containerSignupNow = document.querySelector(".signup_now");
        containerSignupNow.classList.remove("signup_now-dark-theme");
        containerSignupNow.classList.add("signup_now-light-theme");

        const containerForgotPassword = document.querySelector(".forgot_password");
        containerForgotPassword.classList.remove("forgot_password-dark-theme");
        containerForgotPassword.classList.add("forgot_password-light-theme");
        
        const containersLoginMessage = document.querySelectorAll(".login_message");
        containersLoginMessage.forEach(container => {
            container.classList.remove("login_message-dark-theme");
            container.classList.add("login_message-light-theme");
        });
    }
    
    static setDarkTheme() {
        const containersInputs = document.querySelectorAll(".login_container input[type='submit']");
        containersInputs.forEach(container => {
            container.classList.remove("login-input-submit-light-theme");
            container.classList.add("login-input-submit-dark-theme");
        });

        const containerNotAMember = document.querySelector(".not_a_member");
        containerNotAMember.classList.remove("not_a_member-light-theme");
        containerNotAMember.classList.add("not_a_member-dark-theme");

        const containerSignupNow = document.querySelector(".signup_now");
        containerSignupNow.classList.remove("signup_now-light-theme");
        containerSignupNow.classList.add("signup_now-dark-theme");

        const containerForgotPassword = document.querySelector(".forgot_password");
        containerForgotPassword.classList.remove("forgot_password-light-theme");
        containerForgotPassword.classList.add("forgot_password-dark-theme");

        const containersLoginMessage = document.querySelectorAll(".login_message");
        containersLoginMessage.forEach(container => {
            container.classList.remove("login_message-light-theme");
            container.classList.add("login_message-dark-theme");
        });
    }

    static setMode() {

        const theme = Cookie.getCookie('darkThemeCookie');
        document.getElementById("login_submit").disabled = true;

        if (theme === "" || theme === "white") {
            Theme.setLightTheme();
            Login.setLightTheme();
        } else {
            Theme.setDarkTheme();
            Login.setDarkTheme();
        }
        
        Login.checkLoginInputs();
    }

    static switchMode() {

        let theme = Cookie.getCookie('darkThemeCookie');

        if (theme === "" || theme === "white") {
            theme = "black";
            Theme.setDarkTheme();
            Login.setDarkTheme();
        } else {
            theme = "white";
            Theme.setLightTheme();
            Login.setLightTheme();
        }
    
        Cookie.setCookie('darkThemeCookie', theme, 7);
        Login.checkLoginInputs();
    }
}

window.onload = function() {
    Login.setMode();
};

// event listeners
document.getElementById('switch_theme_button-dark-theme').addEventListener('click', () => {
    Login.switchMode();
});

document.getElementById('switch_theme_button-light-theme').addEventListener('click', () => {
    Login.switchMode();
});

document.getElementById('login_username').addEventListener('input', () => {
    Login.checkLoginInputs();
});

document.getElementById('login_password').addEventListener('input', () => {
    Login.checkLoginInputs();
});

