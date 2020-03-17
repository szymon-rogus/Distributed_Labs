var express = require('express');
var app = express();
var cors = require('cors');
var path = require('path');
const fetch = require("node-fetch");

app.use(cors());
var bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
  extended: true
}));
app.use(express.json({limit : '1mb'}));

var server = app.listen(5000, () => {
    console.log('Node server is running at port 5000...');
});

app.get('/', (request, response) => {
    response.sendFile(path.join(__dirname + '/index.html'));
});

app.get('/response', (request, response) => {
    response.sendFile(path.join(__dirname + '/response.html'));
});

app.post('/api', (request, response) => {
    console.log("Got response!");
    console.log(request.body);

    var country = request.body.country;
    const url = "https://restcountries.eu/rest/v2/name/" + country;
    console.log(url);

    fetch(url, {
        method: "GET",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    })
    .then(response => response.json())
    .then(data => {
        language = data[0].languages[0].iso639_1;
        var region = data[0].region;
        var capital = data[0].capital;

        var nextUrl = "https://restcountries.eu/rest/v2/lang/" + language;

        language = language + " (" + data[0].languages[0].name + ")";

        fetch(nextUrl, {
            method: 'GET',
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        })
        .then(response => response.json())
        .then(data => {
            var ammount = 0;
            var population = 0;
            var list = [];
            for (var el in data) {
                ammount = ammount + 1;
                list.push(data[el].name);
                population = population + data[el].population;
            }

            var nextUrl2 = "https://restcountries.eu/rest/v2/region/" + region;

            fetch(nextUrl2, {
                method: 'GET',
                headers: {
                    "Content-type": "application/json; charset=UTF-8"
                }
            })
            .then(response => response.json())
            .then( data => {

                var nextUrl3 = "https://api.openweathermap.org/data/2.5/weather?q=" + capital +"&appid=65b6d8dda0cef4577b29d1091c0bdd84"

                fetch(nextUrl3, {
                    method: 'GET',
                    headers: {
                        "Content-type": "application/json; charset=UTF-8"
                    }
                })
                .then(response => response.json())
                .then(data => {
                    var temp = Math.round(data.main.temp - 273);

                    response.json({
                        status: 'success',
                        language: language,
                        capital: capital,
                        temp: temp,
                        region: region,
                        ammount: ammount,
                        countries: list,
                        fullPopulation: population
                    });
                })
                .catch(error => {	
                    console.log(error.status);  	
                    if (error.status === 404) {
                        console.log("404 error");
                    };
                });                
            }).catch(error => {	
                console.log(error.status);  	
                if (error.status === 404) {
                    console.log("404 error");
                };
            });
        })
        .catch(error => {	
            console.log(error.status);  	
            if (error.status === 404) {
                console.log("404 error");
            };
        });
    })
    .catch(error => {	
        console.log(error.status);  	
		if (error.status === 404) {
            console.log("404 error");
        };
    });
});

