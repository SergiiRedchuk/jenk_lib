package gov.ca.cwds.jenkins

import gov.ca.cwds.jenkins.licensing.LicensingSupport
import gov.ca.cwds.jenkins.SshAgent

def call(stageBody) {
    // evaluate the body block, and collect configuration into the object
    def stageParams = [:]
    stageBody.resolveStrategy = Closure.DELEGATE_FIRST
    stageBody.delegate = stageParams
    stageBody()

    stage('Update License Report') {
        def sshAgent
        if (stageParams.sshAgent) {
            sshAgent = stageParams.sshAgent
        } else {
            sshAgent = new SshAgent(stageParams.script, stageParams.sshCredentialsId)
        }
        def licensingSupport = new LicensingSupport(stageParams.script, stageParams.branch, sshAgent)
        licensingSupport.generateAndPushLicenseReport()
    }
}