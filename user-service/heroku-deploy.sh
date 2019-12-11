heroku create julia-user-service --region eu
heroku config:set DOMAIN_NAME=julia-user-service.herokuapp.com --app julia-user-service
heroku container:push web --app julia-user-service
heroku container:release web --app julia-user-service
heroku logs --tail --app julia-user-service