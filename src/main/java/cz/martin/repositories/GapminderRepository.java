package cz.martin.repositories;

import cz.martin.models.Country;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class GapminderRepository {

    private String databaseURL = "jdbc:mariadb://localhost/gapminder?user=root&password=password";

    public List<Integer> getYears() {
        List<Integer> years = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(this.databaseURL);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("""
                            SELECT DISTINCT G.year
                            FROM gapminder AS G
                            ORDER BY G.year
                        """)
        ) {
            while (resultSet.next()) {
                years.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return years;
    }

    public List<Country> getCountries(int year) {
        List<Country> countries = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(databaseURL);
                PreparedStatement statement = connection.prepareStatement("""
                            SELECT G.country, G.lifeExp
                            FROM gapminder AS G
                            WHERE G.year = ?
                            ORDER BY G.country
                        """);
        ) {
            statement.setInt(1, year);
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    countries.add(new Country(resultSet.getString(1), Math.round(resultSet.getDouble(2) * 1000) / 1000.0));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return countries;
    }

    public List<Country> getContinents() {
        List<Country> continents = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(this.databaseURL);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("""
                            SELECT G.continent, AVG(G.lifeExp)
                            FROM gapminder AS G
                            GROUP BY G.continent
                            ORDER BY G.continent
                        """)
        ) {
            while (resultSet.next()) {
                continents.add(new Country(resultSet.getString(1), Math.round(resultSet.getDouble(2) * 1000) / 1000.0));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return continents;
    }

    public List<Country> getContinentsYear(int year) {
        List<Country> continents = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(databaseURL);
                PreparedStatement statement = connection.prepareStatement("""
                            SELECT G.continent, AVG(G.lifeExp)
                            FROM gapminder AS G
                            WHERE G.year = ?
                            GROUP BY G.continent
                            ORDER BY G.continent
                        """);
        ) {
            statement.setInt(1, year);
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    continents.add(new Country(resultSet.getString(1), Math.round(resultSet.getDouble(2) * 1000) / 1000.0));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return continents;
    }
}
