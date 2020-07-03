# Session
- Session is stored in the server side, and the user information is usually stored inside the session.
- The http is a state-less protocol, which means that server can not know which user is requesting. Therefore,
the session is introduced to maintain the current conversation and let the server know which user is requesting.
- Server can identify the session by session id. So if a user or client query the server for the first time, usually 
the server will add a session id to the response to the client.
# Cookie
- Cookie is stored physically in the client side and is used to implement the session.
- Just like introduction above, the server will add a session id to the response to the client by adding this info to the
cookie of the client.
- The cookie holds the session id. And next time when client send a request along with the cookie to the server, the server
will know exactly which user is manipulating. If the cookie is disabled by the client, there are also other ways like refactor
the URL to let the server keep the conversation. 

# Login

For the situation that when user is logging in but the server is somehow shutdown or reboot, the user have to login again.

- We can set a token in the database and set this token into cookie of the client.
- Through checking the value of token and database, the user can automatically login again.