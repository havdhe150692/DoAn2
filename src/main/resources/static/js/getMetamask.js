import {openPopUpGetMetamask} from './popupNew.js'

window.userWalletAddress = null

export function sendAddressToBackend() {
    if (typeof window.ethereum === 'undefined') {
        openPopUpGetMetamask();
        //open PopUpError here

       // return "openPopup";
    }
    else if (!activeMetaMask())
    {
        console.log("metamask acc not found")
    }
        //return "noAccount"
    //return "ok"
}

async function activeMetaMask() {
    const accounts = await window.ethereum.request({ method: 'eth_requestAccounts' }).catch((e) => {
        return false
    })
    if (!accounts) {return false}

    window.userWalletAddress = accounts[0]
   // const addressField = document.querySelector("input[name='userAddress']")
    const addressField = document.querySelector(".showAccount");
    addressField.value = window.userWalletAddress
    console.log(window.userWalletAddress)
    return true;
}