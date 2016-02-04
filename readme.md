#### Application startup:

1. Create and start container with postgresql database: 
   * sudo docker create --name mypostgresdb-data -v /home/christian/CData/workspaces/docker/volumes/postgres-data:/var/lib/postgresql/data postgres:9.4.5 echo "data-only container for postgres"
   * sudo docker run --name mypostgresdb -p 5432:5432 -e POSTGRES_PASSWORD=******** --volumes-from mypostgresdb-data -d postgres:9.4.5
   * eventually connect to database: psql -h 52.59.250.63 -p 5432 -U postgres trainingdb;
   
2. Start container with REST API: 
   * sudo docker run -p 8181:8181 -d chregli/logbook
   
3. Connect to REST backend: 
   * http://54.93.84.56:8181/swagger-ui.html
   * http://54.93.84.56:8181/workouts
   
4. Start frontend application: 
   * ...

   
## ToDo

1. LOGGER...

