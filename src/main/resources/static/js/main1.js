(function ($) {
    "use strict";
    
    // Dropdown on mouse hover
    $(document).ready(function () {
        function toggleNavbarMethod() {
            if ($(window).width() > 992) {
                $('.navbar .dropdown').on('mouseover', function () {
                    $('.dropdown-toggle', this).trigger('click');
                }).on('mouseout', function () {
                    $('.dropdown-toggle', this).trigger('click').blur();
                });
            } else {
                $('.navbar .dropdown').off('mouseover').off('mouseout');
            }
        }
        toggleNavbarMethod();
        $(window).resize(toggleNavbarMethod);
    });
    
    
    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 100) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({scrollTop: 0}, 1500, 'easeInOutExpo');
        return false;
    });


    // Vendor carousel
    $('.vendor-carousel').owlCarousel({
        loop: true,
        margin: 29,
        nav: false,
        autoplay: true,
        smartSpeed: 1000,
        responsive: {
            0:{
                items:2
            },
            576:{
                items:3
            },
            768:{
                items:4
            },
            992:{
                items:5
            },
            1200:{
                items:6
            }
        }
    });


    // Related carousel
    $('.related-carousel').owlCarousel({
        loop: true,
        margin: 29,
        nav: false,
        autoplay: true,
        smartSpeed: 1000,
        responsive: {
            0:{
                items:1
            },
            576:{
                items:2
            },
            768:{
                items:3
            },
            992:{
                items:4
            }
        }
    });


    // Product Quantity
    $('.quantity button').on('click', function () {
        var button = $(this);
        var oldValue = button.parent().parent().find('input').val();
        if (button.hasClass('btn-plus')) {
            var newVal = parseFloat(oldValue) + 1;
        } else {
            if (oldValue > 0) {
                var newVal = parseFloat(oldValue) - 1;
            } else {
                newVal = 0;
            }
        }
        button.parent().parent().find('input').val(newVal);
    });
    
})(jQuery);

// Restricts input for each element in the set of matched elements to the given inputFilter.
(function ($) {
    $.fn.inputFilter = function (inputFilter) {
        return this.on("input keydown keyup mousedown mouseup select contextmenu drop", function () {
            if (inputFilter(this.value)) {
                this.oldValue = this.value;
                this.oldSelectionStart = this.selectionStart;
                this.oldSelectionEnd = this.selectionEnd;
            } else if (this.hasOwnProperty("oldValue")) {
                this.value = this.oldValue;
                this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
            } else {
                this.value = "";
            }
        });
    };
}(jQuery));

$("#intLimitTextBox").inputFilter(function (value) {
    return /^\d*$/.test(value) && (value === "" || parseInt(value) <= 1000000);
})

$("#intLimitTextBox1").inputFilter(function (value) {
    return /^\d*$/.test(value) && (value === "" || parseInt(value) <= 1000000);
})



function validateFormInputPrice() {
  var from = document.forms["form1"]["priceFrom"].value;
  var to = document.forms["form1"]["priceTo"].value;
  if (from == "" || from == null || to == "" || to == null) {
    document.getElementById("errorMes").innerHTML = "All Field price must be filled out";
    document.getElementById("errorMes").style = "color : red;";
    return false;
  } else if (from > to) {
    document.getElementById("errorMes").innerHTML = "Price in range is invalid!";
    document.getElementById("errorMes").style = "color : red;";
    return false;
  } else if(from == 0 || to == 0) {
    document.getElementById("errorMes").innerHTML = "We don't have 0$ Toad";
    document.getElementById("errorMes").style = "color : red;";
    return false;
  }
  return true;
}

//Feed back validation
function validateRadioButton() {
if (typeof $("input[name='rating']:checked").val() === "undefined") {
    document.getElementById("radioMess").innerHTML = "Please rate us with a point!";
    document.getElementById("radioMess").style = "color : red;";
    return false;
 } else {
    return true;
 }
return true;
}

