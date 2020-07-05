# around-delivery-backend
RESTFul APIs to handle requests from Node.js and interact with AWS RDS

# How to contribute to this repository
- step1: Fork this repository
- step2: git clone to local machine from your own repository
- step3: update code in your local machine
- step4: merge with this master branch
    - make sure already `git add .`,`git commit -m "your commit content"` and your local master branch is clean
    - `git remote add schen246 https://github.com/schen246/around-delivery-backend.git`, `git pull schen246 master` from this master branch to keep update with this repository, otherwise there might be some conflictions
    - `git status` to check whether local master branch is clean
    - `git push -u origin master` to push your local master branch to your github master branch
- step5: in your own github repository, click Pull request to send merge request to this repository
- step6: the owner of this repository will receive your request and review the code to merge them

# How to use the code
- step1: configure tomcat 
- step2: start tomcat 
- step3: test with postman

Postman example:
- method: POST
- endpoint: http://localhost:8080/delivery/recommendation
- body:
```json
{
    "oneAddr": "Google",
    "twoAddr": "Facebook",
    "weight": 2.0
}
```
- Result:
```json
{
    "Robot Estimated Delivery Time (cheapest)": 17.6219627308816,
    "Robot Price (cheapest)": 73.4778509235264,
    "Drone Estimated Delivery Time (fastest)": 3.50898254061472,
    "Drone Price (fastest)": 181.43912703073602
}
```
