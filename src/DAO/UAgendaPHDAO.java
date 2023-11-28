package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Objects.Agenda;
import Objects.PaymentHistory;
import Objects.Unifiers.UAgendaPH;
import Utils.ErrorRegistry;

public class UAgendaPHDAO extends AbstractDAO<UAgendaPH> {

    private static final String TABLE_NAME = "AGENDAS_PAYMENT_HISTORIES";

    private boolean cloud;

    public UAgendaPHDAO(boolean cloud) {
        this.cloud = cloud;
    }

    public boolean insertGroup(Agenda agenda, PaymentHistory paymentHistory, UAgendaPH uAgendaPH) {
        Connection conn1 = null;
        Connection conn2 = null;
        boolean success = false; // Inicialmente asu    mimos que no ha tenido éxito

        try {
            // Configura las conexiones a las dos bases de datos
            conn1 = getConnectionFile();
            conn2 = getConnectionCloud();

            // Inicia una transacción manual
            conn1.setAutoCommit(false);
            conn2.setAutoCommit(false);

            // Realiza operaciones INSERT en ambas bases de datos para la tabla AGENDAS
            String insertAgendasQuery = "INSERT INTO AGENDAS (ID_USERS, ID_PEOPLES, ID_INSTRUCTOR, ID_CAR, " +
                    "NAME_CLASS, MODIFIED, STATE, DATE_START, DATE_END, COMMENT) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Base de datos 1 (AGENDAS)
            try (PreparedStatement stmt1 = new AgendaDAO(false).prepareInsertStatement(conn1.prepareStatement(insertAgendasQuery), agenda)) {
                stmt1.executeUpdate();
            } catch (SQLException e) {
                new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
                }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
            }
            

            // Base de datos 2 (AGENDAS)
            try (PreparedStatement stmt2 = new AgendaDAO(false).prepareInsertStatement(conn2.prepareStatement(insertAgendasQuery), agenda)) {
                stmt2.executeUpdate();
            } catch (SQLException e) {
                new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
                }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
            }

            // Realiza operaciones INSERT en ambas bases de datos para la tabla
            // PAYMENT_HISTORY
            String insertPaymentHistoryQuery = "INSERT INTO PAYMENT_HISTORIES (PAYMENT_METHOD, PRICE, STATE, ID_USERS, ID_PEOPLES, MODIFIED, DATE_START, DATE_END) "
                    +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            if (paymentHistory != null) {
                // Base de datos 1 (PAYMENT_HISTORY)
                try (PreparedStatement stmt1 = new PaymentHistoryDAO(false).prepareInsertStatement(conn1.prepareStatement(insertPaymentHistoryQuery), paymentHistory)) {
                    stmt1.executeUpdate();
                } catch (SQLException e) {
                    new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
                    }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
                }

                // Base de datos 2 (PAYMENT_HISTORY)
                try (PreparedStatement stmt2 = new PaymentHistoryDAO(false).prepareInsertStatement(conn2.prepareStatement(insertPaymentHistoryQuery), paymentHistory)) {
                    stmt2.executeUpdate();
                } catch (SQLException e) {
                    new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
                    }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
                }
            }

            // Realiza operaciones INSERT en ambas bases de datos para la tabla
            // AGENDAS_PAYMENT_HISTORY
            String insertAgendasPaymentHistoryQuery = "INSERT INTO AGENDAS_PAYMENT_HISTORIES (ID_GROUP, ID_AGENDAS, ID_PAYMENT_HISTORY) "
                    +
                    "VALUES (?, ?, ?)";

            // Base de datos 1 (AGENDAS_PAYMENT_HISTORY)
            try (PreparedStatement stmt1 = new UAgendaPHDAO(false).prepareInsertStatement(conn1.prepareStatement(insertAgendasPaymentHistoryQuery), uAgendaPH)) {
                stmt1.executeUpdate();
            } catch (SQLException e) {
                new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
                }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
            }

            // Base de datos 2 (AGENDAS_PAYMENT_HISTORY)
            try (PreparedStatement stmt2 = new UAgendaPHDAO(false).prepareInsertStatement(conn2.prepareStatement(insertAgendasPaymentHistoryQuery), uAgendaPH)) {
                stmt2.executeUpdate();
            } catch (SQLException e) {
                new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
                }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
            }

            // Confirma las transacciones en ambas bases de datos
            conn1.commit();
            conn2.commit();

            // Si todo ha tenido éxito, establece la variable "success" a true
            success = true;
        } catch (SQLException e) {
            // Maneja el error y realiza rollback en caso de excepción
            try {
                if (conn1 != null) {
                    conn1.rollback();
                }
                if (conn2 != null) {
                    conn2.rollback();
                }
            } catch (SQLException rollbackException) {
                System.out.println(rollbackException);
            }
            System.out.println(e);
        } finally {
            // Cierra las conexiones
            try {
                if (conn1 != null) {
                    conn1.close();
                }
                if (conn2 != null) {
                    conn2.close();
                }
            } catch (SQLException closeException) {
                System.out.println(closeException);
            }
        }

        return success; // Devuelve true si todo ha ido bien, de lo contrario, devuelve false
    }

    public int getLastIdPaymentByIdGroup(int idGroup) {
        int count = -1;
        String query = "SELECT MAX(ID_PAYMENT) AS MAX_ID_PAYMENT FROM " + TABLE_NAME + " WHERE ID_GROUP = ?";
        try (Connection connection = getConnectionFile();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idGroup);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {  // Move the cursor to the first row
                    count = rs.getInt("MAX_ID_PAYMENT");
                }
            }
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return count;
    }

    public int getLastIdAgendaByIdGroup(int idGroup) {
        int count = -1;
        String query = "SELECT MAX(ID_AGENDAS) AS MAX_ID_AGENDAS FROM " + TABLE_NAME + " WHERE ID_GROUP = ?";
        try (Connection connection = getConnectionFile();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idGroup);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {  // Move the cursor to the first row
                    count = rs.getInt("MAX_ID_AGENDAS");
                }
            }
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return count;
    }

    public int getIdGroupByIdAgenda(int idAgenda) {
        int count = -1;
        String query = "SELECT ID_GROUP FROM " + TABLE_NAME + " WHERE ID_AGENDAS = ?";
        try (Connection connection = getConnectionFile();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idAgenda);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {  // Move the cursor to the first row
                    count = rs.getInt("ID_GROUP");
                }
            }
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return count;
    }

    public int nextGroup() {
        int count = 0;
        String query = "SELECT MAX(ID_GROUP) FROM " + TABLE_NAME;
        try (Connection connection = getConnectionFile();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    count = rs.getInt("C1");
                }
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return count;
    }

    public ArrayList<UAgendaPH> getUAgendaPHByIdAgenda(int idAgenda) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE ID_GROUP = (SELECT ID_GROUP FROM " + TABLE_NAME
                + " WHERE ID_AGENDAS = ? LIMIT 1)";
        try (Connection connection = getConnectionFile();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idAgenda);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return new UAgendaPHDAO(false).resultSetToObject(rs);
            }
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    public ArrayList<UAgendaPH> getUAgendaPHByNameClassAndIdPeople(String nameClass, int idPeople) {
        String query = "SELECT APH.* FROM " + TABLE_NAME
                + " APH JOIN AGENDAS A ON APH.ID_AGENDAS = A.ID WHERE A.NAME_CLASS = ? AND A.ID_PEOPLES = ?";
        try (Connection connection = getConnectionFile();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nameClass);
            preparedStatement.setInt(2, idPeople);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return new UAgendaPHDAO(false).resultSetToObject(rs);
            }
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    public ArrayList<Agenda> getAgendasByIdGroup(int idGroup) {
        String query = "SELECT A.* FROM AGENDAS A WHERE A.ID IN (SELECT APH.ID_AGENDAS FROM AGENDAS_PAYMENT_HISTORIES APH WHERE APH.ID_GROUP = ?);";
        try (Connection connection = getConnectionFile();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idGroup);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return new AgendaDAO(false).resultSetToObject(rs);
            }
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    public ArrayList<PaymentHistory> getPaymentHistoryByIdGroup(int idGroup) {
        String query = "SELECT P.* FROM PAYMENT_HISTORIES P WHERE P.ID IN (SELECT APH.ID_PAYMENT_HISTORY FROM AGENDAS_PAYMENT_HISTORIES APH WHERE APH.ID_GROUP = ?);";
        try (Connection connection = getConnectionFile();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idGroup);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return new PaymentHistoryDAO(false).resultSetToObject(rs);
            }
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean insert(UAgendaPH uAgendaPH) {
        ArrayList<UAgendaPH> uAgendaPHs = new ArrayList<>();
        uAgendaPHs.add(uAgendaPH);
        return (insert(uAgendaPHs));
    }

    @Override
    public boolean insert(ArrayList<UAgendaPH> uAgendaPHs) {
        String query = "INSERT INTO " + TABLE_NAME + " (ID_GROUP, ID_AGENDAS, ID_PAYMENT_HISTORY) " +
                "VALUES (?, ?, ?)";
        return insertBatch(cloud ? getConnectionCloud() : getConnectionFile(), query, uAgendaPHs);
    }

    @Override
    public boolean save(UAgendaPH uAgendaPH) {
        ArrayList<UAgendaPH> uAgendaPHs = new ArrayList<>();
        uAgendaPHs.add(uAgendaPH);
        return save(uAgendaPHs);
    }

    @Override
    public boolean save(ArrayList<UAgendaPH> uAgendaPHs) {
        String query = "INSERT INTO " + TABLE_NAME + " (ID_GROUP, ID_AGENDAS, ID_PAYMENT_HISTORY) " +
                "VALUES (?, ?, ?)";
        return insertBatch(getConnectionFile(), getConnectionCloud(), query, uAgendaPHs);
    }

    @Override
    public PreparedStatement prepareInsertStatement(PreparedStatement preparedStatement, UAgendaPH uAgendaPH) {
        try {
            preparedStatement.setInt(1, uAgendaPH.getIdGroup());
            preparedStatement.setInt(2, uAgendaPH.getIdAgenda());
            preparedStatement.setInt(3, uAgendaPH.getIdPaymentHistory());
            return preparedStatement;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public ArrayList<UAgendaPH> read() {
        return resultSetToObject(read(cloud ? getConnectionCloud() : getConnectionFile(), TABLE_NAME));
    }

    @Override
    public ArrayList<UAgendaPH> resultSetToObject(ResultSet rs) {
        ArrayList<UAgendaPH> uAgendaPHs = new ArrayList<>();
        try {
            while (rs.next()) {
                UAgendaPH uAgendaPH = new UAgendaPH();
                uAgendaPH.setId(rs.getInt("ID"));
                uAgendaPH.setIdGroup(rs.getInt("ID_GROUP"));
                uAgendaPH.setIdAgenda(rs.getInt("ID_AGENDAS"));
                uAgendaPH.setIdPaymentHistory(rs.getInt("ID_PAYMENT_HISTORY"));
                uAgendaPHs.add(uAgendaPH);
            }
            return uAgendaPHs;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public boolean update(UAgendaPH uAgendaPH) {
        String query = "UPDATE " + TABLE_NAME + " SET ID_GROUP = ?, ID_AGENDAS = ?, ID_PAYMENT_HISTORY = ? WHERE ID = ?";
        return update(getConnectionFile(), getConnectionCloud(), query, uAgendaPH);
    }

    @Override
    public PreparedStatement prepareUpdateStatement(PreparedStatement preparedStatement, Connection connection, String query, UAgendaPH uAgendaPH) {
        try {
            preparedStatement.setInt(1, uAgendaPH.getIdGroup());
            preparedStatement.setInt(2, uAgendaPH.getIdAgenda());
            preparedStatement.setInt(3, uAgendaPH.getIdPaymentHistory());
            preparedStatement.setInt(4, uAgendaPH.getId());
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