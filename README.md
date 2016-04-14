# the-spy-who-loved-me

* Package with:
```mvn clean package```

* then to run:
```mvn spring-boot:run```


To execute the crawler to crawl profiles from LinkedIn, run the following two commands:

To crawl hired profiles:
java -cp target/the-spy-who-loved-me.jar com.criteo.hackathon.LinkedinCrawlController input_profile_hired profiles_hired

To crawl rejected profiles:
java -cp target/the-spy-who-loved-me.jar com.criteo.hackathon.LinkedinCrawlController input_profile_rejected profiles_rejected
