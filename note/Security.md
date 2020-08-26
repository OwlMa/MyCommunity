# Security
This part handles all the problems and approaches for the access permission and security.

- application security boils down to two more or less independent problems: authentication (who are you?) and authorization (what are you allowed to do?). 
- We need to have a list of permissions.

## Roles and Permission
- Admin(1): Browse the articles(Browse), reply(Reply), publish the articles(include edit and delete)-Publish
- User(2): Browse the articles and reply
- Guest: Browse the articles

https://www.cnblogs.com/fanzhidongyzby/p/11610334.html

## Notice
- The configure method in the WebSecurityConfigurerAdapter can only permit or block the header with GET,
all the DELETE and POST method will be blocked by Spring Security. We need to disable the crsf to set up the
DELETE and POST require.
- The Spring Security will set the *X-Frame-Options* DENY, it will make some part of JSON can be gotten by the front end.
So, we need to set the header frame to be its original.
- We use the BCrypt to encode the password. 
