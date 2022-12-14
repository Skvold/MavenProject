package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import exception.YandexApiException;
import model.City;
import model.Weather;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import references.References;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YandexApi {

    private final static String yandexKey = "f945d415-0328-415c-93e3-6df0d4664c83";
    private final static String yandexApiKeyHeaderName = "X-Yandex-API-Key";

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Weather> getWeather(City city) throws IOException, YandexApiException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.weather.yandex.ru")
                .addPathSegment("v2")
                .addPathSegment("forecast")
                .addQueryParameter("lat", city.getLat())
                .addQueryParameter("lon", city.getLon())
                .addQueryParameter("limit", "5")
                .addQueryParameter("lang", "ru_RU")
                .addQueryParameter("extra", "false")
                .addQueryParameter("hours", "false")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader(yandexApiKeyHeaderName, yandexKey)
                .build();
        Response response = client.newCall(request).execute();

        if (response.code() == 200) {
            List<Weather> result = new ArrayList<>();
            Map<String, Object> responseMap = mapper.readValue(response.body().string(), Map.class);
            Map geoObject = (Map) responseMap.get("geo_object");
            Map locality = (Map) geoObject.get("locality");

            String cityFromJson = locality.get("name").toString();

            List dataList = (ArrayList) responseMap.get("forecasts");

            for (Object oneDayData : dataList) {
                Map oneDayMap = (Map) oneDayData;
                String date = oneDayMap.get("date").toString();

                Map parts = (Map) oneDayMap.get("parts");
                Map night = (Map) parts.get("night");
                int nightTemperature = Integer.parseInt(night.get("temp_avg").toString());
                String nightConditionKey = night.get("condition").toString();

                Map day = (Map) parts.get("day");
                int dayTemperature = Integer.parseInt(day.get("temp_avg").toString());
                String dayConditionKey = day.get("condition").toString();

                Weather weather = new Weather(cityFromJson, date, nightTemperature, dayTemperature,
                        References.yandexApiConditions.getOrDefault(nightConditionKey, nightConditionKey),
                        References.yandexApiConditions.getOrDefault(dayConditionKey, dayConditionKey));
                result.add(weather);
            }
            return result;
        } else {
            throw new YandexApiException("???? ???????????????????? ?????????????????? ????????????. ?????? ????????????: " + response.code());
        }
    }
}
