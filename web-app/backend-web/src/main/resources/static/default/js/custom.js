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


//Нажатие на карту
map.on('click', function (evt) {
    var lonlat = ol.proj.transform(evt.coordinate, 'EPSG:3857', 'EPSG:4326');
    var lon = lonlat[0];
    var lat = lonlat[1];

    console.log( lon.toFixed(6) + "  " + lat.toFixed(6) );
    $.get("/malls/nearest", {latitude: lat, longitude: lon})
        .done(function (data) {
            document.querySelector("#nearest-malls").innerHTML = "";
            let tableData = "";
            for (let item in data) {
                //console.log(JSON.stringify(data[item]));

                tableData += "<tr>" +
                    "<td>" + data[item]["address"] + "<br />(" + data[item]["name"] + ")" + "</td>" +
                    "<td>" + data[item]["phone"] + "</td>" +
                    "<td><button class='btn btn-outline-primary btn-sm'>Установить</button></td>"
                "</tr>";

                //Рисуем точки на карте - ближайшие объекты к нажатой точке
                var centerLongitudeLatitude = ol.proj.fromLonLat([
                    data[item]["longitude"], data[item]["latitude"]
                ]);
                var layer = new ol.layer.Vector({
                    source: new ol.source.Vector({
                        projection: 'EPSG:4326',
                        features: [new ol.Feature(new ol.geom.Circle(centerLongitudeLatitude, 400))]
                    }),
                    style: [
                        new ol.style.Style({
                            stroke: new ol.style.Stroke({
                                color: 'blue',
                                width: 3
                            }),
                            fill: new ol.style.Fill({
                                color: 'rgba(0, 0, 255, 0.1)'
                            })
                        })
                    ]
                });
                map.addLayer(layer);
            }
            document.querySelector("#nearest-malls").innerHTML = tableData;
        });
});