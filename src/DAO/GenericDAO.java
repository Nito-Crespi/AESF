package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Resources.DBConstants;
import Utils.ErrorRegistry;

public interface GenericDAO<T> {

    boolean insert(T object);
    
    boolean insert(ArrayList<T> objects);

    boolean save(T object);
    
    boolean save(ArrayList<T> objects);

    PreparedStatement prepareInsertStatement(PreparedStatement preparedStatement, T object);

    default boolean insertBatch(Connection connectionFile, Connection connectionCloud, String query, ArrayList<T> objects) {
        PreparedStatement preparedStatementFile;
        PreparedStatement preparedStatementCloud;
        try {
            preparedStatementFile = connectionFile.prepareStatement(query);
            preparedStatementCloud = connectionCloud.prepareStatement(query);
            try {
                connectionFile.setAutoCommit(false);
                connectionCloud.setAutoCommit(false);
    
                for (T object : objects) {
                    preparedStatementFile = prepareInsertStatement(preparedStatementFile, object);
                    preparedStatementFile.addBatch(); 
                    preparedStatementCloud = prepareInsertStatement(preparedStatementCloud, object);
                    preparedStatementCloud.addBatch(); 
                }
        
                if (preparedStatementFile != null) {
                    preparedStatementFile.executeBatch();
                }
                if (preparedStatementCloud != null) {
                    preparedStatementCloud.executeBatch();
                }
                connectionFile.commit();
                connectionCloud.commit();

                return true;
            } catch (SQLException e) {
                if (connectionFile != null) {
                    try {
                        connectionFile.rollback(); 
                    } catch (SQLException ex) {
                        new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
                        }.getClass().getEnclosingMethod().getName()), String.valueOf(ex));
                    }
                }
                if (connectionCloud != null) {
                    try {
                        connectionCloud.rollback(); 
                    } catch (SQLException ex) {
                        new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
                        }.getClass().getEnclosingMethod().getName()), String.valueOf(ex));
                    }
                }
                new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
                }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
            } finally {
                if (connectionFile != null) {
                    try {
                        connectionFile.setAutoCommit(true); 
                        if (preparedStatementFile != null) {
                            preparedStatementFile.close(); 
                        }
                        connectionFile.close(); 
                    } catch (SQLException ex) {
                        new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
                        }.getClass().getEnclosingMethod().getName()), String.valueOf(ex));
                    }
                }
                if (connectionCloud != null) {
                    try {
                        connectionCloud.setAutoCommit(true); 
                        if (preparedStatementCloud != null) {
                            preparedStatementCloud.close(); 
                        }
                        connectionCloud.close(); 
                    } catch (SQLException ex) {
                        new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
                        }.getClass().getEnclosingMethod().getName()), String.valueOf(ex));
                    }
                }
            }
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return false;
    }

    default boolean insertBatch(Connection connection, String query, ArrayList<T> objects) {
        PreparedStatement preparedStatementFile;
        try {
            preparedStatementFile = connection.prepareStatement(query);
            try {
                connection.setAutoCommit(false);

                for (int i = 0; i < objects.size(); i++) {
                    preparedStatementFile = prepareInsertStatement(preparedStatementFile, objects.get(i));
                    preparedStatementFile.addBatch(); 
                }
        
                if (preparedStatementFile != null) {
                    preparedStatementFile.executeBatch();
                }

                connection.commit();

                return true;
            } catch (SQLException e) {
                if (connection != null) {
                    try {
                        connection.rollback(); 
                    } catch (SQLException ex) {
                        new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
                        }.getClass().getEnclosingMethod().getName()), String.valueOf(ex));
                    }
                }
                new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
                }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
            } finally {
                if (connection != null) {
                    try {
                        connection.setAutoCommit(true); 
                        if (preparedStatementFile != null) {
                            preparedStatementFile.close(); 
                        }
                        connection.close(); 
                    } catch (SQLException ex) {
                        new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
                        }.getClass().getEnclosingMethod().getName()), String.valueOf(ex));
                    }
                }
            }
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return false;
    }

    ArrayList<T> read();

    ArrayList<T> resultSetToObject(ResultSet rs);

    default ResultSet read(Connection connection, String tableName) {
        String query = "SELECT * FROM " + tableName;
        return read(connection, query, tableName);
    }

    default ResultSet read(Connection connection, String query, String tableName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            try {
                ResultSet rs = preparedStatement.executeQuery();
                return rs;
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

    boolean update(T object);

    PreparedStatement prepareUpdateStatement(PreparedStatement preparedStatement, Connection connection, String query, T object);

    default boolean update(Connection connectionFile, Connection connectionCloud, String query, T object) {
        PreparedStatement preparedStatementFile = null;
        PreparedStatement preparedStatementCloud = null;
        try {
            connectionFile.setAutoCommit(false);
            connectionCloud.setAutoCommit(false);
            try {
                preparedStatementFile = connectionFile.prepareStatement(query);
                preparedStatementCloud = connectionCloud.prepareStatement(query);

                preparedStatementFile = prepareUpdateStatement(preparedStatementFile, connectionFile, query, object);
                preparedStatementCloud = prepareUpdateStatement(preparedStatementCloud, connectionCloud, query, object);

                preparedStatementFile.executeUpdate();
                preparedStatementCloud.executeUpdate();

                connectionFile.commit();
                connectionCloud.commit();

                return true;
            } catch (SQLException e) {
                connectionFile.rollback();
                connectionCloud.rollback();
                new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
                }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
            }
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        } finally {
            if (connectionFile != null) {
                try {
                    connectionFile.setAutoCommit(true);
                    if (preparedStatementFile != null) {
                        preparedStatementFile.close();
                    }
                    connectionFile.close();
                } catch (SQLException ex) {
                    new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
                    }.getClass().getEnclosingMethod().getName()), String.valueOf(ex));
                }
            }
            if (connectionCloud != null) {
                try {
                    connectionCloud.setAutoCommit(true);
                    if (preparedStatementCloud != null) {
                        preparedStatementCloud.close();
                    }
                    connectionCloud.close();
                } catch (SQLException ex) {
                    new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
                    }.getClass().getEnclosingMethod().getName()), String.valueOf(ex));
                }
            }
        }
        return false;
    }

    void deleteById(int id);

    default void deleteById(Connection connection, String tableName, int id) {
        String query = "DELETE FROM " + tableName;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
    }

    void deleteAll();

    default void deleteAll(Connection connection, String tableName) {
        String query = "DELETE FROM " + tableName;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
    }

    void resetIdCounter();

    default void resetIdCounter(Connection connection, String tableName, boolean cloud) {
        String query = "";

        String system = cloud ? DBConstants.SYSTEM_CLOUD : DBConstants.SYSTEM_LOCAL;
        switch (system) {
            case "HSQLDB": query = "ALTER TABLE " + tableName + " ALTER id RESTART WITH 1;"; break;
            case "PostgreSQL": query = "SELECT setval(pg_get_serial_sequence('" + tableName + "', 'id'), 1, false)"; break;
            case "MySQL": query = "ALTER TABLE " + tableName + " AUTO_INCREMENT = 1;"; break;
        }

        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
    }
    
    int countAll();

    default int countAll(Connection connection, String tableName) {
        int count = 0;
        String query = "SELECT COUNT(*) AS COUNT FROM " + tableName;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    count = rs.getInt("COUNT");
                }
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return count;
    }
    
}