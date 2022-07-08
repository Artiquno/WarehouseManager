# Warehouse Manager
Just a simple test project

## How to setup

The only setup you have to do is add the proper values in
`/src/main/resources/application.yml`, or if you want to use a profile
in `/src/main/resources/application-<profile name>.yml`. 

### application.yml

`application.yml` already has some defaults, but you may need to provide
credentials to connect to your database server. These are the settings
under `spring.datasource`, namely `url`, `username`, and `password`.  
Also you may want to change `driver-class-name` if you're not using
MariaDB. I don't know what the class names are for other drivers so i
will leave that to you.

Alternatively you can put any of these properties as environment variables
but in this case you will have to flatten the paths and replace any `.`
and `-` with `_`, and for conventions' sake make them all uppercase.  
e.g `spring.datasource.driver-class-name` becomes
`SPRING_DATASOURCE_DRIVER_CLASS_NAME`. The uppercase is because the system
needs to say it out loud so Spring can hear the configuration values.

You can also define them when you run the .war file by passing
`-D<path name>=<value>`, where `<path name>` is the same as it appears in
the YAML file, with dots and stuff. Also note that there is no space
between `-D` and the path name.  
e.g `-Dspring.datasource.username=bob`

## Logging in

The first thing you will have to do is create a default user by calling
`POST /users/create-default`. This will create a user with the credentials
read from configuration (see `application.yml` for the default values).
After that, you can log in with those credentials and do whatever you want.