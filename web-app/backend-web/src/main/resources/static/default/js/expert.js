//Карта для heat-map
const map = new ol.Map({
    target: 'map',
    layers: [
        new ol.layer.Tile({
            source: new ol.source.OSM()
        }),
    ],
    view: new ol.View({
        center: ol.proj.transform([37.646930, 55.725146], 'EPSG:4326', 'EPSG:3857'),
        zoom: 11
    })
});


//Нажатие на карту
map.on('click', function (evt) {
    clearMap('NEAREST_OBJECT');

    var lonlat = ol.proj.transform(evt.coordinate, 'EPSG:3857', 'EPSG:4326');
    var lon = lonlat[0];
    var lat = lonlat[1];

    // console.log(lon.toFixed(6) + "  " + lat.toFixed(6));

    //очистка списка
    document.querySelector("#nearest-objects").innerHTML = "";

    $.get("/api/malls/nearest", {latitude: lat, longitude: lon})
        .done(function (data) {
            let tableData = "<tr><th colspan='3'>Торговый центры</th></tr>";

            for (let item in data) {
                //console.log(JSON.stringify(data[item]));

                tableData += getInsertPostamatRow(
                    data[item]["name"],
                    data[item]["address"],
                    data[item]["phone"],
                    data[item]["latitude"],
                    data[item]["longitude"]);

                var layer = createLayer('NEAREST_OBJECT', 'green', 2, 100, data[item]["latitude"], data[item]["longitude"]);
                map.addLayer(layer);
            }

            document.querySelector("#nearest-objects").innerHTML += tableData;
        });


    $.get("/api/supermarkets/nearest", {latitude: lat, longitude: lon})
        .done(function (data) {
            let tableData = "<tr><th colspan='3'>Супермаркеты</th></tr>";

            for (let item in data) {
                //console.log(JSON.stringify(data[item]));

                tableData += getInsertPostamatRow(
                    data[item]["name"],
                    data[item]["address"],
                    data[item]["phone"],
                    data[item]["latitude"],
                    data[item]["longitude"]);

                var layer = createLayer('NEAREST_OBJECT', 'blue', 2, 100, data[item]["latitude"], data[item]["longitude"]);
                map.addLayer(layer);
            }

            document.querySelector("#nearest-objects").innerHTML += tableData;
        });
});


/**
 * Сохранение постамата
 */
function savePostamat(place, address, latitude, longitude) {
    jQuery.ajax({
        url: "/api/postamats/save",
        type: "POST",
        data: JSON.stringify({place: place, address: address, latitude: latitude, longitude: longitude}),
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function(){
            //ToDo: disable button
        }
    });
}


/**
 * Очистка/удаление слоя с карты по имени
 * */
function clearMap(layerName) {
    map.getLayers().getArray()
        .filter(layer => layer.get('name') === layerName)
        .forEach(layer => map.removeLayer(layer));
}


/**
 * Создание слоя карты
 * */
function createLayer(layerName, color, width, square, lat, lon) {
    //Рисуем точки на карте - ближайшие объекты к нажатой точке
    var centerLongitudeLatitude = ol.proj.fromLonLat([
        lon, lat
    ]);

    return new ol.layer.Vector({
        source: new ol.source.Vector({
            projection: 'EPSG:4326',
            features: [
                new ol.Feature(new ol.geom.Circle(centerLongitudeLatitude, square))
            ]
        }),
        name: layerName,
        style: [
            new ol.style.Style({
                stroke: new ol.style.Stroke({
                    color: color,
                    width: width
                }),
                fill: new ol.style.Fill({
                    color: 'rgba(0, 0, 255, 0.1)'
                })
            })
        ],
        zIndex: 100
    });
}


function getInsertPostamatRow(name, address, phone, lat, lon) {
    address = address.replace("Российская Федерация, внутригородская территория", "").trim();
    var res =
    "<tr>" +
        "<td>" + address + "<br />(" + name + ")" + "</td>" +
        "<td>" + phone + "</td>" +
        "<td><button class=\"btn btn-outline-primary btn-sm\" onclick=\"savePostamat(" +
        "'" + name + "', " +
        "'" + address + "', " +
        lat + ", " +
        lon +
        ")\">Установить</button></td>"
    "</tr>";
    return res;
}


/**
 * Расчет и вывод оптимальных секторов для установки постаматов
 * */
function calcMap() {
    let radius = document.querySelector("#radius-input").value;
    let heatMapUUID = document.querySelector("#heatmap-select").value;

    $.get("/api/calc", {radius: radius, kmlId: heatMapUUID})
        .done(function (data) {
            clearMap('SECTOR_OBJECT');
            clearMap('HEATMAP');

            for (let i=0; i<400; i++) {
                let layer = createLayer(
                    'SECTOR_OBJECT',
                    '#9293a5',
                    1,
                    400,
                    data[i]["point"]["latitude"],
                    data[i]["point"]["longitude"]);
                map.addLayer(layer);
            }

            loadKml();
        });
}


/**
 * Загрузить слой карты - тепловую карту
 * */
function loadKml() {
    let heatMapUUID = document.querySelector("#heatmap-select").value;
    $.get("/api/jobs/" + heatMapUUID + ".txt")
        .done(function (data) {
            let layer = createHeatMap(data);
            map.addLayer(layer);
        });
}


/**
 * Показать или скрыть гексогональную сетку на карте
 * */
function showHexMap() {
    if (document.querySelector("#hexMap").checked) {
        var grid = new ol.HexGrid ({ size:600, origin: map.getView().getCenter() });
        var hex = new ol.source.HexMap({ hexGrid: grid });
        map.addLayer (
            new ol.layer.Image({
                source: hex,
                name: 'HEXMAP'
            })
        );
    } else {
        clearMap('HEXMAP');
    }
}