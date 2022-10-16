$(function() {
    const map = new ol.Map({
        target: 'map',
        layers: [
            new ol.layer.Tile({
                source: new ol.source.OSM()
            }),
            new ol.layer.Heatmap({
                source: new ol.source.Vector({
                    url: '/jobs/' + $("#job_id").text() + ".kml",
                    format: new ol.format.KML({
                        extractStyles: false,
                    }),
                }),
                blur: parseInt(10),
                radius: parseInt(10),
                weight: function (feature) {
                    const area = parseFloat(feature.get('area'));
                    return area;
                },
            })
        ],
        view: new ol.View({
            center: ol.proj.transform([37.646930, 55.725146], 'EPSG:4326', 'EPSG:3857'),
            zoom: 11
        })
    });
});