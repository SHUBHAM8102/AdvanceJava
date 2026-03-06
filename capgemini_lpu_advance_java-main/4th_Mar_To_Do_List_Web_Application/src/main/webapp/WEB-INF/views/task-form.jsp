<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add New Task - TaskTrack</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
<nav class="navbar">
    <div>
        <h1>&#10003; TaskTrack</h1>
        <p>Team Task Manager</p>
    </div>
    <a href="${pageContext.request.contextPath}/tasks" class="btn btn-secondary">&#8592; Back to Tasks</a>
</nav>

<div class="container">
    <div class="form-card">
        <h2>Add New Task</h2>
        <p class="subtitle">Fill in the details below to create a new team task.</p>

        <form:form method="post" action="${pageContext.request.contextPath}/tasks/save" modelAttribute="task" enctype="multipart/form-data">
            <div class="form-group">
                <label for="title">Task Title *</label>
                <form:input path="title" id="title" placeholder="Enter task title..."/>
                <form:errors path="title" cssClass="error"/>
            </div>

            <div class="form-group">
                <label for="description">Description *</label>
                <form:textarea path="description" id="description" placeholder="Describe the task..."/>
                <form:errors path="description" cssClass="error"/>
            </div>

            <div class="form-group">
                <label for="dueDate">Due Date *</label>
                <form:input type="date" path="dueDate" id="dueDate"/>
                <form:errors path="dueDate" cssClass="error"/>
            </div>

            <div class="form-group">
                <label for="priority">Priority *</label>
                <form:select path="priority" id="priority">
                    <form:option value="">-- Select Priority --</form:option>
                    <form:option value="LOW">LOW</form:option>
                    <form:option value="MEDIUM">MEDIUM</form:option>
                    <form:option value="HIGH">HIGH</form:option>
                </form:select>
                <form:errors path="priority" cssClass="error"/>
            </div>

            <div class="form-group">
                <label>Task Image (Optional)</label>
                <div class="file-upload-area" onclick="document.getElementById('imageFile').click()">
                    <input type="file" id="imageFile" name="imageFile" accept="image/*"
                           onchange="document.getElementById('fname').textContent=this.files.length ? this.files[0].name : ''">
                    <p>&#128247; Click to upload an image</p>
                    <p id="fname" style="font-size:0.8rem;color:#2E86DE;"></p>
                </div>
                <c:if test="${not empty imageError}">
                    <span class="error">${imageError}</span>
                </c:if>
            </div>

            <div class="form-buttons">
                <button type="submit" class="btn btn-primary">&#128190; Save Task</button>
                <a href="${pageContext.request.contextPath}/tasks" class="btn btn-secondary">&#10006; Cancel</a>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>

