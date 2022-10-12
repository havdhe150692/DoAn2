import {sendAddressToBackend} from "./getMetamask.js";

$(document).ready(function () {
    console.log("Document Ready")
    sendAddressToBackend()

    ethereum.on('accountsChanged', function (accounts) {
        sendAddressToBackend();
        $('input[name="privateKey"]').val(undefined)
    })

})



