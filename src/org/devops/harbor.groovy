#！groovy
@Library(' jenkinslib@master').

    def tools =new org.devops. tools()

String registryName = "${env.registryName}"
String serviceName = "${env.serviceName)"
String tagName = ${env.tagName}"
def harborProjects = []
currentBuild.description="Trigger by ${serviceName} ${tagName }"
pipeline {
    agent { 
    node { label “build"} }
          stages{
        stage("GetHarhorTags"){
            steps{
                timeout(time:5,unit:"MINUTES"){
                    script{
                        tools.PrintMes("获取仓库中的项目信息","green")
                        println(erviceName)

                        try {
                            reponse =httpRequest anthentication :"harbor-admin",
                                "https://registry.demo.com/api/repositories/${registryName}/${serviceName}/tags",
                                ignoreSslErrors:true
                            reponse=readJSON text :'''&{reponse.content}'''

                        }
                        catch(e) {
                            reponse=['name':'']
                            println(e)
                            println("Harbor镜像不存在此标签")

                        }
                        println(tagName)
                        for(tagName in reponse) {
                            harborProjects <<tagName['name']
                        }
                        println(harborProjects)
                    }
                }
            }
        }
        stage("DeleteHarborTags"){
            steps{
                timeout(time:20,unit:"MINUTES"){
                    script{
                        tools.PrintMes("总共找到 ${harborProjects.size()}个标签","green")
                        sumImageNum =harborProjects.size()
                        for(tag in harborProjects){
                            sumImageNum -=1
                            tools.PrintMes("${sumImageNum} Delete Tags--->${serviceName}--->${tagName}---${tag}","green")

                            httpRequest httpMode:'DELETE'
                            anthentication:'????'
                            url:
                                https://registry.demo.com/api/repositories/${registryName}/${serviceName}/tags/${tag}
                                ignoreSslErrors:true


                            sleep 30



                        }
                    }
                }
                }
        }


    }
