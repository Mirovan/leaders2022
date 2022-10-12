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
    $.get("/malls/nearest", {latitude: "1", longitude: "2"})
        .done(function (data) {
            document.querySelector("#nearest-malls").innerHTML = "";
            let tableData = "";
            for (let item in data) {
                console.log(JSON.stringify(data[item]));

                tableData += "<tr>" +
                    "<td>" + data[item]["address"] + "<br />(" + data[item]["name"] + ")" + "</td>" +
                    "<td>" + data[item]["phone"] + "</td>" +
                    "<td><button class='btn btn-outline-primary btn-sm'>Установить</button></td>"
                "</tr>";
            }
            document.querySelector("#nearest-malls").innerHTML = tableData;
        });
});