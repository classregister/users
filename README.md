Users service requires a running PostgreSQL cluster before it starts. You can either use the official 
[PostgreSQL](https://hub.docker.com/r/_/postgres/), or use your own. 

If you do not have your own PostgreSQL cluster start container by executing :
```bash
docker run -d --name postgresdb \
               -p 5432:5432 \
               -e "POSTGRES_USER=classregister" \
               -e "POSTGRES_PASSWORD=changeit" \
               postgres
```  

***
Prepare **application.properties** and **logback.xml** before you run classregister/users container - just copy to your directory.

Default properties for an application you have here - [application.properties](https://github.com/classregister/users/blob/master/src/main/resources/application.properties).
Same for logging configuration: [logback configuration](https://github.com/classregister/users/blob/master/src/main/resources/logback.xml).

To run Users service try this command: 
```bash
docker run -d --name classregister_users \
              --network=host
              -p 6002:6002 \
              -v /home/user/conf:/config:ro \
              -v /log \
              classregister/users
```  
where */home/user/conf* is a directory you copied application.properties and logback.xml.

Property **spring.jpa.hibernate.ddl-auto=create** in application.properties should be changed to *none* after first run or should be deleted because *none* is the default value.
This property with value *create* is for creating table schema so you do not need to do it. 

To run in your production do not use command network with 'host' value. Create your own network with *docker network create*, inspect ip for postgres database with command
*docker inspect --format '{{ .NetworkSettings.IPAddress }}' postgresdb* 
and paste it to *spring.datasource.url* in application.properties. 

Also remember to run container with flags *--user=your_user*, *--cap-drop=ALL*, *--read-only*. 
For more information - check [docker_run_reference](https://docs.docker.com/engine/reference/run/)
