# around-delivery-backend
RESTFul APIs to handle requests from Node.js and interact with AWS RDS

# How to use the code
- step1: configure tomcat 
- step2: start tomcat 
- step3: test with postman
Example:
```json
{
    "oneAddr": "Google",
    "twoAddr": "Facebook",
    "weight": 2.0
}
```
Result:
```json
{
    "Robot Estimated Delivery Time (cheapest)": 17.6219627308816,
    "Robot Price (cheapest)": 73.4778509235264,
    "Drone Estimated Delivery Time (fastest)": 3.50898254061472,
    "Drone Price (fastest)": 181.43912703073602
}
```
