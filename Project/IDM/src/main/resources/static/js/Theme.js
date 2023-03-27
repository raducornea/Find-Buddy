import Cookie from './Cookie.js';


export default class Theme {

    static setLightTheme() {
        const container = document.querySelector(".container");
        container.classList.remove("dark-theme");
        container.classList.add("light-theme");
    }

    static setDarkTheme() {
        const container = document.querySelector(".container");
        container.classList.remove("light-theme");
        container.classList.add("dark-theme");
    }

    static setMode() {
        const theme = Cookie.getCookie('darkThemeCookie');

        if (theme === "" || theme === "white") {
            Theme.setLightTheme();
        } else {
            Theme.setDarkTheme();
        }
    }
    
    static switchMode() {
    
        let theme = Cookie.getCookie('darkThemeCookie');

        if (theme === "" || theme === "white") {
            theme = "black";
            Theme.setDarkTheme();
        } else {
            theme = "white";
            Theme.setLightTheme();
        }
    
        Cookie.setCookie('darkThemeCookie', theme, 7);
    }
}
