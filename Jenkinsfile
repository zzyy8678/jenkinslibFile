@Library('jenkinslib') _

def tools=new org.devops.tools()

pipeline{
agent any 
options{
 		timestamps()//日志会有时间
		skipDefaultCheckout()//删除隐式checkout scml
		disableConcurrentBuilds()//禁止并行
		timeout(time: 1, unit: "HOURS")//流水线超时
	}
stages {
	stage("GetCode"){
		steps{
			script{
				println("获取代码")
//				tools.printMse("hi，你在哪，你会想我吗",'green')
			}
		}
	}
	stage("Build"){
		steps{
			script{
				println("应用打包")
//				tools.printMse("应用打包",'green')
//				hello()
			}
		}
	}
	stage("CodeScan"){
		steps{
			script{
				println("代码扫描")
//				tools.printMse("代码扫描",'green')
			}
		}
	}
}
post{
	always{
		echo 'I will always say hello again'
	}
	success{
		script{
			currentBuild.description="\n构建成功"
		}
	}
	failure{
		script{
			urrentBuild. description="\n构建失败了"
		}
	}
	aborted{
		script{
			currentBuild.description="\n 构建取消"
		}
	}
}
}

