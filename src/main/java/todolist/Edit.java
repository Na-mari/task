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

@WebServlet(urlPatterns = {"/todolist/Edit"})
public class Edit extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int id=Integer.parseInt(request.getParameter("id"));
			String todo=request.getParameter("todo");
			String dateend=request.getParameter("dateend");
			String date=request.getParameter("date");
			String datecompletion=request.getParameter("datecompletion");
			
			Todo p=new Todo();
			p.setId(id);
			p.setTodo(todo);
			p.setDateend(dateend);
			p.setDate(date);
			p.setDatecompletion(datecompletion);

			TodoDAO dao=new TodoDAO();
			dao.edit(p);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("main.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}