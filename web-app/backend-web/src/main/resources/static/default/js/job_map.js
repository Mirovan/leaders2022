$(function() {
    $.get("/api/jobs/" + $("#job_id").text() + ".txt", function(txt) {
        const lines = txt.split("\n");
        features = [];
        for (const l of lines) {
            const line = l.trim().split(" ");
            if (line.length > 0) {
                const id = Number(line[0]);
                const value = Number(line[1]);
                const lat = Number(line[2]);
                const lon = Number(line[3]);
                const p = new ol.geom.Point(ol.proj.transform([lat, lon], 'EPSG:4326', 'EPSG:3857'));
                features.push(new ol.Feature({ geometry: p, area: value }));
            }
        }
        const vectorLayer = new ol.source.Vector({ features: features });
        const map = new ol.Map({
            target: 'map',
            layers: [
                new ol.layer.Tile({
                    source: new ol.source.OSM()
                }),
                new ol.layer.Heatmap({
                    source: vectorLayer,
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
});