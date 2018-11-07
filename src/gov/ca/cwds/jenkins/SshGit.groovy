package gov.ca.cwds.jenkins

class SshGit {
    def credentialsId
    
    SshGit(credentialsId) {
        this.credentialsId = credentialsId
    }

    def exec(String gitArgs) {
        sshagent (credentials: [credentialsId]) {
            sh(script: sshGitCommand(gitArgs), returnStatus: true)
        }
    }
}
