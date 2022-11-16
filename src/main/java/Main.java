import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Main {

    private final static String YANDEX_KEY = "f945d415-0328-415c-93e3-6df0d4664c83";
    private final static String YANDEXApiKeyHeaderName = "X-Yandex-API-Key";
    private final static String yandexApiUrl = "https://api.weather.yandex.ru/v2/forecast";
    private final static String lat = "59.939079";
    private final static String lon = "30.315766";

    public static void main(String[] args) throws IOException {

        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.weather.yandex.ru")
                .addPathSegment("v2")
                .addPathSegment("forecast")
                .addQueryParameter("lat", lat)
                .addQueryParameter("lon", lon)
                .addQueryParameter("limit", "5")
                .addQueryParameter("lang", "ru_RU")
                .addQueryParameter("extra", "false")
                .addQueryParameter("hours", "false")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader(YANDEXApiKeyHeaderName, YANDEX_KEY)
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() == 200) {
            Map<String, Object> responseMap = mapper.readValue(response.body().string(), Map.class);
            Map geoObject = (Map) responseMap.get("geo_object");
            Map locality = (Map) geoObject.get("locality");

            System.out.println("Получена погода для города " + locality.get("name"));
            System.out.println();
            System.out.println("==================================================");
            System.out.println();

            ArrayList dataList = (ArrayList) responseMap.get("forecasts");


            for (Object oneDayData: dataList) {
                Map oneDayMap = (Map) oneDayData;
                System.out.println("Погода на дату: " + oneDayMap.get("date"));

                Map parts = (Map) oneDayMap.get("parts");
                Map night = (Map) parts.get("night");
                System.out.println("Минимальная температура ночью:" + night.get("temp_min"));
                System.out.println("Средняя температура ночью:" + night.get("temp_avg"));
                System.out.println("Максимальная температура ночью:" + night.get("temp_max"));
                System.out.println("Скорость ветра ночью:" + night.get("wind_speed"));
                System.out.println();
                Map day = (Map) parts.get("day");
                System.out.println("Минимальная температура днем:" + day.get("temp_min"));
                System.out.println("Средняя температура днем:" + day.get("temp_avg"));
                System.out.println("Максимальная температура днем:" + day.get("temp_max"));
                System.out.println("Скорость ветра днем:" + night.get("wind_speed"));
                System.out.println();

                System.out.println();
                System.out.println("==================================================");
                System.out.println();
            }
        } else {
            System.out.println("Не получилось. Вернулся код:" + response.code());
        }

    }
}
