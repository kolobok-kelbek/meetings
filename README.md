# Start project

### dependence

1. docker
2. docker-compose

### run
1. add `127.0.0.1 meetings.local` to `/etc/hosts`
2. `./gradlew dockerBuild`
3. `./gradlew dcUp`

The authorization token in a cookie.
You can get the authorization token with POST request to [url](http://meetings.local/api/auth/signin).
Body for request:
```json
{
	"username": "admin",
	"password": "testpassword"
}
```