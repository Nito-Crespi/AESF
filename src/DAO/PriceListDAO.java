package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import Objects.PriceList;
import Utils.Formats;
import Utils.ErrorRegistry;

public class PriceListDAO extends AbstractDAO<PriceList> {

    private static final String TABLE_NAME = "PRICE_LISTS";

    private boolean cloud;

    public PriceListDAO(boolean cloud) {
        this.cloud = cloud;
    }

    @Override
    public boolean insert(PriceList priceList) {
        ArrayList<PriceList> priceLists = new ArrayList<>();
        priceLists.add(priceList);
        return (insert(priceLists));
    }

    @Override
    public boolean insert(ArrayList<PriceList> priceLists) {
        String query = "INSERT INTO " + TABLE_NAME + " (NAME, PRICE, NUMBER_OF_CLASSES, STATE, DATE_START) " +
                       "VALUES (?, ?, ?, ?, ?)";
        return insertBatch(cloud ? getConnectionCloud() : getConnectionFile(), query, priceLists);
    }

    @Override
    public boolean save(PriceList priceList) {
        ArrayList<PriceList> priceLists = new ArrayList<>();
        priceLists.add(priceList);
        return save(priceLists);
    }

    @Override
    public boolean save(ArrayList<PriceList> priceLists) {
        String query = "INSERT INTO " + TABLE_NAME + " (NAME, PRICE, NUMBER_OF_CLASSES, STATE, DATE_START) " +
                       "VALUES (?, ?, ?, ?, ?)";
        return insertBatch(getConnectionFile(), getConnectionCloud(), query, priceLists);
    }

    @Override
    public PreparedStatement prepareInsertStatement(PreparedStatement preparedStatement, PriceList priceList) {
        try {
            preparedStatement.setString(1, priceList.getName());
            preparedStatement.setInt(2, priceList.getPrice());
            preparedStatement.setInt(3, priceList.getNumberOfClasses());
            preparedStatement.setString(4, priceList.getState());
            preparedStatement.setTimestamp(5, new Timestamp(priceList.getDateStart().getTimeInMillis()));
            return preparedStatement;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public ArrayList<PriceList> read() {
        return resultSetToObject(read(cloud ? getConnectionCloud() : getConnectionFile(), TABLE_NAME));
    }

    @Override
    public ArrayList<PriceList> resultSetToObject(ResultSet rs) {
        ArrayList<PriceList> priceLists = new ArrayList<>();
        try {
            while (rs.next()) {
                PriceList priceList = new PriceList();
                priceList.setId(rs.getInt("ID"));
                priceList.setName(rs.getString("NAME"));
                priceList.setPrice(rs.getInt("PRICE"));
                priceList.setNumberOfClasses(rs.getInt("NUMBER_OF_CLASSES"));
                priceList.setState(rs.getString("STATE"));
                priceList.setDateStart(new Formats().getCalendarToDateSQL(String.valueOf(rs.getTimestamp("DATE_START"))));
                priceLists.add(priceList);
            }
            return priceLists;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public boolean update(PriceList priceList) {
        String query = "UPDATE " + TABLE_NAME + " SET NAME = ?, PRICE = ?, NUMBER_OF_CLASSES = ?, STATE = ?, DATE_START = ? WHERE ID = ?";
        return update(getConnectionFile(), getConnectionCloud(), query, priceList);
    }

    @Override
    public PreparedStatement prepareUpdateStatement(PreparedStatement preparedStatement, Connection connection, String query, PriceList priceList) {
        try {
            preparedStatement.setString(1, priceList.getName());
            preparedStatement.setInt(2, priceList.getPrice());
            preparedStatement.setInt(3, priceList.getNumberOfClasses());
            preparedStatement.setString(4, priceList.getState());
            preparedStatement.setTimestamp(5, new Timestamp(priceList.getDateStart().getTimeInMillis()));
            preparedStatement.setInt(6, priceList.getId());
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