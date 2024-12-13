package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        
        PreparedStatement getMaxIdStmt = con.prepareStatement("SELECT MAX(id) FROM todolist");
        ResultSet rs = getMaxIdStmt.executeQuery();
        int nextId = 1; // 初期値（最初のレコード用）
        if (rs.next()) {
            nextId = rs.getInt(1) + 1; // 最大idに1を足す
        }
        rs.close();
        getMaxIdStmt.close();

        PreparedStatement st = con.prepareStatement(
                "INSERT INTO todolist(id, todo, date, dateend, status, datecompletion) VALUES(?, ?, ?, ?, 1, '')");
            st.setInt(1, nextId);
            st.setString(2, todo.getTodo());
            st.setString(3, formatted);
            st.setString(4, todo.getDateend());
            int line = st.executeUpdate();

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
	
	public List<Todo> csv(String today) throws Exception {
	
		Connection con=getConnection();

		PreparedStatement st;
		st=con.prepareStatement("SELECT * FROM todolist;");
					
		ResultSet rs=st.executeQuery();
		System.out.println(st + " record(s) dashboard.");
		
		List<Todo> beans = new ArrayList<>();
		while (rs.next()) {
		    Todo bean = new Todo();
		    bean.setId(rs.getInt("id"));
		    bean.setTodo(rs.getString("todo"));
		    bean.setDate(rs.getString("date"));
		    bean.setDateend(rs.getString("dateend"));
		    bean.setDatecompletion(rs.getString("datecompletion"));
		    bean.setStatus(rs.getInt("status"));
		    beans.add(bean);
		}
		
		rs.close();
		st.close();
		con.close();
		
		return beans;
	}
	
	public int intake(List<String[]> csvData) throws Exception {
        Connection con = getConnection(); // DB接続メソッド（適切に実装してください）
        PreparedStatement st = null;
        int insertedRows = 0;

        try {
            // テーブルのデータを削除
            st = con.prepareStatement("DELETE FROM todolist;");
            st.executeUpdate();

            // INSERT文の準備
            st = con.prepareStatement("INSERT INTO todolist (id, todo, date, dateend, datecompletion, status) VALUES (?, ?, ?, ?, ?, ?)");

            // 配列からデータを取得してSQLに挿入
            for (String[] values : csvData) {
                st.setInt(1, Integer.parseInt(values[0]));  // ID
                st.setString(2, values[1]);                  // todo (タスク名)
                st.setString(3, values[2]);                  // date (開始日)
                st.setString(4, values[3]);                  // dateend (終了日)
                st.setString(5, values[4]);                  // datecompletion (完了日)
                st.setInt(6, Integer.parseInt(values[5]));   // status (ステータス)

                insertedRows += st.executeUpdate();  // データを挿入し、挿入した行数をカウント
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("データベースへのインサート処理に失敗しました。");
        } finally {
            try {
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
        }

        return insertedRows;  // 挿入した行数を返す
    }
}

