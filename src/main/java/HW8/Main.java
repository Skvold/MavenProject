package HW8;

import api.YandexApi;
import exception.YandexApiException;
import model.City;
import model.Weather;
import repository.DataBaseRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        Class.forName("org.sqlite.JDBC");
        DataBaseRepository repository = new DataBaseRepository();
        repository.initializeTables();

        YandexApi yandexApi = new YandexApi();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Введите команду: 'обновить', 'показать', 'завершить'");
            // ждем команду от пользователя
            String userRequest = reader.readLine();

            switch (userRequest) {
                case "обновить": {
                    try {
                        repository.cleanDb();
                        for (City city : City.values()) {
                            List<Weather> weathers = yandexApi.getWeather(city);
                            repository.saveWeather(weathers);
                        }
                        System.out.println("Погода успешно сохранена в базу данных");
                    } catch (YandexApiException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }

                case "показать": {
                    System.out.println("Введите город :");
                    String city = reader.readLine();
                    List<Weather> weatherFromDb = null;

                    for (City cityEnum : City.values()) {
                        if (city.equals(cityEnum.getName())) {
                            weatherFromDb = repository.getWeatherFromDb(cityEnum);
                            break;
                        }
                    }

                    if (weatherFromDb != null) {
                        if (weatherFromDb.size() == 0) {
                            System.out.println("Записей нет");
                        } else {
                            for (Weather weather : weatherFromDb) {
                                System.out.println(weather);
                            }
                        }
                    } else {
                        System.out.println("Программа не знает такого города");
                    }
                    break;
                }
                case "завершить": {
                    isRunning = false;
                    break;
                }
                default: {
                    System.out.println("такой команды нет");
                }
            }
        }


        System.out.println("Программа завершена");
    }
}
