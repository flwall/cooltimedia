# Cooltimedia
Cooltimedia should become a platform for managing personal media files and access them from everywhere. 
Nevertheless, this repository is developed only by myself and motivated by personal interest. So don't expect too much contributions on this project.

## Technology
This project is a PWA-ready Application built with Vaadin and Spring Boot.  
It's hosted under https://cooltimedia.herokuapp.com  
This is still under development

###Authorization
We use Auth0 as Auth Provider

### Media Cloud Storage  
We use Cloudinary to store Media of the Users.

# Installation
There is already Docker Support with a corresponding Docker-Compose config-file, but there are some known bugs in this Configuration. 
As soon I can release v1 of this repo, I'll fix those Issues.

## Configuration
You'll need a MySQL DB to store the data to. You can specify the connection string in the application-dev.properties file.
Under application.yml you can specify the connection data for Auth0 and Cloudinary.  