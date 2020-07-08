# Exception Handle

- MyExceptionHandler is used to handle the exceptions that i specify, like article not found.
- The exceptions that i specify are declared in the MyException class and MyException class extends the RuntimeException. because
i want all the exception can be handled in the MyExceptionHandler and default ExceptionHandler but not in the try-catch block.

## MyException
- ErrorCode interface has only one method which returns the message of this type of error.
- I use a enum which implements the ErrorCode to define every type of error code. Because some types of exception belong to
one Service and other may belong to other Service level. But i manipulate all these error code by using the interface ErrorCode.
If i want to add more error codes, i can directly add inside corresponding service's level ErrorCode.

## BaseErrorController
- This class implements the ErrorController to pick up all the errors that i don't specify.

