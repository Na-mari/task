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

@WebServlet(urlPatterns = {"/todolist/Restore"})
public class Restore extends HttpServlet {

	public void doGet (
		HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
		try {
			int id=Integer.parseInt(request.getParameter("id"));

			Todo p=new Todo();
			p.setId(id);
			
			TodoDAO dao=new TodoDAO();
			dao.restore(p);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("main.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}