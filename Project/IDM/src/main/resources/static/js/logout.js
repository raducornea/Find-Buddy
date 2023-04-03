import Theme from './Theme.js';
import Cookie from './Cookie.js';


export default class Logout {
   
    static setLightTheme() {
        const containers = document.querySelectorAll(".logout_confirm_container input[type='submit']");
        containers.forEach(container => {
            container.classList.remove("logout-input-submit-dark-theme");
            container.classList.add("logout-input-submit-light-theme");
        });

        const containerAlreadyMember = document.querySelector(".logout_confirm_message");
        containerAlreadyMember.classList.remove("logout_confirm_message-dark-theme");
        containerAlreadyMember.classList.add("logout_confirm_message-light-theme");
    }
    
    static setDarkTheme() {
        const containersInputs = document.querySelectorAll(".logout_confirm_container input[type='submit']");
        containersInputs.forEach(container => {
            container.classList.remove("logout-input-submit-light-theme");
            container.classList.add("logout-input-submit-dark-theme");
        });

        const containerAlreadyMember = document.querySelector(".logout_confirm_message");
        containerAlreadyMember.classList.remove("logout_confirm_message-light-theme");
        containerAlreadyMember.classList.add("logout_confirm_message-dark-theme");
    }

    static setMode() {

        const theme = Cookie.getCookie('darkThemeCookie');

        if (theme === "" || theme === "white") {
            Theme.setLightTheme();
            Logout.setLightTheme();
        } else {
            Theme.setDarkTheme();
            Logout.setDarkTheme();
        }
    }

    static switchMode() {

        let theme = Cookie.getCookie('darkThemeCookie');

        if (theme === "" || theme === "white") {
            theme = "black";
            Theme.setDarkTheme();
            Logout.setDarkTheme();
        } else {
            theme = "white";
            Theme.setLightTheme();
            Logout.setLightTheme();
        }
    
        Cookie.setCookie('darkThemeCookie', theme, 7);
    }
}

window.onload = function() {
    document.body.style.opacity = '1';
    Logout.setMode();
};

// event listeners
document.getElementById('switch_theme_button-dark-theme').addEventListener('click', () => {
    Logout.switchMode();
});

document.getElementById('switch_theme_button-light-theme').addEventListener('click', () => {
    Logout.switchMode();
});

const formContainer = document.querySelector('.form-container');
const left_button = document.querySelector('#logout_input_button');
const right_button = document.querySelector('#logout_cancel_button');

// add event listener to the window's resize event
window.addEventListener('resize', () => {
  // check the window width
  const width = formContainer.offsetWidth;
  
  if (width <= 250) {
    formContainer.style.flexDirection = 'column';
    formContainer.style.marginRight = '0';

    left_button.style.marginRight = '0';
    left_button.style.marginBottom = '10px';
    right_button.style.marginLeft = '0';
  } else {
    formContainer.style.flexDirection = 'row';
    formContainer.style.marginRight = '10px';

    left_button.style.marginBottom = '0';
    left_button.style.marginRight = '10px';
    right_button.style.marginLeft = '10px';
  }
});