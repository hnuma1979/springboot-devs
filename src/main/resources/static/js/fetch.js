
function modals(selector, message) {
    var $modal = document.querySelector(selector);
    var $bsmodal = new bootstrap.Modal($modal);
    $modal.querySelector(".modal-body p").innerHTML = message;
    $bsmodal.show();
}

function error_modal(message) {
    modals("#error_modal", message);
}

function warn_modal(message) {
    modals("#warn_modal", message);
}

function info_modal(message) {
    modals("#info_modal", message);
}

async function  post(
        $form = {}, 
        $data = {}, 
        success = undefined,
        feiler  = undefined
) {

    var $url        = $form.getAttribute("action");
    var $method     = $form.getAttribute("method");

    var $headers    = new Headers();
    $headers.set("Content-Type", "application/json");

    var $response = await fetch($url,{
            method  :   $method,
            headers :   $headers,
            redirect:   "error",
            cache:      "no-cache",
            body:       $data
        }).then(async (response) => {
            if (response.ok) {
                info_modal("正常登録しました。")
                if (typeof success == "function") {
                    success(await response.json());
                }
            } else if (response.status == 302 ) {
                error_modal("ログイン状態が不正です。")
                return {};
            } else if (response.status == 500 ) {
                error_modal("サーバーエラーです")
                return {};
            } else {
                warn_modal("リクエストエラーです")
                if (typeof feiler == "function") {
                    feiler(await response.json());
                }
            }
        }).catch(error => {
            console.error(error);
        });

}