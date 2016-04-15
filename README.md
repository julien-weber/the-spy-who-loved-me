# the-spy-who-loved-me

* Package with:
```mvn clean package```

* then to run:
```mvn spring-boot:run```


To execute the crawler to crawl profiles from LinkedIn, run the following two commands:

To crawl hired profiles:
java -cp target/the-spy-who-loved-me.jar com.criteo.hackathon.LinkedinCrawlController -i input_profile_hired -o profiles_hired -u https://www.linkedin.com/in/jonathan-wu-264316b

To crawl rejected profiles:
java -cp target/the-spy-who-loved-me.jar com.criteo.hackathon.LinkedinCrawlController -i input_profile_rejected -o profiles_rejected -u https://www.linkedin.com/in/jonathan-wu-264316b

To render text file from crawl result:
rm -rf profiles_hired_output/*
rm -rf profiles_rejected_output/*
java -cp target/the-spy-who-loved-me.jar com.criteo.hackathon.LinkedInOutput.FileReader profiles_hired profiles_hired_output
java -cp target/the-spy-who-loved-me.jar com.criteo.hackathon.LinkedInOutput.FileReader profiles_rejected profiles_rejected_output
