# DropsTab portfolio monitor telegram bot
Bot to monitor portfolio changes and send notifications to telegram on significant change.
Bot intended for personal use and can't be used for multiple users\profiles. 

## Configuration
Application relies on environment variables to configure itself.
Variable must be provided:
- `BOT_TOKEN` - telegram bot token
- `EMAIL` - login email address for https://dropstab.com/
- `PASSWORD` - login password
- `TARGET_USER` - telegram user id to send notifications

## Deploy
You can run application locally:
```
# export BOT_TOKEN=<your_bot_token>
# export EMAIL=<your_email>
# export PASSWORD=<your_password>
# export TARGET_USER=<your_telegram_user_id>
./gradlew run 
```
or on remote server:
```
./gradlew shadowJar && scp build/libs/dropsTabTelegramMonitor-1.0-all.jar <your_server_ip>:~/monitor.jar

# ssh <your_server_ip>
# export env variables 
java -cp "*:." monitor.dropstab.Main

```

