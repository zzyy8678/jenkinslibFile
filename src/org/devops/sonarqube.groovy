 package org.devops

//scan
def SonarScan(sonarServer,projectName,projectDesc,projectPath){
     //定义服务器列表
    def servers = ["test":"sonarqube-test","prod":"sonarqube-prod"]
    withSonarQubeEnv("${servers[sonarServer]}"){
        def scannerHome = "/usr/local/SonarScanner/sonar-scanner-4.8.0.2856-macosx"
        //def sonarServer="http://172.16.211.134:9000 "
        def sonarDate = sh  returnStdout: true, script: 'date  +%Y%m%d%H%M%S'
        sonarDate = sonarDate - "\n"
        sh """
        ${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=${projectName} \
        -Dsonar.projectName=${projectName} \
        -Dsonar.projectVersion=${sonarDate} \
        -Dsonar.ws.timeout=30 \
        -Dsonar.projectDescription=${projectDesc} \
        -Dsonar.sources=${projectPath} \
        -Dsonar.sourceEncoding=UTF-8 \
        -Dsonar.java.binaries=./target/classes \
        -Dsonar.java.test.binaries=target/test-classes \
        -Dsonar.java.surefire.report=target/surefire-reports \
        -X
        """
    }
    def qg = waitForQualityGate()
    if (qg.status != 'OK') {
        error "Pipeline aborted due to quality gate failure: ${qg.status}"
  }
    
}
