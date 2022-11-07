import {detectEthereum} from "./getMetamask.js";
import {openPopupError, openPopupPrivateKey} from "./popup.js";

$(document).ready(function () {
    console.log("Document Ready")

    //ethereum.on('accountsChanged', function (accounts) {
    //    sendAddressToBackend();
    //    $('input[name="privateKey"]').val(undefined)
    //})

    $('#playBtn').click(function (){
        detectEthereum()
    })

})

function sendPublicAddressToBackEnd(publicAdrress){

    $.ajax({
        type: "GET",
        url: "/api/users?publicAddress=${publicAddress}",
        data: {
            email: $("input[name='email']").val(),
            password: $password.val(),
            repeatPassword: $password.val(),
            userAddress: $userAddress.val(),
            privateKey: $privateKey.val(),
        },
        dataType: 'json',
        success: function (data) {
            const $emailInput = $('#email-error')
            const $passwordInput = $('#password-error')
            $('input[name="privateKey"]').val(undefined)

            if (data.msgError['sessionEmpty'] != null) {
                window.location.href = "/kryptoauth/404";
            }

            if (data.msgError['email'] != null) {
                console.log("hhhh")
                if ($emailInput.length)
                    $emailInput.remove()
                $('input[name="email"]').after('<label id="email-error" class="error" for="email">' + data.msgError['email'] + '</label>')
            }

            if (data.msgError['password'] != null) {
                if ($passwordInput.length)
                    $passwordInput.remove()
                $('input[name="password"]').after('<label id="password-error" class="error" for="password">' + data.msgError['password'] + '</label>')
            }

            if (data.msgError['userAddress'] != null) {
                openPopupError()
                $('div.error-p').children('p').eq(1)
                    .html("Nessun account rilevato. <br>Accedere a Metamask.");
            }

            if (data.msgError['contract'] != null){
                openPopupPrivateKey()
            }

            if (data.msgError['privateKey'] != null){
                openPopupError()
                $('div.error-p').children('p').eq(1)
                    .html("Chiave privata non corretta. <br>Riprovare.");
            }

            if (data.msgError['notEqualsAddress'] != null){
                openPopupError()
                $('div.error-p').children('p').eq(1)
                    .html("Chiave privata non associata a questo account.<br>Riprovare.");
            }

            if (data.msgError['loginError'] != null){
                openPopupError()
                $('div.error-p').children('p').eq(1)
                    .html("Credenziali errate. <br> Riprovare.");
            }

            if (data.msgError['credentialsNotVerified'] != null){
                openPopupError()
                $('div.error-p').children('p').eq(1)
                    .html("L'admin non ha ancora accettato la registrazione oppure le credenziali sono errate.");
            }

            if (data.msgError['successAdmin'] != null){
                window.location.href = "/kryptoauth/loginAdmin"
            }

            if (data.msgError['successUser'] != null){
                window.location.href = "/kryptoauth"
            }
        },
        error: function (e) {
            console.log(e)
        }
    });
}

function ajaxLogin($privateKey, $userAddress){
    const $password = $("input[name='password']")

    $.ajax({
        type: "POST",
        url: "/toadking/login",
        data: {
            email: $("input[name='email']").val(),
            password: $password.val(),
            repeatPassword: $password.val(),
            userAddress: $userAddress.val(),
            privateKey: $privateKey.val(),
        },
        dataType: 'json',
        success: function (data) {
            const $emailInput = $('#email-error')
            const $passwordInput = $('#password-error')
            $('input[name="privateKey"]').val(undefined)

            if (data.msgError['sessionEmpty'] != null) {
                window.location.href = "/kryptoauth/404";
            }

            if (data.msgError['email'] != null) {
                console.log("hhhh")
                if ($emailInput.length)
                    $emailInput.remove()
                $('input[name="email"]').after('<label id="email-error" class="error" for="email">' + data.msgError['email'] + '</label>')
            }

            if (data.msgError['password'] != null) {
                if ($passwordInput.length)
                    $passwordInput.remove()
                $('input[name="password"]').after('<label id="password-error" class="error" for="password">' + data.msgError['password'] + '</label>')
            }

            if (data.msgError['userAddress'] != null) {
                openPopupError()
                $('div.error-p').children('p').eq(1)
                    .html("Nessun account rilevato. <br>Accedere a Metamask.");
            }

            if (data.msgError['contract'] != null){
                openPopupPrivateKey()
            }

            if (data.msgError['privateKey'] != null){
                openPopupError()
                $('div.error-p').children('p').eq(1)
                    .html("Chiave privata non corretta. <br>Riprovare.");
            }

            if (data.msgError['notEqualsAddress'] != null){
                openPopupError()
                $('div.error-p').children('p').eq(1)
                    .html("Chiave privata non associata a questo account.<br>Riprovare.");
            }

            if (data.msgError['loginError'] != null){
                openPopupError()
                $('div.error-p').children('p').eq(1)
                    .html("Credenziali errate. <br> Riprovare.");
            }

            if (data.msgError['credentialsNotVerified'] != null){
                openPopupError()
                $('div.error-p').children('p').eq(1)
                    .html("L'admin non ha ancora accettato la registrazione oppure le credenziali sono errate.");
            }

            if (data.msgError['successAdmin'] != null){
                window.location.href = "/kryptoauth/loginAdmin"
            }

            if (data.msgError['successUser'] != null){
                window.location.href = "/kryptoauth"
            }
        },
        error: function (e) {
            console.log(e)
        }
    });
}




