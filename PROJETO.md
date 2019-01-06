# spring-boot-microservices 
Código desenvolvido para testes 

## Como executar?

### Construa todos os módulos:

`./mvnw clean package -DskipTests=true`

**Rodando Local:**
**Iniciar cada microservice sempre o config-server primeiro, pois é onde serão consultadas as configurações:**
`./mvnw spring-boot:run`


**Fullstack no Docker:**  

### DOCKER 1 - Iniciar módulos de infraestrutura no Docker:

`spring-boot-microservices-series> docker-compose up -d mysqldb rabbitmq setup-vault config-server service-registry hystrix-dashboard oauth2-server`

### DOCKER 2 - Iniciar módulos de negócio no Docker:

`docker-compose up -d store-service order-service payment-service store-ui`

### Stack :

* MySQL container:
     * hostname: mysqldb
     * Ports : 3306:3306 (<host_port>:<container_port>)
     * Username/Password: root/admin

* RabbitMQ:
     * hostname: rabbitmq
     * Ports: 5672:5672, 15672:15672
     * Admin UI: http://localhost:15672
     * Username/password: guest/guest

* Vault:
    * hostname: vault
    * Ports: 8200:8200
    * Root token: 934f9eae-31ff-a8ef-e1ca-4bea9e07aa09

* config-server:
    * hostname: config-server
    * Ports: 8888:8888
    * URL: http://localhost:8888/

* service-registry:
    * hostname: service-registry
    * Ports: 8761:8761
    * URL: http://localhost:8761/
    
* hystrix-dashboard:
    * hostname: hystrix-dashboard
    * Ports: 8788:8788
    * URL: http://localhost:8788/hystrix

* store-service:
    * hostname: store-service
    * Ports: 18181:8181
    * URL: http://localhost:18181
    * Swagger: http://localhost:18181/swagger-ui.html http://localhost:8282/v2/api-docs
    
* order-service   
    * hostname: order-service
    * Ports: 18282:8282
    * URL: http://localhost:18282
    * Swagger: http://localhost:18282/swagger-ui.html http://localhost:18282/v2/api-docs
    
* payment-service  
    * hostname: payment-service
    * Ports: 18383:8383
    * URL: http://localhost:18383 
    * Swagger: http://localhost:18383/swagger-ui.html http://localhost:18383/v2/api-docs
    
* store-ui    
    * hostname: store-ui
    * Ports: 18080:8080
    * URL: http://localhost:18080

    
## Nice to have features (describe or implement):
* Asynchronous processing 
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
* LOAD BALANCER(RIBBON) - Não foi implementado , porém seria importante pra balanceamento de carga em sistemas de alta disponibilidade e carga excessiva de requests.