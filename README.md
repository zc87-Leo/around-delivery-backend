## How to use backend code

#### Follow jupiter project 
- select new workspace
- create tomcat server
- tomcat configuration
- new maven project
- add and remove: configured with `delivery`
> Group Id: flagcampproject
> Artifact Id: delivery
> Version: 0.0.0.1-SNAPSHOT

#### dependencies required in this project
```
<properties>
   <javaVersion>1.8</javaVersion>
   <maven.compiler.source>1.8</maven.compiler.source>
   <maven.compiler.target>1.8</maven.compiler.target>
</properties>

<dependencies>
   <dependency>
       <groupId>junit</groupId>
       <artifactId>junit</artifactId>
       <version>4.11</version>
       <scope>test</scope>
   </dependency>
   <dependency>
       <groupId>org.apache.tomcat</groupId>
       <artifactId>tomcat-catalina</artifactId>
       <version>9.0.30</version>
   </dependency>
   <dependency>
       <groupId>org.json</groupId>
       <artifactId>json</artifactId>
       <version>20190722</version>
   </dependency>
   <dependency>
       <groupId>org.apache.httpcomponents</groupId>
       <artifactId>httpclient</artifactId>
       <version>4.5.10</version>
   </dependency>
   <dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
       <version>8.0.18</version>
   </dependency>
   <dependency>
       <groupId>com.google.maps</groupId>
       <artifactId>google-maps-services</artifactId>
       <version>0.14.0</version>
   </dependency>
   <dependency>
       <groupId>org.slf4j</groupId>
       <artifactId>slf4j-simple</artifactId>
       <version>1.7.25</version>
   </dependency>
   <dependency>
       <groupId>com.smartystreets.api</groupId>
	   <artifactId>smartystreets-java-sdk</artifactId>
	   <version>3.5.2</version>
   </dependency>
</dependencies>
```

#### Servlet
- rpc
	- /register
	- /login
	- /autocomplete
	- /validaddr
	- /recommendation
	- /neworder
	- /tracking
	- /history
	- /detail

# dronbot-backend
RESTFul APIs to handle requests from Node.js and interact with AWS RDS

# How to contribute to this repository

## fork a repo
- step1: Fork this [repository](https://github.com/schen246/dronbot-backend.git)
- step2: git clone to local machine from your own repository
    - git clone https://github.com/YOUR-USERNAME/around-delivery-backend (replace YOUR-USERNAME with your repository)
- step3: configure git to sync your fork with the original dronbot-backend repository
    - `cd your_listed_directory` which is the location of the fork you cloned in step2 (around-delivery-backend)
    - `git remote -v`
    ```
    > origin  https://github.com/YOUR_USERNAME/YOUR_FORK.git (fetch)
    > origin  https://github.com/YOUR_USERNAME/YOUR_FORK.git (push)
    ```
    - `git remote add upstream https://github.com/schen246/around-delivery-backend.git`
    - To verify the new upstream repository you've specified for your fork, type git remote -v again. You should see the URL for your fork as origin, and the URL for the original repository as upstream.
    ```
    $ git remote -v
    > origin    https://github.com/YOUR_USERNAME/YOUR_FORK.git (fetch)
    > origin    https://github.com/YOUR_USERNAME/YOUR_FORK.git (push)
    > upstream  https://github.com/ORIGINAL_OWNER/ORIGINAL_REPOSITORY.git (fetch)
    > upstream  https://github.com/ORIGINAL_OWNER/ORIGINAL_REPOSITORY.git (push)
    ```
Now, you can keep your fork synced with the upstream repository with a few Git commands.
## syncing a fork
- step1: Fetch the branches and their respective commits from the upstream repository. Commits to master will be stored in a local branch, upstream/master.
    ```
    $ git fetch upstream
    > remote: Counting objects: 75, done.
    > remote: Compressing objects: 100% (53/53), done.
    > remote: Total 62 (delta 27), reused 44 (delta 9)
    > Unpacking objects: 100% (62/62), done.
    > From https://github.com/ORIGINAL_OWNER/ORIGINAL_REPOSITORY
    >  * [new branch]      master     -> upstream/master
    ```
- step2: Check out your fork's local master branch.
    ```
    $ git checkout master
    > Switched to branch 'master'
    ```
- step3: Merge the changes from upstream/master into your local master branch. This brings your fork's master branch into sync with the upstream repository, without losing your local changes.
    ```
    $ git merge upstream/master
    > Updating a422352..5fdff0f
    > Fast-forward
    >  README                    |    9 -------
    >  README.md                 |    7 ++++++
    >  2 files changed, 7 insertions(+), 9 deletions(-)
    >  delete mode 100644 README
    >  create mode 100644 README.md
    ```
    If your local branch didn't have any unique commits, Git will instead perform a "fast-forward":
    ```
    $ git merge upstream/master
    > Updating 34e91da..16c56ad
    > Fast-forward
    >  README.md                 |    5 +++--
    >  1 file changed, 3 insertions(+), 2 deletions(-)
    ```
- step4: `git push -u origin master`
## make your changes
- implementation

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




















