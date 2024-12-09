package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.Todo;

public class TodoDAO extends DAO {

	public List<Todo> search(String task) throws Exception {
		List<Todo> list=new ArrayList<>();

		Connection con=getConnection();

		PreparedStatement st=con.prepareStatement(
			"select * from todolist where status not like '2'");
		ResultSet rs=st.executeQuery();
		
		
		while (rs.next()) {
			Todo p=new Todo();
			p.setId(rs.getInt("id"));
			p.setTodo(rs.getString("todo"));
			p.setDate(rs.getString("date"));
			p.setDateend(rs.getString("dateend"));
			p.setDatecompletion(rs.getString("datecompletion"));
			p.setStatus(rs.getInt("status"));
			list.add(p);
		}

		st.close();
		con.close();

		return list;
	}
	

	public int insert(Todo todo) throws Exception {
		Connection con=getConnection();
		
		Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String formatted = dateFormat.format(now);

		PreparedStatement st=con.prepareStatement(
			"insert into todolist(todo, date, dateend) values(?, ?, ?)");
		st.setString(1, todo.getTodo());
		st.setString(2, formatted);
		st.setString(3, todo.getDateend());
		int line=st.executeUpdate();

		st.close();
		con.close();
		return line;
	}
	
	public int update(Todo todo) throws Exception {
		Connection con=getConnection();
		
		Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String formatted = dateFormat.format(now);

		PreparedStatement st=con.prepareStatement(
			"update todolist set status = 1,datecompletion = ? where id = ?");
		st.setString(1, formatted);
		st.setInt(2, todo.getId());
		int line=st.executeUpdate();

		st.close();
		con.close();
		return line;
	}
	
	public int delete(Todo todo) throws Exception {
		Connection con=getConnection();
		
		PreparedStatement st=con.prepareStatement(
			"update todolist set status = 2 where id = ?");
		st.setInt(1, todo.getId());
		int line=st.executeUpdate();

		st.close();
		con.close();
		return line;
	}

	public int edit(Todo todo) throws Exception {
		Connection con=getConnection();
	
		PreparedStatement st=con.prepareStatement(
			"update todolist set todo = ?,dateend = ?,date = ?,datecompletion = ? where id = ?");
		st.setString(1, todo.getTodo());
		st.setString(2, todo.getDateend());
		st.setString(3, todo.getDate());
		st.setString(4, todo.getDatecompletion());
		st.setInt(5, todo.getId());
		int line=st.executeUpdate();
	
		st.close();
		con.close();
		return line;
	}
	
	public int restore(Todo todo) throws Exception {
		Connection con=getConnection();
		
		PreparedStatement st=con.prepareStatement(
			"update todolist set status = 1 where id = ?");
		st.setInt(1, todo.getId());
		int line=st.executeUpdate();

		st.close();
		con.close();
		return line;
	}
}

