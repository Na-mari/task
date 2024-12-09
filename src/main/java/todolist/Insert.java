package todolist;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.Todo;
import dao.TodoDAO;

@WebServlet(urlPatterns = {"/todolist/insert"})
public class Insert extends HttpServlet {

	public void doPost (
		HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
		try {
			String todo=request.getParameter("todo");
			String dateend=request.getParameter("dateend");

			Todo p=new Todo();
			p.setTodo(todo);
			p.setDateend(dateend);
			
			TodoDAO dao=new TodoDAO();
			dao.insert(p);
			
			//フォワード先の指定
			RequestDispatcher dispatcher =  request.getRequestDispatcher("main.jsp");
	        //フォワードの実行
			dispatcher.forward(request, response);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}