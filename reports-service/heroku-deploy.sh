heroku create julia-reports-service --region eu
heroku config:set DOMAIN_NAME=julia-reports-service.herokuapp.com --app julia-reports-service
heroku container:push web --app julia-reports-service
heroku container:release web --app julia-reports-service
heroku logs --tail --app julia-reports-service