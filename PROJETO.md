# spring-boot-microservices 
Código desenvolvido para testes 

## Como executar?

### Primeiro Construa todos os módulos :
`cd backend-challenge`
`./mvnw clean package -DskipTests=true`

**Rodando microserviços de negócio Local:**
###Para Iniciar cada microservice sempre iniciar os módulos de infra, pois é onde serão consultadas as configurações e registrado os serviços:**
`cd store-service`
`./mvnw spring-boot:run`


**Fullstack no Docker:**  

### DOCKER 1 - Iniciar módulos de infraestrutura no Docker:

`<BACKEND-CHALLENGE-DIR> docker-compose up -d mysqldb rabbitmq setup-vault config-server service-registry hystrix-dashboard oauth2-server`

Eles podem ser iniciados independentemente, também pode ser iniciados sem o docker apenas pelo spring-boot:run

### DOCKER 2 - Iniciar módulos de negócio no Docker:

`<BACKEND-CHALLENGE-DIR> docker-compose up -d store-service order-service payment-service store-ui`
* Os serviços podem ser iniciados separadamente

#### Para Recriar os containers após uma atualização por exemplo

`$docker-compose up -d --no-deps --build <service_name>`

## Stack :

### MySQL container:
     * hostname: mysqldb
     * Ports : 3306:3306 (<host_port>:<container_port>)
     * Username/Password: root/admin

### RabbitMQ:
     * hostname: rabbitmq
     * Ports: 5672:5672, 15672:15672
     * Admin UI: http://localhost:15672
     * Username/password: guest/guest

### Vault:
    * hostname: vault
    * Ports: 8200:8200
    * Root token: 934f9eae-31ff-a8ef-e1ca-4bea9e07aa09

### config-server:
    * hostname: config-server
    * Ports: 8888:8888
    * URL: http://localhost:8888/

### service-registry:
    * hostname: service-registry
    * Ports: 8761:8761
    * URL: http://localhost:8761/
    
### hystrix-dashboard:
    * hostname: hystrix-dashboard
    * Ports: 8788:8788
    * URL: http://localhost:8788/hystrix

### store-service:
    * hostname: store-service
    * Ports: 18181:8181
    * URL: http://localhost:18181
    * Swagger: http://localhost:18181/swagger-ui.html http://localhost:8282/v2/api-docs
    
### order-service   
    * hostname: order-service
    * Ports: 18282:8282
    * URL: http://localhost:18282
    * Swagger: http://localhost:18282/swagger-ui.html http://localhost:18282/v2/api-docs
    
### payment-service  
    * hostname: payment-service
    * Ports: 18383:8383
    * URL: http://localhost:18383 
    * Swagger: http://localhost:18383/swagger-ui.html http://localhost:18383/v2/api-docs
    
### store-ui    
    * hostname: store-ui
    * Ports: 18080:8080
    * URL: http://localhost:18080

    
## Nice to have features (describe or implement):
* Asynchronous processing - rabbitmq - a comunicação utilizado filas assíncronas
* Fault tolerance - Circuit Breaker (Hystrix/Turbine)- Intelligent Routing (Zuul)
* Scalabiliry - Service Discovery (Eureka) 
* Database (mysql)
* Docker 
	* (DockerFile, docker-compose)
* AWS
	* (Service registy-Eureka, srping cloud) Toda infraestrutura pode ser implantada na AWS 
* Security (oauth2,vault)
	* Gerenciamento de tokens e vault pra auditoria e segurança do acesso as credenciais
* Swagger 
* Clean Code (lombok, layers) código padronizado
* (NOT IMPLEMENTED) LOAD BALANCER CLIENT (RIBBON) - Não foi implementado , porém seria importante pra balanceamento de carga em sistemas de alta disponibilidade e carga excessiva de requests.