# DropsTab portfolio monitor telegram bot
Bot to monitor portfolio changes and send notifications to telegram on significant change.
Bot intended for personal use and can't be used for multiple users\profies. 

## Configuration
Application relies on environment variables to configure itself.
Variable must be provided:
- `BOT_TOKEN` - telegram bot token
- `EMAIL` - login email address for https://dropstab.com/
- `PASSWORD` - login password
- `TARGET_USER` - telegram user id to send notifications
