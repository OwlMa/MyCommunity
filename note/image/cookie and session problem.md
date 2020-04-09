# Login

For the situation that when user is logging in but the server is somehow shutdown or reboot, the user have to login agagin.

- We can set a token in the database and set this token into cookie to the client.
- Through checking the value of token and database, the user can automatically login again.
