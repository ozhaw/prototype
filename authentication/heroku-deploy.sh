heroku create julia-auth --region eu
heroku container:push web --app julia-auth
heroku container:release web --app julia-auth
heroku logs --tail --app julia-auth