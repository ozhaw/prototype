heroku create julia-gateway --region eu
heroku config:set DOMAIN_NAME=julia-gateway.herokuapp.com --app julia-gateway
heroku container:push web --app julia-gateway
heroku container:release web --app julia-gateway
heroku logs --tail --app julia-gateway