rm -rf profiles_hired_output/*
rm -rf profiles_rejected_output/*
java -cp target/the-spy-who-loved-me.jar com.criteo.hackathon.LinkedInOutput.FileReader profiles_hired profiles_hired_output
java -cp target/the-spy-who-loved-me.jar com.criteo.hackathon.LinkedInOutput.FileReader profiles_rejected profiles_rejected_output
