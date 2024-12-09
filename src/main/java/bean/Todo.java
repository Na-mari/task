package bean;

public class Todo implements java.io.Serializable {

	private int id;
	private String todo;
	private String date;
	private String dateend;
	private String datecompletion;
	private int status;

	public int getId() {
		return id;
	}
	public String getTodo() {
		return todo;
	}
	public String getDate() {
		return date;
	}
	public int getStatus() {
		return status;
	}
	public String getDateend() {
		return dateend;
	}
	public String getDatecompletion() {
		return datecompletion;
	}

	public void setId(int id) {
		this.id=id;
	}
	public void setTodo(String todo) {
		this.todo=todo;
	}
	public void setDate(String date) {
		this.date=date;
	}
	public void setDateend(String dateend) {
		this.dateend=dateend;
	}
	public void setDatecompletion(String datecompletion) {
		this.datecompletion=datecompletion;
	}
	public void setStatus(int status) {
		this.status=status;
	}
}
