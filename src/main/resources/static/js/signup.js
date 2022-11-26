
function set_error(id, msg) {
    var input   = document.querySelector("#"+id);
    var messg   = document.querySelector("#"+id+"_invalid_feedback");
    // システム規定のエラーメッセージを出力する（ユーザー入力を表示してはいけない）
    messg.innerHTML = msg;
    if (msg == "" || msg == undefined) {
        input.classList.remove("is-invalid");
    } else {
        input.classList.add("is-invalid");
    }
}

function input_change(event) {
    event.target.classList.remove("is-invalid")
}

function send(event) {

    var $form       = document.querySelector("#form");

    document.querySelector("#form input").addEventListener("change",    input_change);

    var $data = JSON.stringify({
        username: document.querySelector("#username").value,
        email:    document.querySelector("#email").value,
        password: document.querySelector("#password").value,
    });

    post($form, $data, 
        (json) => {
            document.querySelector("#nav-login-form").submit();
        }, (json) => {
        // 異常時
        set_error("username",   json["username"]);
        set_error("email",      json["email"]);
        set_error("password",   json["password"]);
        console.log(json);
    });
}
window.addEventListener("load", () => {
    document.querySelector("#submit").addEventListener("click", send);
}); // window.onload
