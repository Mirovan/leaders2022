//Карта для отображения постаматов
const map = new ol.Map({
    target: 'map',
    layers: [
        new ol.layer.Tile({
            source: new ol.source.OSM()
        })
    ],
    view: new ol.View({
        center: ol.proj.transform([37.618423, 55.751244], 'EPSG:4326', 'EPSG:3857'),
        zoom: 11
    })
});


$(function () {

    $.get("/api/postamats/list")
        .done(function (data) {
            for (let item in data) {
                //Рисуем точки на карте - установленные постаматы
                var centerLongitudeLatitude = ol.proj.fromLonLat([
                    data[item]["longitude"], data[item]["latitude"]
                ]);
                var layer = new ol.layer.Vector({
                    source: new ol.source.Vector({
                        projection: 'EPSG:4326',
                        features: [
                            new ol.Feature(new ol.geom.Circle(centerLongitudeLatitude, 100)),
                            new ol.Feature(new ol.geom.Circle(centerLongitudeLatitude, 500))
                        ]
                    }),
                    name: 'POSTAMAT',
                    style: [
                        new ol.style.Style({
                            stroke: new ol.style.Stroke({
                                color: 'blue',
                                width: 2
                            }),
                            fill: new ol.style.Fill({
                                color: 'rgba(0, 0, 255, 0.2)'
                            })
                        })
                    ]
                });
                map.addLayer(layer);
            }
        });
});