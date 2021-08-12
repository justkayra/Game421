

## Run service

to build and run the application

```mvn tomcat7:run```

from a browser it is available at this address:  http://localhost:8080/Game421/


##Brief description of the implementation.

The application uses Tomcat7 as application container. 
To make it simple, it uses a pure servlet solution, but it relys on JSON as transport.
Also, to simplify implementation of the game object works as static instance (Singleton), it means we have only one game session for all potential users.    
TossAdviser is part of the application only for demonstration purposes, in a real use case scenario, it should run client side.  


##What might be improved to make the service ready to usage (alfa version).

- Session support.
- To offer more entertainment for the user the application could collect some stats.
- TossAdviser could be more sophisticated (as sugessted in the task). 
- The API can be easily attached to the messengers.       
- It needs more tests.
- It needs more detailed logs.
- The performance could be improved.



