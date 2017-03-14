#Hyperwallet/VISA Hackathon Submission, March 14 2017

This requires Java 8 to run. Once you have the JDK, you can run the project with:

```console
./mvnw spring-boot:run
```

Once it's up, hit `http:localhost:8080` and you should see a registration page. After registering, you should get a text message with a URL that you can donate to. This by default links to an ngruok url which you can change in the `RegisterController`.
