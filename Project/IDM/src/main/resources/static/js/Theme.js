

export default class Theme {

    static setLightTheme() {
        const container = document.querySelector(".container");
        container.classList.remove("dark-theme");
        container.classList.add("light-theme");

        // set correct image for sun/moon
        const imgLight = document.getElementById("switch_theme_button-dark-theme");
        const imgDark = document.getElementById("switch_theme_button-light-theme");

        imgLight.style.visibility = "visible";
        imgDark.style.visibility = "hidden";
    }

    static setDarkTheme() {
        const container = document.querySelector(".container");
        container.classList.remove("light-theme");
        container.classList.add("dark-theme");

        // set correct image for sun/moon
        const imgLight = document.getElementById("switch_theme_button-dark-theme");
        const imgDark = document.getElementById("switch_theme_button-light-theme");

        imgLight.style.visibility = "hidden";
        imgDark.style.visibility = "visible";
    }

    
}
