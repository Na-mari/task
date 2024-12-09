package todolist;

import java.util.List;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Todo;
import dao.TodoDAO;
import tool.Action;

@WebServlet("/todolist/TodoAction")
public class TodoAction extends Action {
	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response
		) throws Exception {

			HttpSession session=request.getSession();

			String task=request.getParameter("task");
			if (task==null) task="";

			TodoDAO dao=new TodoDAO();
			List<Todo> list=dao.search(task);

			session.setAttribute("list", list);

			return "/todolist/main.jsp";
		}
	}
