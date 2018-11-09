package gov.ca.cwds.jenkins

class SshAgent implements Serializable {
    def script
    def credentialsId

    SshAgent(script, credentialsId) {
        this.script = script
        this.credentialsId = credentialsId
    }

    def exec(String command) {
        def cmd = Utils.sshCommand(command)
        script.sshagent (credentials: [credentialsId]) {
            script.sh(script: cmd, returnStatus: true)
        }
    }
}
