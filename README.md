# iFood Backend Advanced Test
Create a micro-service able to accept RESTful requests receiving as parameter either city name or lat long coordinates and returns a playlist (only track names is fine) suggestion according to the current temperature.
The service was elaborated using mainly:
* Spring Boot 2
* Hazel Cast
* Spring Cloud
* Feign
* Hytrix
* Lombok
* etc

## Required tools

* JDK 8.0 (or higher)
* Maven 3.5.0 (or higher)
* Docker 17 (or higher)
* Docker compose 1.22

## How to run
### Using docker:
1. clone the repository
2. go to the root of the repository
3. edit the docker-compose file located at src/main/docker/docker-compose.yml and fill:
    1. OPEN_WEATHER_API_KEY with open weather api key(https://openweathermap.org/),
    2. SPOTIFY_CLIENT_ID with spotify client id (https://developer.spotify.com/),
    3. SPOTIFY_CLIENT_SECRET spotify client secret (https://developer.spotify.com/).
4. mvn clean install
5. mvn docker:build
6. cd src/main/docker/
7. docker-compose up ifood

### Without docker
1. Fill the variables OPEN_WEATHER_API_KEY, SPOTIFY_CLIENT_ID and SPOTIFY_CLIENT_SECRET or pass the keys when run the application
2. mvn clean install
3. mvn spring-boot:run

## API calls example
### Endpoint to get music recommendation given the city:
> curl -X GET \
  'http://127.0.0.1:8443/recommendation/city?city=campinas' \
  -H 'accept: application/json' \
  -H 'cache-control: no-cache'
### Endpoint to get music recommendation given the geolocation:
> curl -X GET \
  'http://127.0.0.1:8443/recommendation/geolocation?lat=50&lon=-5000' \
  -H 'cache-control: no-cache'

## Fallback strategies
- If the container crashes it will be restarted automatically by docker-compose.
- The service uses hazel cast as cache manager in order to keep the service working if the third-party api is down
- The second fallback strategy is to use the circuit break implementation from hytrix and load a statical response on fallback to give
a result to the end user while the doesn't re-establish.
So the service continuing responsive even 
*NOTE: This response will not be cached, because if the third-party returns to work the user can retrieve an updated data*

## Business rules

* If temperature (celcius) is above 30 degrees, suggest tracks for party
* In case temperature is between 15 and 30 degrees, suggest pop music tracks
* If it's a bit chilly (between 10 and 14 degrees), suggest rock music tracks
* Otherwise, if it's freezing outside, suggests classical music tracks

## Hints

You can make usage of OpenWeatherMaps API (https://openweathermap.org) to fetch temperature data and Spotify (https://developer.spotify.com) to suggest the tracks as part of the playlist.

## Non functional requirements

As this service will be a worldwide success, it must be prepared to be fault tolerant, responsive and resilient.

Use whatever language, tools and frameworks you feel comfortable to, and briefly elaborate on your solution, architecture details, choice of patterns and frameworks.

Also, make it easy to deploy/run your service(s) locally (consider using some container/vm solution for this). Once done, share your code with us.
