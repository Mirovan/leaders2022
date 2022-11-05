$("#loader-bg").hide();

//Карта для heat-map
const map = new ol.Map({
    target: 'map',
    layers: [
        new ol.layer.Tile({
            source: new ol.source.OSM()
        }),
    ],
    view: new ol.View({
        center: ol.proj.transform([37.618423, 55.751244], 'EPSG:4326', 'EPSG:3857'),
        zoom: 11
    })
});


//Popup Для карты
var container = document.getElementById('popup');
var content = document.getElementById('popup-content');
var closer = document.getElementById('popup-closer');

var overlay = new ol.Overlay({
    element: container,
    autoPan: true,
    autoPanAnimation: {
        duration: 250
    }
});
map.addOverlay(overlay);

map.on('pointermove', function (event) {
    let coordinate = null;
    let layerName = null;
    let feature = map.forEachFeatureAtPixel(event.pixel, function (feature, layer) {
        coordinate = event.coordinate;
        layerName = layer.get('name');
        return feature;
    });

    if (feature != null
        && feature.get('geometry') != null
        && feature.get('geometry') instanceof ol.geom.Point
        && 'NEAREST_OBJECT' === layerName) {
        this.getTargetElement().style.cursor = 'pointer';
        content.innerHTML = feature.get('name');
        overlay.setPosition(coordinate);
    } else {
        this.getTargetElement().style.cursor = '';
        overlay.setPosition(undefined);
    }
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

    $.get("/api/kiosk/nearest", {latitude: lat, longitude: lon})
        .done(function (data) {
            doKioskData(data);

            $.get("/api/mfc/nearest", {latitude: lat, longitude: lon})
                .done(function (data) {
                    doMfcData(data);

                    $.get("/api/library/nearest", {latitude: lat, longitude: lon})
                        .done(function (data) {
                            doLibraryData(data);

                            $.get("/api/malls/nearest", {latitude: lat, longitude: lon})
                                .done(function (data) {
                                    doMallData(data);

                                    $.get("/api/supermarkets/nearest", {latitude: lat, longitude: lon})
                                        .done(function (data) {
                                            doSupermarketData(data);
                                        });
                                });
                        });
                });
        });

});


function doKioskData(data) {
    let tableData = "<tr><th colspan='3'><img src='/static/default/images/icons/kiosk.png' width='25' />&nbsp;&nbsp;Киоски</th></tr>";

    for (let item in data) {
        tableData += getInsertPostamatRow(
            data[item]["name"],
            data[item]["address"],
            data[item]["businessEntity"],
            data[item]["latitude"],
            data[item]["longitude"]);

        var layer = createLayer('NEAREST_OBJECT', 'blue', 2, 100, data[item], "kiosk");
        map.addLayer(layer);
    }

    document.querySelector("#nearest-objects").innerHTML += tableData;
}


function doMfcData(data) {
    let tableData = "<tr><th colspan='3'><img src='/static/default/images/icons/mfc.png' width='25' />&nbsp;&nbsp;МФЦ</th></tr>";

    for (let item in data) {
        tableData += getInsertPostamatRow(
            data[item]["name"],
            data[item]["address"],
            data[item]["phone"],
            data[item]["latitude"],
            data[item]["longitude"]);

        var layer = createLayer('NEAREST_OBJECT', 'green', 2, 100, data[item], "mfc");
        map.addLayer(layer);
    }

    document.querySelector("#nearest-objects").innerHTML += tableData;
}


function doLibraryData(data) {
    let tableData = "<tr><th colspan='3'><img src='/static/default/images/icons/library.png' width='25' />&nbsp;&nbsp;Библиотеки</th></tr>";

    for (let item in data) {
        tableData += getInsertPostamatRow(
            data[item]["name"],
            data[item]["address"],
            data[item]["phone"],
            data[item]["latitude"],
            data[item]["longitude"]);

        var layer = createLayer('NEAREST_OBJECT', '#7f6809', 2, 100, data[item], "library");
        map.addLayer(layer);
    }

    document.querySelector("#nearest-objects").innerHTML += tableData;
}


function doMallData(data) {
    let tableData = "<tr><th colspan='3'><img src='/static/default/images/icons/mall.png' width='25' />&nbsp;&nbsp;Торговый центры</th></tr>";

    for (let item in data) {
        tableData += getInsertPostamatRow(
            data[item]["name"],
            data[item]["address"],
            data[item]["phone"],
            data[item]["latitude"],
            data[item]["longitude"]);

        var layer = createLayer('NEAREST_OBJECT', '#73097f', 2, 100, data[item], "mall");
        map.addLayer(layer);
    }

    document.querySelector("#nearest-objects").innerHTML += tableData;
}


function doSupermarketData(data) {
    let tableData = "<tr><th colspan='3'><img src='/static/default/images/icons/supermarket.png' width='25' />&nbsp;&nbsp;Супермаркеты</th></tr>";

    for (let item in data) {
        tableData += getInsertPostamatRow(
            data[item]["name"],
            data[item]["address"],
            data[item]["phone"],
            data[item]["latitude"],
            data[item]["longitude"]);

        var layer = createLayer('NEAREST_OBJECT', '#7f3209', 2, 100, data[item], "supermarket");
        map.addLayer(layer);
    }

    document.querySelector("#nearest-objects").innerHTML += tableData;
}

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
        success: function () {
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
function createLayer(layerName, color, width, square, itemData, icon) {
    let lat = itemData["latitude"];
    let lon = itemData["longitude"];
    let type = "";
    if (icon == "kiosk") type = "Киоск: ";
    if (icon == "mfc") type = "МФЦ: ";
    if (icon == "library") type = "Библиотека: ";
    if (icon == "mall") type = "ТЦ: ";
    if (icon == "supermarket") type = "Супермаркет: ";
    let name = "<strong>" + type + itemData["name"] + "</strong><br />" + itemData["address"];

    return new ol.layer.Vector({
        source: new ol.source.Vector({
            projection: 'EPSG:4326',
            features: [
                new ol.Feature({
                    geometry: new ol.geom.Point(ol.proj.fromLonLat([lon, lat])),
                    name: name,
                })
            ]
        }),
        name: layerName,
        style: new ol.style.Style({
            image: new ol.style.Icon({
                anchor: [0.5, 0.5],
                anchorXUnits: 'fraction',
                anchorYUnits: 'fraction',
                scale: 0.5,
                src: '/static/default/images/icons/' + icon + '.png'
            })
        }),
        zIndex: 100
    });
}


function getInsertPostamatRow(name, address, addData, lat, lon) {
    address = address.replace("Российская Федерация, внутригородская территория", "").trim();
    var res =
        "<tr>" +
        "<td>" + address + "<br />(" + name + ")" + "</td>" +
        "<td>" + addData + "</td>" +
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
    $("#loader-bg").show();

    let radius = $("#hexagonRadius").val();
    let kmlId = $("#heatmap-select").val();
    let considerHouses = $("#considerHouses").is(":checked");
    let considerMalls = $("#considerMalls").is(":checked");
    let considerSupermarkets = $("#considerSupermarkets").is(":checked");
    let considerMetro = $("#considerMetro").is(":checked");
    let considerWorkCenter = $("#considerWorkCenter").is(":checked");
    let considerChildHouse = $("#considerChildHouse").is(":checked");
    let considerParking = $("#considerParking").is(":checked");
    let considerPostamat = $("#considerPostamat").is(":checked");

    clearMap('SECTOR_OBJECT');
    clearMap('HEATMAP');

    $.getJSON("/api/calc", {
        radius, kmlId, considerHouses, considerMalls, considerSupermarkets, considerMetro,
        considerWorkCenter, considerChildHouse, considerParking, considerPostamat
    }, function (data) {
        var format = new ol.format.WKT();
        var features = data.map(function (item) {
            const feature = format.readFeature(item.wkt);
            const w = Math.floor(item.weight * 512);
            const r = w > 255 ? 255 : w;
            const g = w < 255 ? 255 : (512 - w);
            // console.log(r, g);
            feature.setStyle(new ol.style.Style({
                stroke: new ol.style.Stroke({
                    color: 'rgba(125,0,135,0.45)',
                    width: '1',
                    lineDash: [4]
                }),
                fill: new ol.style.Fill({color: 'rgba(' + r + ', ' + g + ', 0, 0.5)'}),
            }));
            return feature;
        });
        var vectorSource = new ol.source.Vector({
            features: features
        });

        map.addLayer(
            new ol.layer.Vector({
                source: vectorSource,
                name: 'SECTOR_OBJECT',
                zIndex: 100
            })
        );

        $("#loader-bg").hide();
    });
}


/**
 * Загрузить слой карты - тепловую карту
 * */
function loadKml() {
    let heatMapUUID = document.querySelector("#heatmap-select").value;
    if (heatMapUUID == 0) {
        clearMap('HEATMAP');
    } else {
        $.get("/api/jobs/" + heatMapUUID + ".txt")
            .done(function (data) {
                let layer = createHeatMap(data);
                map.addLayer(layer);
            });
    }
}


/**
 * Показать или скрыть гексогональную сетку на карте
 * */
function showHexMap() {
    if (document.querySelector("#hexMap").checked) {

        var format = new ol.format.WKT();

        $.getJSON("/api/calc/hexagon-map", {hexagonRadius: document.querySelector("#hexagonRadius").value}, function (data) {
            var features = data.map(function (wkt) {
                return format.readFeature(wkt);
            });
            var vectorSource = new ol.source.Vector({
                features: features
            });

            map.addLayer(
                new ol.layer.Vector({
                    source: vectorSource,
                    style: [
                        new ol.style.Style({
                            stroke: new ol.style.Stroke({
                                color: 'rgba(125,0,135,0.45)',
                                width: '1',
                                lineDash: [4]
                            })
                        })
                    ],
                    name: 'HEXMAP',
                    zIndex: 100
                })
            );
        });
    } else {
        clearMap('HEXMAP');
    }
}


//Сохранение карты в файл
document.getElementById('export-png').addEventListener('click', function () {
    map.once('rendercomplete', function () {
        const mapCanvas = document.createElement('canvas');
        const size = map.getSize();
        mapCanvas.width = size[0];
        mapCanvas.height = size[1];
        const mapContext = mapCanvas.getContext('2d');
        Array.prototype.forEach.call(
            map.getViewport().querySelectorAll('.ol-layer canvas, canvas.ol-layer'),
            function (canvas) {
                if (canvas.width > 0) {
                    const opacity =
                        canvas.parentNode.style.opacity || canvas.style.opacity;
                    mapContext.globalAlpha = opacity === '' ? 1 : Number(opacity);
                    let matrix;
                    const transform = canvas.style.transform;
                    if (transform) {
                        // Get the transform parameters from the style's transform matrix
                        matrix = transform
                            .match(/^matrix\(([^\(]*)\)$/)[1]
                            .split(',')
                            .map(Number);
                    } else {
                        matrix = [
                            parseFloat(canvas.style.width) / canvas.width,
                            0,
                            0,
                            parseFloat(canvas.style.height) / canvas.height,
                            0,
                            0,
                        ];
                    }
                    // Apply the transform to the export map context
                    CanvasRenderingContext2D.prototype.setTransform.apply(
                        mapContext,
                        matrix
                    );
                    const backgroundColor = canvas.parentNode.style.backgroundColor;
                    if (backgroundColor) {
                        mapContext.fillStyle = backgroundColor;
                        mapContext.fillRect(0, 0, canvas.width, canvas.height);
                    }
                    mapContext.drawImage(canvas, 0, 0);
                }
            }
        );
        mapContext.globalAlpha = 1;
        mapContext.setTransform(1, 0, 0, 1, 0, 0);
        const link = document.getElementById('image-download');
        link.href = mapCanvas.toDataURL();
        link.click();
    });
    map.renderSync();
});