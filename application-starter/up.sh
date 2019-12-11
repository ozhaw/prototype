heroku ps:scale web=1 --app julia-eureka-service
heroku ps:scale web=1 --app julia-user-service
heroku ps:scale web=1 --app julia-device-service
heroku ps:scale web=1 --app julia-reports-service
heroku ps:scale web=1 --app julia-auth
heroku ps:scale web=1 --app julia-gateway