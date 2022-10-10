<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ru">
<head>
    <meta http-equiv="Content-Type"  content="text/html; charset=UTF-8">
    <title>Add new meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>
<form action="meals" accept-charset="UTF-8"  method="post" name="myform">
    DateTime :<input name="mealDateTime" size="14" type="datetime-local"
                     value=${meal.dateTime}> <br/>
    Description:<input  name="mealDescription"  type="text" size="14" value=${meal.description}><br/>
    Calories:<input name="mealCalories"  type="text" size="10" value=${meal.calories}><br/>
    <input  name="mealId"  type="hidden" size="14"  readonly="readonly" value="${meal.id}"/> <br/>
    <input name="submit" type="submit" value="Submit"/>
    <input name="reset" type="reset" value="Reset"/>
</form>

</body>
</html>