## Goal
A porject for Amazon/Ebay/Walmart seller to maintain inventories and for suppliers to supply goods, request payment and receive offer from sellers

## Terms (nick names):
Doctor = Seller, Nurse = suppliers, seller could be both Doctor and Nurse.

## Tech Stacks
- Frontend: React 16, Redux, React-Redux, Redux-Saga, React-Admin, Axios, Material-UI
- Backend: spring boot 2.2.6, Liquibase, PostgreSQL, Oauth2.0, Spring security, Kafka 
- Devops: Ansible, Guthub Actions, Heroku postgre database for free dev
- Cloud: Digital Ocean (2 gb memory and 25G data, limited buget for development stage)

### Road map
V0.1(completed):  basic skeltons of front end and backend
- Frontend features:
  - Login in page, register new user, configure user.
  - creating new group for seller, applying to a new seller, applying joining a seller group
- Backend features: 
   - Spring Auth2.0 for authorize server and resource server
   - implemented user, group, seller, usergroup tables with Liquibase 
   - kafka comsumer in Author service to receive password and user and kafka producer in user service to publish user and password
 
V0.2(in the road): depends on free pensonal time:
- Bug fixes and code refactoring fron V0.1 
- Frontend: nav bar for groups, offers, inventories, payments.
- Backend: offer-service ,inventory-service, payment-service
- Devops: may move cloud from digital ocean to AWS/Azure (depends on buget)

# ShoppingInventory


app: http://161.35.52.95:3000/  <br/>
auth-service: http://161.35.52.95:8081<br/>
user-service: http://161.35.52.95:8083<br/>


## Local test
 app: npm start <br/>
 auth-service: http://localhost:8081<br/>
 user-service: http://localhost:8083<br/>
 offser-service: http://localhost:8082<br/>
 
 Run postgre, zoomkeeper, kafka service locally, please check it in their offical documents
 
 postman collecton and env, please contact me.
 
