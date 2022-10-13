import {openPopUpGetMetamask} from './popupNew.js'

window.userWalletAddress = null

export function detectEthereum() {
    //const provider = await detectEthereumProvider()
    if (typeof window.ethereum === 'undefined') {
        openPopUpGetMetamask();
        return "openPopup"
    } else if (!activeMetaMask()) {
        console.log("metamask acc not found")
        return "noAccount"
    } else {
        return "ok"
    }
}



async function activeMetaMask() {
    const accounts = await window.ethereum.request({ method: 'eth_requestAccounts' }).catch((e) => {
        return false
    })
    if (!accounts) {return false}

    window.userWalletAddress = accounts[0]
   // const addressField = document.querySelector("input[name='userAddress']")
    const addressField = document.querySelector(".showAccount");
    addressField.innerHTML = window.userWalletAddress
    console.log(window.userWalletAddress)
    return true;
}