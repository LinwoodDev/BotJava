function toggleDrawer(drawer) {
    var x = document.getElementById(drawer);
    if (x.className === "topnav") {
      x.className += " responsive";
    } else {
      x.className = "topnav";
    }
}
$('#nav .item').click(function () {
  $('#nav .item.active').removeClass('active');
  $(this).addClass('active');
});
$('.menuitem').on('click', function (e) {
  e.preventDefault();
  //  $(document).off("scroll");
  var athis = this;
  var target = this.hash,
          menu = target;
  $target = $(target);
  $('html, body').stop().animate({
      'scrollTop': $target.offset().top + 2
  }, 800, 'swing', function () {
      window.location.hash = target;
      $('.menuitem').removeClass('active');
      $(athis).addClass('active');

  });    
});    

$(window).on("scroll", function (event) {
  var $scrollPos = $(document).scrollTop(),
      $links = $('#nav .item');
  $links.each(function () {
      var $currLink = $(this),
          $refElement = $($("a", $currLink).attr("href"));
          if ($refElement.length) {
      if ($refElement.position().top <= $scrollPos && $refElement.position().top + $refElement.height() > $scrollPos) {
          $links.removeClass("active");
          $currLink.addClass("active");
      } else {
          $currLink.removeClass("active");
      }
    }
  });
});