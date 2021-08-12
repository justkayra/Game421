

## Run service

to build and run the application

```mvn tomcat7:run```

from a browser it is available at this address:  http://localhost:8080/Game421/


##Brief description of the implementation.

The application uses Tomcat7 as application container. 
To make simple it uses pure servlet solution, but it still uses JSON as transport.

TossAdviser is part of application only for demonstration, in real life it should part of
client side.  


##What might be improved to make the service ready to usage (alfa version).


- To get more entertaiment tha pplication couls collect some stats;
- TossAdviser could be more sophisticated (as sugessted in the task); 
- The API can be easily attached to messengers;       
- It needs tests
- It needs more logs
- The performance could be improved


- Session support;
Also, to simplify implmentation the game works implenetd as static object (Singleton), it means we have only one instance for all potential users;    