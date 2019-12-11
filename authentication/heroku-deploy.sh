heroku create julia-auth --region eu
heroku config:set DOMAIN_NAME=julia-auth.herokuapp.com --app julia-auth
heroku container:push web --app julia-auth
heroku container:release web --app julia-auth
heroku logs --tail --app julia-auth