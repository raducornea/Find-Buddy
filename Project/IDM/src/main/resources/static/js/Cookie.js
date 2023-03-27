

export default class Cookie {
    static setCookie(cookieName, cookieValue, expiryDays) {
    
        const date = new Date();
        date.setTime(date.getTime() + (expiryDays * 24 * 60 * 60 * 1000));
    
        const expires = "expires=" + date.toUTCString();
        document.cookie = cookieName + "=" + cookieValue + ";" + expires + ";path=/";
    }
    
    static getCookie(cookieName) {
    
        const name = cookieName + "=";
        const decodedCookie = decodeURIComponent(document.cookie);
        const cookieArray = decodedCookie.split(';');
    
        for(let index = 0; index < cookieArray.length; index++) {
          let cookie = cookieArray[index];
    
          while (cookie.charAt(0) == ' ') {
            cookie = cookie.substring(1);
          }
    
          if (cookie.indexOf(name) == 0) {
            return cookie.substring(name.length, cookie.length);
          }
        }
    
        return "";
    }
}