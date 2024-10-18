<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Register page</title>
</head>
<body>
<h1>Register page</h1>
<form action="/register" method="post">
    <label for="email">Email:</label>
    <input type="text" id="email" name="email" required><br/>

    <label for="firstname">First Name:</label>
    <input type="text" id="firstname" name="firstname" required><br/>

    <label for="lastname">Last Name:</label>
    <input type="text" id="lastname" name="lastname" required><br/>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required>

    <input type="submit" value="Register">
</form>
</body>
</html>

