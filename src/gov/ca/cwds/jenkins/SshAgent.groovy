package gov.ca.cwds.jenkins

class SshAgent implements Serializable {
    def script
    def credentialsId

    SshAgent(script, credentialsId) {
        this.script = script
        this.credentialsId = credentialsId
    }

    def exec(String command) {
        script.sshagent (credentials: [credentialsId]) {
            sh(script: sshCommand(command), returnStatus: true)
        }
    }
}
