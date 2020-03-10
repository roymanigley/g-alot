
g-alot:link{jpa-Entity.java}

g-alot:link{spring-Repository.java}

g-alot:link{spring-IService.java}

g-alot:link{spring-ServiceImpl.java}

g-alot:link{spring-Controller.java}

g-alot:link{test-ServiceTest.java}

g-alot:link{test-ControllerTest.java}

g-alot:link{test-ControllerIT.java}


echo src/main/java/g-alot{basePackage}/model | sed 's#\.#/#g' | xargs mkdir -p $1
echo src/main/java/g-alot{basePackage}/repository | sed 's#\.#/#g' | xargs mkdir -p $1
echo src/main/java/g-alot{basePackage}/service/impl | sed 's#\.#/#g' | xargs mkdir -p $1
echo src/main/java/g-alot{basePackage}/controller | sed 's#\.#/#g' | xargs mkdir -p $1
echo src/test/java/g-alot{basePackage}/test/unit/controller | sed 's#\.#/#g' | xargs mkdir -p $1
echo src/test/java/g-alot{basePackage}/test/unit/service | sed 's#\.#/#g' | xargs mkdir -p $1
echo src/test/java/g-alot{basePackage}/test/it/controller | sed 's#\.#/#g' | xargs mkdir -p $1

echo src/main/java/g-alot{basePackage}/model/g-alot{Entity} | sed 's#\.#/#g' | xargs -i echo "touch {}.java" | bash
echo src/main/java/g-alot{basePackage}/repository/g-alot{Entity}Repository | sed 's#\.#/#g' | xargs -i echo "touch {}.java" | bash
echo src/main/java/g-alot{basePackage}/service/g-alot{Entity}Service | sed 's#\.#/#g' | xargs -i echo "touch {}.java" | bash
echo src/main/java/g-alot{basePackage}/service/impl/g-alot{Entity}ServiceImpl | sed 's#\.#/#g' | xargs -i echo "touch {}.java" | bash
echo src/main/java/g-alot{basePackage}/controller/g-alot{Entity}Controller | sed 's#\.#/#g' | xargs -i echo "touch {}.java" | bash
echo src/test/java/g-alot{basePackage}/test/unit/controller/g-alot{Entity}ControllerTest | sed 's#\.#/#g' | xargs -i echo "touch {}.java" | bash
echo src/test/java/g-alot{basePackage}/test/unit/service/g-alot{Entity}ServiceTest | sed 's#\.#/#g' | xargs -i echo "touch {}.java" | bash
echo src/test/java/g-alot{basePackage}/test/it/controller/g-alot{Entity}ControllerTest | sed 's#\.#/#g' | xargs -i echo "touch {}.java" | bash
