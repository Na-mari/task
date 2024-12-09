<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>
<style><%@include file="../styles/todo.css" %></style>

<div class="page-container">
    <div class="task-card">
        <h1>タスク編集</h1>
        <form action="Edit"name="a_edit_${task.id}" method="POST">
            <!-- タスクID (非表示フィールド) -->
            <input type="hidden" name="id" value="${param.id}">
            
            <div class="form-field">
			    <label for="todo"><strong>Task</strong></label>
			    <input type="text" id="todo" name="todo" value="${param.todo}" required>
			</div>
			
			<div class="form-field">
			    <label for="date"><strong>Reg.Time</strong></label>
			    <input type="text" id="date" name="date" value="${param.date}" required>
			</div>
			
			<div class="form-field">
			    <label for="dateend"><strong>End Time</strong></label>
			    <input type="text" id="dateend" name="dateend" value="${param.dateend}" required>
			</div>
			
			<div class="form-field">
			    <label for="datecompletion"><strong>Compl.Time</strong></label>
			    <input type="text" id="datecompletion" name="datecompletion" value="${param.datecompletion}" required>
			</div>

			<br>
            <!-- 更新ボタン -->
            <div class="form-actions">
            	<a href="#" onclick="document.forms['a_edit_${task.id}'].submit(); return false;" class="task-button complete-button">更新</a>
<!--                <button type="submit" class="task-button save-button">更新</button>-->
                <a href="main.jsp" class="task-button cancel-button">キャンセル</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
