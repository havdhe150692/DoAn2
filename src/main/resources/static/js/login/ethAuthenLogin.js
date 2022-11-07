

export function sendPublicAddressToBackEnd(publicAdrress) {

    $.ajax({
        url: "localhost:8080/api/login/authentication/" + publicAdrress,
        type: 'GET',
        dataType: 'json', // added data type
        success: function(res) {
            console.log(res);
        }
    });
}

