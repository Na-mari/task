<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="sql" uri="jakarta.tags.sql" %>
<%@include file="../header.html" %>
<style><%@include file="../styles/todo.css" %></style>

<sql:update dataSource="jdbc/todo">
    CREATE TABLE IF NOT EXISTS TODOLIST (
        ID INTEGER PRIMARY KEY AUTO_INCREMENT,
        TODO CHARACTER VARYING(200),
        DATE CHARACTER VARYING(16),
        DATECOMPLETION CHARACTER VARYING(16),
        STATUS INTEGER,
        DATEEND CHARACTER VARYING(30)
    );
</sql:update>

<%
    String showDeleted = request.getParameter("showDeleted");
    String whereCondition = "status NOT LIKE '2'";  // デフォルトは削除済みタスクを非表示

    if (showDeleted != null && showDeleted.equals("true")) {
        whereCondition = "status = '2'";  // 削除済みタスク
    }
%>

<sql:query var="list" dataSource="jdbc/todo">
    SELECT * FROM todolist
    WHERE 
        <%= whereCondition %> 
        AND (todo LIKE ? OR date LIKE ? OR dateend LIKE ? OR datecompletion LIKE ?)
    ORDER BY datecompletion, dateend;
    
    <sql:param value="%${param.keyword}%" />
    <sql:param value="%${param.keyword}%" />
    <sql:param value="%${param.keyword}%" />
    <sql:param value="%${param.keyword}%" />
</sql:query>

<h1>タスク管理</h1>
<div class="page-container">
    <div class="card-container">
        <!-- タスク追加フォーム -->
        <div class="task-card add-task-card">
            <form action="insert" method="post">
                <div class="form-field">
                    <label for="todo">Task</label>
                    <input type="text" id="todo" name="todo" required>
                </div>
                <div class="form-field">
                    <label for="dateend">End Time</label>
                    <input type="datetime-local" id="dateend" name="dateend" required>
                </div>
                <br>
                <div class="form-actions">
                    <input type="submit" value="追加" class="add-button">
                </div>
            </form>
        </div>
        <!-- タスク検索、削除済み表示 -->
        <div class="task-card add-task-card">
            <form action="main.jsp" name="a_search" method="get">
                <input type="text" name="keyword" placeholder="検索" />
                <div class="form-actions">
                	<br>
                    <input type="submit" value="検索" class="add-button">
                </div>
            </form>
            <form action="main.jsp" method="get">
            	<input type="hidden" name="showDeleted" value="true" />
	                <div class="form-actions">
	                    <input type="submit" value="削除済みタスクを表示" class="add-button">
	                </div>
			</form>
        </div>
        <div class="task-card add-task-card">
			<div class="form-actions">
				<input type="submit" value="CSVへ出力" onClick="location.href='DownloadCSV'" class="add-button">
				<div class="file-upload-container">
		            <form action="Intake" method="post" enctype="multipart/form-data">
		                <label for="csvFile">CSVファイルを選択:</label>
		                <input type="file" name="csvFile" id="csvFile" required>
		                <input type="submit" value="CSVから取込" class="add-button">
		            </form>
		        </div>
			</div>
        </div>

        <!-- タスクリスト -->
        <c:forEach var="task" items="${list.rows}">
		    <div class="task-card" data-endtime="${task.dateend}" data-completiontime="${task.datecompletion}">
		        <div class="task-field"><strong>Task:</strong> ${task.todo}</div>
		        <div class="task-field"><strong>Reg.Time:</strong> ${task.date}</div>
		        <div class="task-field"><strong>End Time:</strong> ${task.dateend}</div>
		        <div class="task-field"><strong>Compl.Time:</strong> ${task.datecompletion}</div>
		        <div class="task-actions">
		            <c:choose>
		                <c:when test="${task.status == '2'}">
		                    <!-- 削除済みタスクの場合は戻すボタン -->
		                    <a href="Restore?id=${task.id}" class="task-button delete-button">戻す</a>
		                </c:when>
		                <c:otherwise>
		                    <!-- 通常のタスクの場合は編集・完了・削除ボタン -->
		                    <a href="#" onclick="document.forms['a_edit_${task.id}'].submit(); return false;" class="task-button complete-button">編集</a>
		                    
		                    <form action="Edit.jsp" name="a_edit_${task.id}" method="get" style="display:none;">
		                        <input type="hidden" name="id" value="${task.id}">
		                        <input type="hidden" name="todo" value="${task.todo}">
		                        <input type="hidden" name="date" value="${task.date}">
		                        <input type="hidden" name="dateend" value="${task.dateend}">
		                        <input type="hidden" name="datecompletion" value="${task.datecompletion}">
		                    </form>
		
		                    <a href="Completion?id=${task.id}" class="task-button complete-button">完了</a>
		                    <a href="Delete?id=${task.id}" class="task-button delete-button">削除</a>
		                </c:otherwise>
		            </c:choose>
		        </div>
		    </div>
		</c:forEach>
    </div>
</div>

<script src="script.js"></script>

<%@include file="../footer.html" %>
