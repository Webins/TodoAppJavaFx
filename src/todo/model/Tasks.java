package todo.model;

import todo.db.dbHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Tasks {
    private int taskid;
    private int userid;
    private String task;
    private String description;
    private Timestamp createdAt;
    public int count;

    public Tasks(int userid, String task, String description) {
        this.userid = userid;
        this.task = task;
        this.description = description;
    }
    public Tasks(int userid, String task, String description, Timestamp ts) {
        this.userid = userid;
        this.task = task;
        this.description = description;
        this.createdAt = ts;
    }
    public Tasks(int taskid, String task, String description, boolean flag){
        this.taskid = taskid;
        this.task = task;
        this.description = description;
    }
    public Tasks(int taskid, int userid, String task, String description, Timestamp ts) {
        this.taskid = taskid;
        this.userid = userid;
        this.task = task;
        this.description = description;
        this.createdAt = ts;
    }

    public Tasks() {
    }
    public void insert(Tasks tasks) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Tasks (user_id, task, description) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = dbHandler.getDbConnection().prepareStatement(sql);

        preparedStatement.setInt(1, tasks.getUserid());
        preparedStatement.setString(2, tasks.getTask());
        preparedStatement.setString(3, tasks.getDescription());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    public void update(Tasks task) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Tasks SET task = ?, description=? WHERE task_id = ? ";
        PreparedStatement preparedStatement = dbHandler.getDbConnection().prepareStatement(sql);
        preparedStatement.setString(1, task.getTask());
        preparedStatement.setString(2, task.getDescription());
        preparedStatement.setInt(3, task.getTaskid());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public ArrayList<Tasks> getAllTask(Integer userId) throws SQLException, ClassNotFoundException {
        int i = 0;
        ArrayList<Tasks> tasksArrayList = new ArrayList<>();
        String sql = "SELECT task_id, user_id, task, description, created_at FROM Tasks WHERE user_id=\""+ userId + "\";";
        PreparedStatement preparedStatement = dbHandler.getDbConnection().prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();


        while(rs.next()){
            Tasks task = new Tasks(rs.getInt(1),rs.getInt(2),
                    rs.getString(3), rs.getString(4), rs.getTimestamp(5));
            i++;
            task.count = i;
            tasksArrayList.add(task);
        }

        return tasksArrayList;
    }

    public void delete(Tasks tasks) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Tasks WHERE task_id = ? ";
        PreparedStatement preparedStatement = dbHandler.getDbConnection().prepareStatement(sql);
        preparedStatement.setInt(1, tasks.getTaskid());
        preparedStatement.execute();
        preparedStatement.close();
    }
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public int getTaskid() {
        return taskid;
    }
    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }


    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

}
