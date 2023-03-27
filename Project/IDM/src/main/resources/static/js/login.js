function checkLoginInputs() {

    var username = document.getElementById("login_username").value;
    var password = document.getElementById("login_password").value;
    var login_submit = document.getElementById("login_submit");

    if (password === "" || username === "")
        login_submit.disabled = true;
    else
        login_submit.disabled = false;
}