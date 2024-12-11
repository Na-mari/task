package todolist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import dao.TodoDAO;

@WebServlet(urlPatterns = {"/todolist/Intake"})
@MultipartConfig // このアノテーションでファイルアップロードを有効化
public class Intake extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ファイルの取得
        Part filePart = request.getPart("csvFile"); // HTMLフォームの`name="csvFile"`に対応
        if (filePart != null && filePart.getSize() > 0) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream(), "Shift_JIS"));) {
                // CSVデータを配列に格納
                List<String[]> csvData = new ArrayList<>();
                String line;
                reader.readLine(); // ヘッダー行をスキップ
                while ((line = reader.readLine()) != null) {
                    csvData.add(line.split(",")); // カンマ区切りで配列に格納
                }

                // データベースにインサート
                TodoDAO dao = new TodoDAO();
                int insertedRows = dao.intake(csvData);

                // 結果をリクエスト属性に設定
                request.setAttribute("message", insertedRows + " 行のデータが挿入されました。");
                RequestDispatcher dispatcher = request.getRequestDispatcher("main.jsp");
                dispatcher.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().println("CSVファイルの取り込みに失敗しました。");
            }
        } else {
            response.getWriter().println("CSVファイルが選択されていません。");
        }
    }
}
