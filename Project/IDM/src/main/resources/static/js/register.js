import Theme from './Theme.js';
import Cookie from './Cookie.js';


export default class Register {

    static debounceTimer;

    static checkUsernameBackend(username) {
        if (!username) return Promise.resolve(false);
        if (Register.debounceTimer) clearTimeout(Register.debounceTimer);
      
        // Return a Promise that resolves with a boolean value
        return new Promise((resolve, reject) => {
            Register.debounceTimer = setTimeout(() => {
            fetch(`/check-username/${username}`)
              .then(response => response.json())
              .then(data => {
                resolve(data["available"]);
              })
              .catch(error => {
                console.error(error);
                reject(error);
              });
          }, 250);
        });
    }

    static applyStyleSheetToInput(input, style) {
        if (style === "red"){
            input.classList.remove("focus");
            input.classList.remove("valid");
            input.classList.add("invalid");
            input.style.border="1px solid red";
        } else if (style === "green") {
            input.classList.remove("focus");
            input.classList.remove("invalid");
            input.classList.add("valid");
            input.style.border="1px solid green";
        } else {
            input.classList.remove("invalid");
            input.classList.remove("valid");
            input.classList.add("focus");
            input.style.border="1px solid blue";
        }
    }

    static async checkUsername(onlyCheck) {
        const inputUsername = document.getElementById("register_username");
        const username = inputUsername.value;
        const usernameRegex = /^[a-zA-Z0-9_.]+$/
        const usernameAvailable = await Register.checkUsernameBackend(username);

        if (username === "" || username.length < 2 || !usernameRegex.test(username) || !usernameAvailable) {
            if (onlyCheck) return false;
            Register.applyStyleSheetToInput(inputUsername, "red");
        } else {
            Register.applyStyleSheetToInput(inputUsername, "green");
            return true;
        }
    }
    
    static checkPassword(onlyCheck) {
        const inputPassword = document.getElementById("register_password");
        const value = inputPassword.value;
        if (value === "" || value.length < 3) {
            if (onlyCheck) return false;
            Register.applyStyleSheetToInput(inputPassword, "red");
        } else {
            Register.applyStyleSheetToInput(inputPassword, "green");
            return true;
        }
    }
    
    static checkPasswordConfirm(onlyCheck) {
        const inputPassword = document.getElementById("register_password");
        const inputPasswordConfirm = document.getElementById("register_password_confirm");
        const password = inputPassword.value;
        const passwordConfirm = inputPasswordConfirm.value;
        if (passwordConfirm === "" || passwordConfirm != password) {
            if (onlyCheck) return false;
            Register.applyStyleSheetToInput(inputPasswordConfirm, "red");
        } else {
            Register.applyStyleSheetToInput(inputPasswordConfirm, "green");
            return true;
        }
    }
    
    static checkFirstName(onlyCheck) {
        const inputFirstName = document.getElementById("register_first_name");
        const value = inputFirstName.value;
        if (value === "") {
            if (onlyCheck) return false;
            Register.applyStyleSheetToInput(inputFirstName, "red");
        } else {
            Register.applyStyleSheetToInput(inputFirstName, "green");
            return true;
        }
    }
    
    static checkLastName(onlyCheck) {
        const inputLastName = document.getElementById("register_last_name");
        const value = inputLastName.value;
        if (value === "") {
            if (onlyCheck) return false;
            Register.applyStyleSheetToInput(inputLastName, "red");
        } else {
            Register.applyStyleSheetToInput(inputLastName, "green");
            return true;
        }
    }
    
    static checkEmail(onlyCheck) {
        const inputEmail = document.getElementById("register_email");
        const value = inputEmail.value;
        if (value === "") {
            if (onlyCheck) return false;
            Register.applyStyleSheetToInput(inputEmail, "red");
        } else {
            Register.applyStyleSheetToInput(inputEmail, "green");
            return true;
        }
    }

    static validateInputs() {
        const validUsername = Register.checkUsername(true);
        const validPassword = Register.checkPassword(true);
        const validPasswordConfirm = Register.checkPasswordConfirm(true);
        const validFirstName = Register.checkFirstName(true);
        const validLastName = Register.checkLastName(true);
        const validMail = Register.checkEmail(true);
        
        const validAllFields = validUsername && validPassword && validPasswordConfirm && validFirstName && validLastName && validMail
        return validAllFields
    }

    static checkRegisterInputs() {

        const theme = Cookie.getCookie('darkThemeCookie');
        const valid = Register.validateInputs();

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

document.getElementById('register_username').addEventListener('input', async () => {
    Register.checkRegisterInputs();
    Register.checkUsername();
});

document.getElementById('register_password').addEventListener('input', () => {
    Register.checkRegisterInputs();
    Register.checkPassword();
    Register.checkPasswordConfirm();
});

document.getElementById('register_password_confirm').addEventListener('input', () => {
    Register.checkRegisterInputs();
    Register.checkPasswordConfirm();
});

document.getElementById('register_first_name').addEventListener('input', () => {
    Register.checkRegisterInputs();
    Register.checkFirstName();
});

document.getElementById('register_last_name').addEventListener('input', () => {
    Register.checkRegisterInputs();
    Register.checkLastName();
});

document.getElementById('register_email').addEventListener('input', () => {
    Register.checkRegisterInputs();
    Register.checkEmail();
});

// events for fields
const containersInputs = document.querySelectorAll(".register_container input");
containersInputs.forEach(container => {
    
    container.addEventListener('focus', () => {
        Register.applyStyleSheetToInput(container, "blue");
    });
});

const usernameInput = document.getElementById("register_username");
const passwordInput = document.getElementById("register_password");
const passwordConfirmInput = document.getElementById("register_password_confirm");
const firstNameInput = document.getElementById("register_first_name");
const lastNameInput = document.getElementById("register_last_name");
const emailInput = document.getElementById("register_email");

// we have to use anonymous functions insteaad of named functions
// also, when passing functions like that, either use anonymous functions (better), either Register.checkUsername
const events = ["blur"];
events.forEach(eventType => {
    usernameInput.addEventListener(eventType, function() {
        Register.checkUsername();
    });
    passwordInput.addEventListener(eventType, function() {
        Register.checkPassword();
    });
    passwordConfirmInput.addEventListener(eventType, function() {
        Register.checkPasswordConfirm();
    });
    firstNameInput.addEventListener(eventType, function() {
        Register.checkFirstName();
    });
    lastNameInput.addEventListener(eventType, function() {
        Register.checkLastName();
    });
    emailInput.addEventListener(eventType, function() {
        Register.checkEmail();
    });
});
