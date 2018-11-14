package gov.ca.cwds.jenkins.utils

class ProjectUtils implements Serializable {
    static boolean hasGradleBuildFile(script) {
        script.sh (script: 'test -e build.gradle', returnStatus: true) == 0
    }

    static boolean isBackEndProject = ProjectUtils.&hasGradleBuildFile

    static boolean hasPackageJsonFile(script) {
        script.sh (script: 'test -e package.json', returnStatus: true) == 0
    }

    static boolean isFrontEndProject = ProjectUtils.&hasPackageJsonFile
}
