package references;

import java.util.HashMap;
import java.util.Map;

public class References {
    public static final Map<String, String> yandexApiConditions = new HashMap<String, String>() {{
        put("clear", "ясно");
        put("partly-cloudy", "малооблаков");
        put("cloudy", "облачно с прояснениями");
        put("overcast", "пасмурно");
        put("partly-cloudy-and-light-rain", "малооблачно, небольшой дождь");
        put("partly-cloudy-and-rain", "малооблачно, дождь");
        put("overcast-and-rain", "значительная облачность, сильный дождь");
        put("overcast-thunderstorms-with-rain", "сильный дождь с грозой");
        put("cloudy-and-light-rain", "облачно, небольшой дождь");
        put("overcast-and-light-rain", "значительная облачность, небольшой дождь");
        put("cloudy-and-rain", "облачно, дождь");
        put("overcast-and-wet-snow", "дождь со снегом");
        put("partly-cloudy-and-light-snow", "небольшой снег");
        put("partly-cloudy-and-snow", "малооблачно, снег");
        put("overcast-and-snow", "снегопад");
        put("cloudy-and-light-snow", "облачно, небольшой снег");
        put("overcast-and-light-snow", "значительная облачность, небольшой снег");
        put("cloudy-and-snow", "облачно, снег");
        put("light-rain", "небольшой дождь");
        put("light-snow", "небольшой снег");
        put("wet-snow", "дождь со снегом");
    }};
}
