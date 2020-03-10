echo src/main/java/g-alot{basePackage}/model | sed 's#\.#/#g' | xargs mkdir -p $1
echo src/main/java/g-alot{basePackage}/repository | sed 's#\.#/#g' | xargs mkdir -p $1
echo src/main/java/g-alot{basePackage}/service/impl | sed 's#\.#/#g' | xargs mkdir -p $1
echo src/main/java/g-alot{basePackage}/controller | sed 's#\.#/#g' | xargs mkdir -p $1
echo src/test/java/g-alot{basePackage}/test/unit/controller | sed 's#\.#/#g' | xargs mkdir -p $1
echo src/test/java/g-alot{basePackage}/test/unit/service | sed 's#\.#/#g' | xargs mkdir -p $1
echo src/test/java/g-alot{basePackage}/test/it/controller | sed 's#\.#/#g' | xargs mkdir -p $1

echo "[+] Generating in src/main/java/"
declare gAlotCode=$(g-alot -t jpa-Entity.java -r Entity:g-alot{Entity},entity:g-alot{entity},basePackage:g-alot{basePackage}) 
declare gAlotTargetFile=$(echo src/main/java/g-alot{basePackage}/model/g-alot{Entity} | sed 's#\.#/#g' | xargs -i echo "{}.java")
echo "[+] g-alot{basePackage}.model.g-alot{Entity}.java => $gAlotTargetFile"
echo $gAlotCode > $gAlotTargetFile 

declare gAlotCode=$(g-alot -t spring-Repository.java -r Entity:g-alot{Entity},entity:g-alot{entity},basePackage:g-alot{basePackage}) 
declare gAlotTargetFile=$(echo src/main/java/g-alot{basePackage}/repository/g-alot{Entity}Repository | sed 's#\.#/#g' | xargs -i echo "{}.java")
echo "[+] g-alot{basePackage}.repository.g-alot{Entity}Repository.java => $gAlotTargetFile"
echo $gAlotCode > $gAlotTargetFile 

declare gAlotCode=$(g-alot -t spring-IService.java -r Entity:g-alot{Entity},entity:g-alot{entity},basePackage:g-alot{basePackage}) 
declare gAlotTargetFile=$(echo src/main/java/g-alot{basePackage}/service/g-alot{Entity}Service | sed 's#\.#/#g' | xargs -i echo "{}.java")
echo "[+] g-alot{basePackage}.service.g-alot{Entity}Service.java => $gAlotTargetFile"
echo $gAlotCode > $gAlotTargetFile 

declare gAlotCode=$(g-alot -t spring-ServiceImpl.java -r Entity:g-alot{Entity},entity:g-alot{entity},basePackage:g-alot{basePackage}) 
declare gAlotTargetFile=$(echo src/main/java/g-alot{basePackage}/service/impl/g-alot{Entity}ServiceImpl | sed 's#\.#/#g' | xargs -i echo "{}.java")
echo "[+] g-alot{basePackage}.service.impl.g-alot{Entity}ServiceImpl.java => $gAlotTargetFile"
echo $gAlotCode > $gAlotTargetFile 

declare gAlotCode=$(g-alot -t spring-Controller.java -r Entity:g-alot{Entity},entity:g-alot{entity},basePackage:g-alot{basePackage}) 
declare gAlotTargetFile=$(echo src/main/java/g-alot{basePackage}/controller/g-alot{Entity}Controller | sed 's#\.#/#g' | xargs -i echo "{}.java")
echo "[+] g-alot{basePackage}.controller.g-alot{Entity}Controller.java => $gAlotTargetFile"
echo $gAlotCode > $gAlotTargetFile 

echo "[+] Generating in src/test/java/"
declare gAlotCode=$(g-alot -t test-ServiceTest.java.java -r Entity:g-alot{Entity},entity:g-alot{entity},basePackage:g-alot{basePackage}) 
declare gAlotTargetFile=$(echo src/test/java/g-alot{basePackage}/test/unit/service/g-alot{Entity}ServiceTest | sed 's#\.#/#g' | xargs -i echo "{}.java")
echo "[+] g-alot{basePackage}.test.unit.service.g-alot{Entity}ServiceTest.java => $gAlotTargetFile"
echo $gAlotCode > $gAlotTargetFile 

declare gAlotCode=$(g-alot -t test-ControllerTest.java.java -r Entity:g-alot{Entity},entity:g-alot{entity},basePackage:g-alot{basePackage}) 
declare gAlotTargetFile=$(echo src/test/java/g-alot{basePackage}/test/unit/controller/g-alot{Entity}ControllerTest | sed 's#\.#/#g' | xargs -i echo "{}.java")
echo "[+] g-alot{basePackage}.test.unit.controller.g-alot{Entity}ControllerTest.java => $gAlotTargetFile"
echo $gAlotCode > $gAlotTargetFile 

declare gAlotCode=$(g-alot -t test-ControllerIT.java.java -r Entity:g-alot{Entity},entity:g-alot{entity},basePackage:g-alot{basePackage}) 
declare gAlotTargetFile=$(echo src/test/java/g-alot{basePackage}/test/it/controller/g-alot{Entity}ControllerIT | sed 's#\.#/#g' | xargs -i echo "{}.java")
echo "[+] g-alot{basePackage}.test.it.controller.g-alot{Entity}ControllerIT.java => $gAlotTargetFile"
echo $gAlotCode > $gAlotTargetFile 
