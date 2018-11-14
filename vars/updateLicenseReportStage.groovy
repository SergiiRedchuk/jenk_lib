package gov.ca.cwds.jenkins

def call(body) {
    // evaluate the body block, and collect configuration into the object
    def pipelineParams = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    /*
    stage ('Update License Report') {
        licensingSupport.generateAndPushLicenseReport()
      }
     */

    stage('Update License Report') {
        echo sshCredentialsId
        echo $sshCredentialsId
    }
}