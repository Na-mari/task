package todolist;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.Todo;
import dao.TodoDAO;

@WebServlet(urlPatterns = {"/todolist/DownloadCSV"})
public class DownloadCSV extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	LocalDateTime date = LocalDateTime.now();
        String today = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        
        TodoDAO dao = new TodoDAO();
        List<Todo> beans = null;

        try {
            // データ取得
            beans = dao.csv(today);
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Database error occurred while fetching data.");
            return; // 処理を中止
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("An error occurred.");
            return; // 処理を中止
        }

        // 出力するファイル名の設定
        String filename = "todo_data.csv";
        
        response.setContentType("text/csv; charset=UTF-8"); 
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        response.setStatus(HttpServletResponse.SC_OK);

        // CSVにデータを書き込む
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "Shift_JIS"));
        
        // CSVヘッダー
        writer.write("id,Task,Reg.Time,End Time,Compl.Time,Status");
        writer.newLine();

        // `csv`のデータをCSVに書き込む
        for (Todo bean : beans) {
            writer.write(bean.getId() + "," + bean.getTodo() + "," + bean.getDate() + "," + bean.getDateend()+ "," + bean.getDatecompletion() + "," + bean.getStatus());
            writer.newLine();
        }

        writer.flush();
        writer.close();
    }
}