# Gateway API sample using Spring Boot

## What is it?
The repository contains a bare-bones implementation of a gateway API, consisting of two separate Spring Boot applications:
a SPA client based on [Vue.js](https://vuejs.org/) and an authentication/authorization server.

This sample project is the result of two weeks of struggle against Spring Security and its mechanisms.
Since the setup of a complete Oauth2 system in Spring Boot can be quite challenging and confusing at first, I hope this resource can save
some time to someone else facing the same problems for the first time.

A detailed description of the gateway API architecture can be found on this [spring.io guide](https://spring.io/guides/tutorials/spring-security-and-angular-js/).

## Quickstart
**Note:** [Node](https://nodejs.org) is required in order to build the client UI, but the same principles
apply for any other type of web application, provided that the endpoints are configured in the same way.

* Clone or download the sample
* Open the repository folder as a *Maven* project.
* Build the UI client:
```
> cd client
> npm install
> npm run build
```

* Start authentication/authorization server application and client application as usual Spring Boot apps.
* Navigate to [localhost:8080](http://localhost:8080) in order to start the SSO procedure.
It is important, at least for local testing, to keep client and server ond different ports and context paths,
in order to avoid problems with cookie handling.

## Authentication - authorization flow
1) The browser requests the index page from the client.
2) The user is not authenticated yet: the SSO mechanism triggers the sign on procedure.
3) The browser/user is redirected to [localhost:9090/auth](http://localhost:9090/auth)

4) On the auth-server, the user can enter with basic credentials (check com.example.authserver.DataLoader),
or by authenticating with google (SSO-ception).
5) Once the **authentication** is successful, **authorisation** is granted to the UI client to access data in behalf of the user.
6) After this request "dance", (using the *oauth/authorize* and *oauth/token* endpoints), an access token is generated
and given back to the UI client, that populates the Securitycontext with the info taken from the userInfoUri, stated in the 
*application.yml* configuration.
7) The client can finally show the index page. On creation, the Vue app requests data from the "api/resource" endpoint. 
The request reaches the server on *localhost:8080*, activating the Zuul proxy that forwards it to the actual resource server,
located at *localhost:9090*.

