heroku create julia-device-service --region eu
heroku config:set DOMAIN_NAME=julia-device-service.herokuapp.com --app julia-device-service
heroku container:push web --app julia-device-service
heroku container:release web --app julia-device-service
heroku logs --tail --app julia-device-service