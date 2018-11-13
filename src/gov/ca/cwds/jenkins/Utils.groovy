package gov.ca.cwds.jenkins

class Utils implements Serializable {
    static def sshCommand(String command) {
        // Used to avoid known_hosts addition, which would require each machine to have GitHub added in advance (maybe should do?)
        'GIT_SSH_COMMAND="ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no" ' + command
    }

    static boolean hasGradleBuildFile(script) {
        script.sh (script: 'test -e build.gradle', returnStatus: true) == 0
    }

    static boolean hasPackageJsonFile(script) {
        script.sh (script: 'test -e package.json', returnStatus: true) == 0
    }
}
