rm -rf developer-weapons-repository
mkdir developer-weapons-repository
cd developer-weapons-repository
git init
git remote add origin https://github.com/developer-weapons/repository.git
git pull origin master
git branch --set-upstream-to=origin/master master
cd -
mvn deploy -DaltDeploymentRepository=developer-weapons-repository::default::file:developer-weapons-repository
cd developer-weapons-repository
git add .
git commit -m "deploy"
git push
cd -
rm -rf developer-weapons-repository