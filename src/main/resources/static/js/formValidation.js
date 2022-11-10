    // Email Validation
    function ValidateEmail(event,inputText) {
      var mailFormat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
      if (inputText.value.match(mailFormat)) {
        return true;
      } else {
        event.preventDefault();
        return false;
      }

    }

    const validateEmail = (email) => {
      var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
      return email.match(mailformat);
    };

    // Email Validation
    const validate = () => {
      const $result = $('#result');
      const email = $('#email').val();
      $result.text('');

      if (validateEmail(email)) {
        $result.text(email + ' ✓ ');
        $result.css('color', 'green');
      } else {
        $result.text(' your Email is not valid :(');
        $result.css('color', 'red');
      }
      return false;
    }

    $('#email').on('input', validate);

//    Password Validation
    $('#password, #confirm_password').on('keyup', function () {
      if ($('#password').val() == $('#confirm_password').val()) {
        $('#message').html('Match ✓ ').css('color', 'green');
        if($('#password').val() == "") {
         $('#message').html('Password Not Null').css('color', 'red');
        }
      } else
        $('#message').html('Your Password is Not Matching').css('color', 'red');
    });

    //Check Enable
    function enable() {
                var check = document.getElementById("check");
                var btn = document.getElementById("btnRegister");
                if (check.checked) {
                    btn.removeAttribute("disabled");
                } else {
                    btn.disabled = true;
                }
            }
