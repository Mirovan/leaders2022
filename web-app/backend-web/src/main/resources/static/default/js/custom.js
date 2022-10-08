function countDownTimer(finishTestTime, nowTime) {
    var finishTestTime = new Date($('#finishTestTime').val()).getTime();
    var nowTime = new Date($('#nowTime').val()).getTime();

    var timeIntervalID = setInterval(function() {
        // Find the distance between now and the count down date
        var distance = finishTestTime - nowTime;

        // Time calculations for days, hours, minutes and seconds
        var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = Math.floor((distance % (1000 * 60)) / 1000);

        var result = (hours < 10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);

        $('#timer').html(result);

        if (distance < 0) {
            clearInterval(timeIntervalID);
            $('#test').html("");
            $('#timer').html("Время вышло");
        }

        //increment now for 1 sec
        nowTime = new Date(nowTime + 1000).getTime();
        //$('#nowTime').val(nowTime);
    }, 1000);
}


/* INPUT MASK */
// function init_InputMask() {
// 	if( typeof ($.fn.inputmask) === 'undefined') { return; }
// 	console.log('init_InputMask');
//
// 	$(":input").inputmask();
// };
//
//
// $(document).ready(function() {
// 	init_InputMask();
// });

const map = new ol.Map({
  target: 'map',
  layers: [
    new ol.layer.Tile({
      source: new ol.source.OSM()
    }),
    new ol.layer.Heatmap({
      source: new ol.source.Vector({
        url: '/kml',
        format: new ol.format.KML({
          extractStyles: false,
        }),
      }),
      blur: parseInt(10),
      radius: parseInt(10),
      weight: function (feature) {
        const area = parseFloat(feature.get('area')) / 10000;
        return area;
      },
    })
  ],
  view: new ol.View({
    center: ol.proj.transform([37.646930, 55.725146], 'EPSG:4326', 'EPSG:3857'),
    zoom: 11
  })
});