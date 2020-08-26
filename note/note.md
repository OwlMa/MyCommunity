- By using the @RequestBody annotation, we can serialize the Object to Json doc.
- By using the MultipartHttpServletRequest, we can use additional methods for dealing with multipart content within a servlet request, allowing to access uploaded files. Implementations also need to override the standard ServletRequest methods for parameter access, making multipart parameters available.

- href="https://github.com/login/oauth/authorize?client_id=1925142f4c54181de70e&redirect_uri=http://192.168.4.46:8080/callback&scope=user&state=ssss"