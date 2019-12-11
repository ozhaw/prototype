heroku create julia-gateway --region eu
heroku container:push web --app julia-gateway
heroku container:release web --app julia-gateway
heroku logs --tail --app julia-gateway