call:docker_push authentication
call:docker_push eureka-service
call:docker_push user-service
call:docker_push reports-service
call:docker_push device-service
call:docker_push gateway
call:docker_push hystrix-dashboard
call:docker_push jms

:docker_push
    cd ../%~1
    docker build -t ozhaw/%~1:latest .
    docker push ozhaw/%~1:latest
goto:eof