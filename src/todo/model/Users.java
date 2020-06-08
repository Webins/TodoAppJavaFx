package todo.model;

import todo.db.dbHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Users {
    private String name;
    private String username;
    private String password;
    private String gender;
    private String location;

    public Users(String name, String username, String password, String gender, String location) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.location = location;
    }

    public Users() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

  public void insert(Users user) throws SQLException, ClassNotFoundException {
      String sql = "INSERT INTO Users (name, username, password, gender, location) VALUES (?, ?, ?, ?, ?)";
      PreparedStatement preparedStatement = dbHandler.getDbConnection().prepareStatement(sql);

      preparedStatement.setString(1, user.getName());
      preparedStatement.setString(2, user.getUsername());
      preparedStatement.setString(3, user.getPassword());
      preparedStatement.setString(4, user.getGender());
      preparedStatement.setString(5, user.getLocation());

      preparedStatement.executeUpdate();
      preparedStatement.close();
  }

  public ResultSet select(String sql) throws SQLException, ClassNotFoundException {
          PreparedStatement preparedStatement = dbHandler.getDbConnection().prepareStatement(sql);
          ResultSet resultSet = preparedStatement.executeQuery();
          return resultSet;

    }

    public Integer getId(String username) throws SQLException, ClassNotFoundException {
        Integer id;
        String sql = "SELECT user_id FROM Users WHERE username = \"" +username+ "\";";
        PreparedStatement preparedStatement = dbHandler.getDbConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        id = resultSet.getInt(1);
        return id;
    }
}


