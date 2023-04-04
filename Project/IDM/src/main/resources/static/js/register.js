import Theme from './Theme.js';
import Cookie from './Cookie.js';


export default class Register {

    static validateInputs(username, password, password_confirm) {
        if (username === "" || password === "" || password_confirm === "") return false;
        if (password != password_confirm) return false;
        return true;
    }

    static checkRegisterInputs() {

        const username = document.getElementById("register_username").value;
        const password = document.getElementById("register_password").value;
        const confirm_password = document.getElementById("register_password_confirm").value;
        const theme = Cookie.getCookie('darkThemeCookie');
        const valid = this.validateInputs(username, password, confirm_password);

        const register_submit = document.getElementById("register_submit");
        if (theme === "" || theme === "white") {
            if (!valid) {
                register_submit.disabled = true;
                register_submit.classList.remove("register-input-with-cursor-dark-theme");
                register_submit.classList.add("register-input-with-cursor-light-theme");
            } else {
                register_submit.classList.remove("register-input-with-cursor-light-theme");
                register_submit.classList.remove("register-input-with-cursor-dark-theme");
                register_submit.disabled = false;
            }
        } else {
            if (!valid) {
                register_submit.disabled = true;
                register_submit.classList.remove("register-input-with-cursor-light-theme");
                register_submit.classList.add("register-input-with-cursor-dark-theme");
            } else {
                register_submit.classList.remove("register-input-with-cursor-light-theme");
                register_submit.classList.remove("register-input-with-cursor-dark-theme");
                register_submit.disabled = false;
            }
        }
    }
    
    static setLightTheme() {
        const containers = document.querySelectorAll(".register_container input[type='submit']");
        containers.forEach(container => {
            container.classList.remove("register-input-submit-dark-theme");
            container.classList.add("register-input-submit-light-theme");
        });

        const labelForInputs = document.querySelectorAll(".register_container .label_for_input");
        labelForInputs.forEach(label => {
            label.classList.remove("label_for_input-dark-theme");
            label.classList.add("label_for_input-light-theme");
        });

        const labelForHintOrError = document.querySelectorAll(".register_container .label_for_hint_or_error");
        labelForHintOrError.forEach(label => {
            label.classList.remove("label_for_hint_or_error-dark-theme");
            label.classList.add("label_for_hint_or_error-light-theme");
        });

        const containerAlreadyMember = document.querySelector(".already_member");
        containerAlreadyMember.classList.remove("already_member-dark-theme");
        containerAlreadyMember.classList.add("already_member-light-theme");

        const containerSignupNow = document.querySelector(".login_now");
        containerSignupNow.classList.remove("login_now-dark-theme");
        containerSignupNow.classList.add("login_now-light-theme");
    }
    
    static setDarkTheme() {
        const containersInputs = document.querySelectorAll(".register_container input[type='submit']");
        containersInputs.forEach(container => {
            container.classList.remove("register-input-submit-light-theme");
            container.classList.add("register-input-submit-dark-theme");
        });

        const labelForInputs = document.querySelectorAll(".register_container .label_for_input");
        labelForInputs.forEach(label => {
            label.classList.remove("label_for_input-light-theme");
            label.classList.add("label_for_input-dark-theme");
        });

        const labelForHintOrError = document.querySelectorAll(".register_container .label_for_hint_or_error");
        labelForHintOrError.forEach(label => {
            label.classList.remove("label_for_hint_or_error-light-theme");
            label.classList.add("label_for_hint_or_error-dark-theme");
        });

        const containerAlreadyMember = document.querySelector(".already_member");
        containerAlreadyMember.classList.remove("already_member-light-theme");
        containerAlreadyMember.classList.add("already_member-dark-theme");

        const containerSignupNow = document.querySelector(".login_now");
        containerSignupNow.classList.remove("login_now-light-theme");
        containerSignupNow.classList.add("login_now-dark-theme");
    }

    static setMode() {

        const theme = Cookie.getCookie('darkThemeCookie');
        document.getElementById("register_submit").disabled = true;

        if (theme === "" || theme === "white") {
            Theme.setLightTheme();
            Register.setLightTheme();
        } else {
            Theme.setDarkTheme();
            Register.setDarkTheme();
        }
        
        Register.checkRegisterInputs();
    }

    static switchMode() {

        let theme = Cookie.getCookie('darkThemeCookie');

        if (theme === "" || theme === "white") {
            theme = "black";
            Theme.setDarkTheme();
            Register.setDarkTheme();
        } else {
            theme = "white";
            Theme.setLightTheme();
            Register.setLightTheme();
        }
    
        Cookie.setCookie('darkThemeCookie', theme, 7);
        Register.checkRegisterInputs();
    }
}

window.onload = function() {
    document.body.style.opacity = '1';
    Register.setMode();
};

// event listeners
document.getElementById('switch_theme_button-dark-theme').addEventListener('click', () => {
    Register.switchMode();
});

document.getElementById('switch_theme_button-light-theme').addEventListener('click', () => {
    Register.switchMode();
});

document.getElementById('register_username').addEventListener('input', () => {
    Register.checkRegisterInputs();
});

document.getElementById('register_password').addEventListener('input', () => {
    Register.checkRegisterInputs();
});

document.getElementById('register_password_confirm').addEventListener('input', () => {
    Register.checkRegisterInputs();
});

// const avatarInput = document.getElementById('avatar');
// const avatarPreview = document.getElementById('avatar-preview');

// avatarInput.addEventListener('change', function() {
//     const file = this.files[0];
//     const reader = new FileReader();

//     reader.addEventListener('load', function() {
//         avatarPreview.src = reader.result;
//     });

//     reader.readAsDataURL(file);
// });