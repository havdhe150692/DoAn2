    function ValidateEmail(event,inputText) {
      var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
      if (inputText.value.match(mailformat)) {
        return true;
      } else {
//        document.getElementById("emailInvalid").innerHTML = "Email is not valid , please check again";
        event.preventDefault();
        return false;
      }
    }

    const validateEmail = (email) => {
      var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
      return email.match(mailformat);
    };

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