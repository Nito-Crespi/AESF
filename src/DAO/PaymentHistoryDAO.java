package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import Objects.PaymentHistory;
import Utils.Formats;
import Utils.ErrorRegistry;

public class PaymentHistoryDAO extends AbstractDAO<PaymentHistory> {

    private static final String TABLE_NAME = "PAYMENT_HISTORIES";

    private boolean cloud;

    public PaymentHistoryDAO(boolean cloud) {
        this.cloud = cloud;
    }

    @Override
    public boolean insert(PaymentHistory paymentHistory) {
        ArrayList<PaymentHistory> paymentHistories = new ArrayList<>();
        paymentHistories.add(paymentHistory);
        return (insert(paymentHistories));
    }

    @Override
    public boolean insert(ArrayList<PaymentHistory> paymentHistories) {
        String query = "INSERT INTO " + TABLE_NAME + " (PAYMENT_METHOD, PRICE, STATE, ID_USERS, ID_PEOPLES, MODIFIED, DATE_START, DATE_END) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return insertBatch(cloud ? getConnectionCloud() : getConnectionFile(), query, paymentHistories);
    }

    @Override
    public boolean save(PaymentHistory paymentHistory) {
        ArrayList<PaymentHistory> paymentHistories = new ArrayList<>();
        paymentHistories.add(paymentHistory);
        return save(paymentHistories);
    }

    @Override
    public boolean save(ArrayList<PaymentHistory> paymentHistories) {
        String query = "INSERT INTO " + TABLE_NAME + " (PAYMENT_METHOD, PRICE, STATE, ID_USERS, ID_PEOPLES, MODIFIED, DATE_START, DATE_END) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return insertBatch(getConnectionFile(), getConnectionCloud(), query, paymentHistories);
    }

    @Override
    public PreparedStatement prepareInsertStatement(PreparedStatement preparedStatement, PaymentHistory paymentHistory) {
        try {
            preparedStatement.setString(1, paymentHistory.getPaymentMethod());
            preparedStatement.setInt(2, paymentHistory.getPrice());
            preparedStatement.setString(3, paymentHistory.getState());
            preparedStatement.setInt(4, paymentHistory.getIdUser());
            preparedStatement.setInt(5, paymentHistory.getIdPeople());
            preparedStatement.setInt(6, paymentHistory.getModified());
            preparedStatement.setTimestamp(7, new Timestamp(paymentHistory.getDateStart().getTimeInMillis()));
            preparedStatement.setTimestamp(8, new Timestamp(paymentHistory.getDateEnd().getTimeInMillis()));
            return preparedStatement;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public ArrayList<PaymentHistory> read() {
        return resultSetToObject(read(cloud ? getConnectionCloud() : getConnectionFile(), TABLE_NAME));
    }

    @Override
    public ArrayList<PaymentHistory> resultSetToObject(ResultSet rs) {
        ArrayList<PaymentHistory> paymentHistories = new ArrayList<>();
        try {
            while (rs.next()) {
                PaymentHistory paymentHistory = new PaymentHistory();
                paymentHistory.setId(rs.getInt("ID"));
                paymentHistory.setPaymentMethod(rs.getString("PAYMENT_METHOD"));
                paymentHistory.setPrice(rs.getInt("PRICE"));
                paymentHistory.setState(rs.getString("STATE"));
                paymentHistory.setIdUser(rs.getInt("ID_USERS"));
                paymentHistory.setIdPeople(rs.getInt("ID_PEOPLES"));
                paymentHistory.setModified(rs.getInt("MODIFIED"));
                paymentHistory.setDateStart(new Formats().getCalendarToDateSQL(String.valueOf(rs.getTimestamp("DATE_START"))));
                paymentHistory.setDateEnd(new Formats().getCalendarToDateSQL(String.valueOf(rs.getTimestamp("DATE_END"))));
                paymentHistories.add(paymentHistory);
            }
            return paymentHistories;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public boolean update(PaymentHistory paymentHistory) {
        String query = "UPDATE " + TABLE_NAME + " SET PAYMENT_METHOD = ?, PRICE = ?, STATE = ?, ID_USERS = ?, ID_PEOPLES = ?, MODIFIED = ?, DATE_START = ?, DATE_END = ? WHERE ID = ?";
        return update(getConnectionFile(), getConnectionCloud(), query, paymentHistory);
    }

    @Override
    public PreparedStatement prepareUpdateStatement(PreparedStatement preparedStatement, Connection connection, String query, PaymentHistory paymentHistory) {
        try {
            preparedStatement.setString(1, paymentHistory.getPaymentMethod());
            preparedStatement.setInt(2, paymentHistory.getPrice());
            preparedStatement.setString(3, paymentHistory.getState());
            preparedStatement.setInt(4, paymentHistory.getIdUser());
            preparedStatement.setInt(5, paymentHistory.getIdPeople());
            preparedStatement.setInt(6, paymentHistory.getModified());
            preparedStatement.setTimestamp(7, new Timestamp(paymentHistory.getDateStart().getTimeInMillis()));
            preparedStatement.setTimestamp(8, new Timestamp(paymentHistory.getDateEnd().getTimeInMillis()));
            preparedStatement.setInt(9, paymentHistory.getId());
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