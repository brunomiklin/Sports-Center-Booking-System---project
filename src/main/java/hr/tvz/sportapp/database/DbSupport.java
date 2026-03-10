package hr.tvz.sportapp.database;

import hr.tvz.sportapp.exceptions.DataBaseException;


import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

public interface DbSupport<T> {

  String DATA_FILE = "src/main/resources/database.properties";

  default Connection connectToDatbase() throws DataBaseException
    {
        try(FileReader reader = new FileReader(DATA_FILE))
        {
            Properties properties = new Properties();
            properties.load(reader);

            String URL = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");

            System.out.println("uspješno spajanje na bazu!");
           return DriverManager.getConnection(URL,username,password);

        }catch (SQLException | IOException e)
        {
                throw new DataBaseException(e);
        }

    }
    default void closeConnection(Connection conn) throws DataBaseException
    {
        try {
            conn.close();
        }catch (SQLException e)
        {
            throw new DataBaseException(e);
        }


    }

    Set<T> getAll() throws DataBaseException;
    Optional<T> getOneById(String id) throws DataBaseException;
    void insertIntoDb(T temp) throws DataBaseException;


}
