heroku create julia-eureka-service --region eu
heroku container:push web --app julia-eureka-service
heroku container:release web --app julia-eureka-service
heroku logs --tail --app julia-eureka-service