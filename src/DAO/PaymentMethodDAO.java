package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Objects.PaymentMethod;
import Utils.ErrorRegistry;

public class PaymentMethodDAO extends AbstractDAO<PaymentMethod> {

    private static final String TABLE_NAME = "PAYMENT_METHOD";

    private boolean cloud;

    public PaymentMethodDAO(boolean cloud) {
        this.cloud = cloud;
    }

    @Override
    public boolean insert(PaymentMethod paymentMethod) {
        ArrayList<PaymentMethod> paymentMethods = new ArrayList<>();
        paymentMethods.add(paymentMethod);
        return (insert(paymentMethods));
    }

    @Override
    public boolean insert(ArrayList<PaymentMethod> paymentMethods) {
        String query = "INSERT INTO " + TABLE_NAME + " (NAME, STATE) VALUES (?, ?)";
        return insertBatch(cloud ? getConnectionCloud() : getConnectionFile(), query, paymentMethods);
    }

    @Override
    public boolean save(PaymentMethod paymentMethod) {
        ArrayList<PaymentMethod> paymentMethods = new ArrayList<>();
        paymentMethods.add(paymentMethod);
        return save(paymentMethods);
    }

    @Override
    public boolean save(ArrayList<PaymentMethod> paymentMethods) {
        String query = "INSERT INTO " + TABLE_NAME + " (NAME, STATE) VALUES (?, ?)";
        return insertBatch(getConnectionFile(), getConnectionCloud(), query, paymentMethods);
    }

    @Override
    public PreparedStatement prepareInsertStatement(PreparedStatement preparedStatement, PaymentMethod paymentMethod) {
        try {
            preparedStatement.setString(1, paymentMethod.getName());
            preparedStatement.setString(2, paymentMethod.getState());
            return preparedStatement;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public ArrayList<PaymentMethod> read() {
        return resultSetToObject(read(cloud ? getConnectionCloud() : getConnectionFile(), TABLE_NAME));
    }

    @Override
    public ArrayList<PaymentMethod> resultSetToObject(ResultSet rs) {
        ArrayList<PaymentMethod> paymentMethods = new ArrayList<>();
        try {
            while (rs.next()) {
                PaymentMethod paymentMethod = new PaymentMethod();
                paymentMethod.setId(rs.getInt("ID"));
                paymentMethod.setName(rs.getString("NAME"));
                paymentMethod.setState(rs.getString("STATE"));
                paymentMethods.add(paymentMethod);
            }
            return paymentMethods;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public boolean update(PaymentMethod paymentMethod) {
        String query = "UPDATE " + TABLE_NAME + " SET NAME = ?, STATE = ? WHERE ID = ?";
        return update(getConnectionFile(), getConnectionCloud(), query, paymentMethod);
    }

    @Override
    public PreparedStatement prepareUpdateStatement(PreparedStatement preparedStatement, Connection connection, String query, PaymentMethod paymentMethod) {
        try {
            preparedStatement.setString(1, paymentMethod.getName());
            preparedStatement.setString(2, paymentMethod.getState());
            preparedStatement.setInt(3, paymentMethod.getId());
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