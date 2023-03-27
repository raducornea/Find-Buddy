
function setCookie(cookieName, cookieValue, expiryDays) {
    
    var date = new Date();
    date.setTime(date.getTime() + (expiryDays * 24 * 60 * 60 * 1000));

    var expires = "expires=" + date.toUTCString();
    document.cookie = cookieName + "=" + cookieValue + ";" + expires + ";path=/";
}

function getCookie(cookieName) {

    var name = cookieName + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var cookieArray = decodedCookie.split(';');

    for(var index = 0; index < cookieArray.length; index++) {
      var cookie = cookieArray[index];

      while (cookie.charAt(0) == ' ') {
        cookie = cookie.substring(1);
      }

      if (cookie.indexOf(name) == 0) {
        return cookie.substring(name.length, cookie.length);
      }
    }

    return "";
}

window.onload = function() {
    setMode();
};

function setMode() {

    var container = document.querySelector(".container");
    var theme = getCookie('darkThemeCookie');

    if (theme === "" || theme === "white") {
        container.classList.remove("dark-theme");
        container.classList.add("light-theme");
    } else {
        container.classList.remove("light-theme");
        container.classList.add("dark-theme");
    }
}

function switchMode() {

    var container = document.querySelector(".container");
    var theme = getCookie('darkThemeCookie');

    if (theme === "" || theme === "white") {
        theme = "black";
        container.classList.remove("light-theme");
        container.classList.add("dark-theme");
    } else {
        theme = "white";
        container.classList.remove("dark-theme");
        container.classList.add("light-theme");
    }

    setCookie('darkThemeCookie', theme, 7);
}