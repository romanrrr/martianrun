
$(document).ready(function(){

    var timerId = setInterval(function () {
        if ($('.multiple-container').hasClass('multiple-attached')) {
           var obj = {"video": {
             "value": "<iframe width='520' height='300' src='https://www.youtube.com/embed/DwJGIqJwuXI' frameborder='0' allow='autoplay; encrypted-media' allowfullscreen></iframe>"
           }}
           $( "#CustomModelForm_enemiesImages_preVideo" ).parent().removeClass("item");
           $( "#CustomModelForm_enemiesImages_preVideo" ).parent().addClass("videoYouTube");
           $( "#CustomModelForm_enemiesImages_preVideo" ).parent().append( obj.video.value );
           $( "#CustomModelForm_enemiesImages_preVideo" ).hide();


           var backgroundVideo = {"video": {
             "value": "<iframe width='520' height='300' src='https://www.youtube.com/embed/PDBj7slxeb0' frameborder='0' allow='autoplay; encrypted-media' allowfullscreen></iframe>"
           }}
           $( "#CustomModelForm_gameImages_preVideoBackground" ).parent().removeClass("item");
           $( "#CustomModelForm_gameImages_preVideoBackground" ).parent().addClass("videoYouTube");
           $( "#CustomModelForm_gameImages_preVideoBackground" ).parent().append( backgroundVideo.video.value );
           $( "#CustomModelForm_gameImages_preVideoBackground" ).hide();

          $( "#CustomModelForm_enemiesImages_preEnemies" ).parent().removeClass("item");
          $( "#CustomModelForm_enemiesImages_preEnemies" ).parent().addClass("videoYouTube");
          $( "#CustomModelForm_enemiesImages_preEnemies" ).parent().append("<img src='/img/templates/runnerGame/enieme1.jpg' alt='Enemy example' height='293' width='520'>");
          $( "#CustomModelForm_enemiesImages_preEnemies" ).parent().append("<img src='/img/templates/runnerGame/enieme2.jpg' alt='Enemy example' height='293' width='520'>");
          $( "#CustomModelForm_enemiesImages_preEnemies" ).parent().append("<img src='/img/templates/runnerGame/enieme3.jpg' alt='Enemy example' height='293' width='520'>");
          $( "#CustomModelForm_enemiesImages_preEnemies" ).hide();


            clearInterval(timerId);
        }
    }, 200);

});