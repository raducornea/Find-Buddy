import Theme from './Theme.js';
import Cookie from './Cookie.js';


export default class RegisterSuccess {
    
    static setLightTheme() {
        const containers = document.querySelectorAll(".register_succes_container input[type='submit']");
        containers.forEach(container => {
            container.classList.remove("register_success-input-submit-dark-theme");
            container.classList.add("register_success-input-submit-light-theme");
        });
        
        const registerSuccessMessage = document.querySelector(".register_success_message");
        registerSuccessMessage.classList.remove("register_success_message-dark-theme");
        registerSuccessMessage.classList.add("register_success_message-light-theme");
        
        const registerSuccessMessageHint = document.querySelector(".register_success_message_hint");
        registerSuccessMessageHint.classList.remove("register_success_message_hint-dark-theme");
        registerSuccessMessageHint.classList.add("register_success_message_hint-light-theme");

        document.getElementById("success_image-light-theme").style.visibility = "visible";
        document.getElementById("success_image-dark-theme").style.visibility = "hidden";
    }
    
    static setDarkTheme() {
        const containersInputs = document.querySelectorAll(".register_succes_container input[type='submit']");
        containersInputs.forEach(container => {
            container.classList.remove("register_success-input-submit-light-theme");
            container.classList.add("register_success-input-submit-dark-theme");
        });
                
        const registerSuccessMessage = document.querySelector(".register_success_message");
        registerSuccessMessage.classList.remove("register_success_message-light-theme");
        registerSuccessMessage.classList.add("register_success_message-dark-theme");
        
        const registerSuccessMessageHint = document.querySelector(".register_success_message_hint");
        registerSuccessMessageHint.classList.remove("register_success_message_hint-light-theme");
        registerSuccessMessageHint.classList.add("register_success_message_hint-dark-theme");
        
        document.getElementById("success_image-dark-theme").style.visibility = "visible";
        document.getElementById("success_image-light-theme").style.visibility = "hidden";
    }

    static setMode() {

        const theme = Cookie.getCookie('darkThemeCookie');

        if (theme === "" || theme === "white") {
            Theme.setLightTheme();
            RegisterSuccess.setLightTheme();
        } else {
            Theme.setDarkTheme();
            RegisterSuccess.setDarkTheme();
        }
    }

    static switchMode() {

        let theme = Cookie.getCookie('darkThemeCookie');

        if (theme === "" || theme === "white") {
            theme = "black";
            Theme.setDarkTheme();
            RegisterSuccess.setDarkTheme();
        } else {
            theme = "white";
            Theme.setLightTheme();
            RegisterSuccess.setLightTheme();
        }
    
        Cookie.setCookie('darkThemeCookie', theme, 7);
    }
}

window.onload = function() {
    document.body.style.opacity = '1';
    RegisterSuccess.setMode();
};

// event listeners
document.getElementById('switch_theme_button-dark-theme').addEventListener('click', () => {
    RegisterSuccess.switchMode();
});

document.getElementById('switch_theme_button-light-theme').addEventListener('click', () => {
    RegisterSuccess.switchMode();
});