package todolist;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.Todo;
import dao.TodoDAO;

@WebServlet(urlPatterns = {"/todolist/Completion"})
public class Completion extends HttpServlet {

	public void doGet (
		HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
		try {
			int id=Integer.parseInt(request.getParameter("id"));

			Todo p=new Todo();
			p.setId(id);
			
			TodoDAO dao=new TodoDAO();
			dao.update(p);
			
			String task=request.getParameter("task");
			if (task==null) task="";

			List<Todo> list=dao.search(task);

			request.setAttribute(task, list);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("main.jsp");
			dispatcher.forward(request, response);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}