window.addEventListener("load", function(){
    var navLink = document.querySelector('meta[name="nav-link"]').getAttribute("content");
    if ( navLink != "nav-link-home" ) {
        document.querySelector("#nav-link-home").classList.remove(  "active");
        document.querySelector("#" + navLink   ).classList.add(     "active");
    }
})
