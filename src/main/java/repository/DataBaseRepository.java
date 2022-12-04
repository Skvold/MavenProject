package repository;

import model.City;
import model.Weather;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseRepository {

    private static final String dbUrl = "jdbc:sqlite:weatherDB.db";

    /**
     * Метод инициализации таблицы погоды. Вызывается при старте программы и служит для проверки наличия необходимой
     * таблицы в базе. Если таблицы нет - создает ее со всеми нужными столбцами.
     */
    public void initializeTables() {
        String initWeatherTableSql =
                "        CREATE TABLE IF NOT EXISTS Weather (\n" +
                        "                id                INTEGER       PRIMARY KEY AUTOINCREMENT,\n" +
                        "                city              VARCHAR (255),\n" +
                        "                date              VARCHAR (255),\n" +
                        "                night_temperature INTEGER,\n" +
                        "                day_temperature   INTEGER,\n" +
                        "                night_condition   VARCHAR (255),\n" +
                        "                day_condition     VARCHAR (255)\n" +
                        "        );";
        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(initWeatherTableSql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод сохранения объектов типа Weather, полученных из Яндекса, в базу данных
     * @param weathers список объектов погоды
     */
    public void saveWeather(List<Weather> weathers) {
        String saveWeatherSql =
                "INSERT INTO Weather (city, date, night_temperature, day_temperature, night_condition, day_condition) " +
                        "VALUES (?,?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(saveWeatherSql)) {

            for (Weather weather : weathers) {
                preparedStatement.setString(1, weather.getCity());
                preparedStatement.setString(2, weather.getDate());
                preparedStatement.setInt(3, weather.getNightTemperature());
                preparedStatement.setInt(4, weather.getDayTemperature());
                preparedStatement.setString(5, weather.getNightCondition());
                preparedStatement.setString(6, weather.getDayCondition());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод получения данных из базы и превращения их в объекты типа Weather
     * @return список полученных объектов из базы.
     */
    public List<Weather> getWeatherFromDb(City city) {
        String getWeatherSql = "SELECT * FROM Weather WHERE city = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(getWeatherSql)) {
            preparedStatement.setString(1, city.getName());

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Weather> weathers = new ArrayList<>();
            while (resultSet.next()) {
                String cityString = resultSet.getString("city");
                String date = resultSet.getString("date");
                int nightTemperature = resultSet.getInt("night_temperature");
                int dayTemperature = resultSet.getInt("day_temperature");
                String nightCondition = resultSet.getString("night_condition");
                String dayCondition = resultSet.getString("day_condition");

                Weather weather = new Weather(cityString, date, nightTemperature, dayTemperature, nightCondition, dayCondition);
                weathers.add(weather);
            }
            return weathers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Метод очистки таблицы "погода" в базе данных
     */
    public void cleanDb() {
        String cleanWeatherSql = "DELETE FROM Weather";
        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(cleanWeatherSql)) {
            preparedStatement.execute();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
