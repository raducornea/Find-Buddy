function checkLoginInputs() {

    var username = document.getElementById("login_username").value;
    var password = document.getElementById("login_password").value;
    var login_submit = document.getElementById("login_submit");

    if (password === "" || username === "")
        login_submit.disabled = true;
    else
        login_submit.disabled = false;
}

function setCookie(cookieName, cookieValue, expiryDays) {
    var d = new Date();
    d.setTime(d.getTime() + (expiryDays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toUTCString();
    document.cookie = cookieName + "=" + cookieValue + ";" + expires + ";path=/";
}

function getCookie(cookieName) {
    var name = cookieName + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var cookieArray = decodedCookie.split(';');
    for(var i = 0; i < cookieArray.length; i++) {
      var cookie = cookieArray[i];
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
    var mode = getCookie('darkModeCookie');

    if (mode === "" || mode === "white") {
        container.classList.remove("dark-mode");
        container.classList.add("light-mode");
        
    } else {
        container.classList.remove("light-mode");
        container.classList.add("dark-mode");
    }
}

function switchMode() {

    var container = document.querySelector(".container");
    var mode = getCookie('darkModeCookie');
    
    if (mode === "" || mode === "white") {
        mode = "black";
        container.classList.remove("light-mode");
        container.classList.add("dark-mode");
    } else {
        mode = "white";
        container.classList.remove("dark-mode");
        container.classList.add("light-mode");
    }

    setCookie('darkModeCookie', mode, 7);
}