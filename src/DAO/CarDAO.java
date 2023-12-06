package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import Objects.Car;
import Resources.StateConstants;
import Utils.Formats;
import Utils.ErrorRegistry;

public class CarDAO extends AbstractDAO<Car> {

    private static final String TABLE_NAME = "CARS";

    private boolean cloud;

    public CarDAO(boolean cloud) {
        this.cloud = cloud;
    }

    public ArrayList<Car> getCarsAvailables() {
        new StateConstants();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE STATE = ? ;";
        try (Connection connection = getConnectionFile();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, StateConstants.ACTIVE);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return resultSetToObject(rs);
            } catch (SQLException e) {
                new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
                }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
            }
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public boolean insert(Car car) {
        ArrayList<Car> cars = new ArrayList<>();
        cars.add(car);
        return (insert(cars));
    }

    @Override
    public boolean insert(ArrayList<Car> cars) {
        String query = "INSERT INTO " + TABLE_NAME + " (BRAND, MODEL, PATENT, COLOR, STATE, DATE_START) " +
                       "VALUES (?, ?, ?, ?, ?, ?)";
        return insertBatch(cloud ? getConnectionCloud() : getConnectionFile(), query, cars);
    }

    @Override
    public boolean save(Car car) {
        ArrayList<Car> cars = new ArrayList<>();
        cars.add(car);
        return save(cars);
    }

    @Override
    public boolean save(ArrayList<Car> cars) {
        String query = "INSERT INTO " + TABLE_NAME + " (BRAND, MODEL, PATENT, COLOR, STATE, DATE_START) " +
                       "VALUES (?, ?, ?, ?, ?, ?)";
        return insertBatch(getConnectionFile(), getConnectionCloud(), query, cars);
    }

    @Override
    public PreparedStatement prepareInsertStatement(PreparedStatement preparedStatement, Car car) {
        try {
            preparedStatement.setString(1, car.getBrand());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setString(3, car.getPatent());
            preparedStatement.setString(4, car.getColor());
            preparedStatement.setString(5, car.getState());
            preparedStatement.setTimestamp(6, new Timestamp(car.getDateStart().getTimeInMillis()));
            return preparedStatement;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public ArrayList<Car> read() {
        return resultSetToObject(read(cloud ? getConnectionCloud() : getConnectionFile(), TABLE_NAME));
    }

    @Override
    public ArrayList<Car> resultSetToObject(ResultSet rs) {
        ArrayList<Car> cars = new ArrayList<>();
        try {
            while (rs.next()) {
                Car car = new Car();
                car.setId(rs.getInt("ID"));
                car.setBrand(rs.getString("BRAND"));
                car.setModel(rs.getString("MODEL"));
                car.setPatent(rs.getString("PATENT"));
                car.setColor(rs.getString("COLOR"));
                car.setState(rs.getString("STATE"));
                car.setDateStart(new Formats().getCalendarToDateSQL(String.valueOf(rs.getTimestamp("DATE_START"))));
                cars.add(car);
            }
            return cars;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public boolean update(Car car) {
        String query = "UPDATE " + TABLE_NAME + " SET BRAND = ?, MODEL = ?, PATENT = ?, COLOR = ?, " +
                        "STATE = ?, DATE_START = ? WHERE ID = ?";
        return update(getConnectionFile(), getConnectionCloud(), query, car);
    }

    @Override
    public PreparedStatement prepareUpdateStatement(PreparedStatement preparedStatement, Connection connection, String query, Car car) {
        try {
            preparedStatement.setString(1, car.getBrand());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setString(3, car.getPatent());
            preparedStatement.setString(4, car.getColor());
            preparedStatement.setString(5, car.getState());
            preparedStatement.setTimestamp(6, new Timestamp(car.getDateStart().getTimeInMillis()));
            preparedStatement.setInt(7, car.getId());
            return preparedStatement;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        deleteById(cloud ? getConnectionCloud() : getConnectionFile(), TABLE_NAME, id);
    }

    @Override
    public void deleteAll() {
        deleteAll(cloud ? getConnectionCloud() : getConnectionFile(), TABLE_NAME);
    }

    @Override
    public void resetIdCounter() {
        resetIdCounter(cloud ? getConnectionCloud() : getConnectionFile(), TABLE_NAME, cloud);
    }

    @Override
    public int countAll() {
        return countAll(cloud ? getConnectionCloud() : getConnectionFile(), TABLE_NAME);
    }

}