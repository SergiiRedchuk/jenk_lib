package gov.ca.cwds.jenkins

class LicensingSupport implements Serializable {
    def script
    def branchName
    def sshCredentialsId

    LicensingSupport(script, branchName, sshCredentialsId) {
        this.script = script
        this.branchName = branchName
        this.sshCredentialsId = sshCredentialsId
    }

    def generateLicenseInfo() {
        if ('master' == this.branchName) {
            switch (getLicensingSupportType(this.script)) {
                case LicensingSupportType.GRADLE_HIERYNOMUS_LICENSE:
                    // todo deleteLicenses copyLicenses
                    this.script.sh './gradlew deleteLicenses downloadLicenses copyLicenses'
                    // todo check result?
                    break
                case LicensingSupportType.RUBY_LICENSE_FINDER:
                    break
                case LicensingSupportType.NONE:
            // todo error
            }
        } else {
            script.echo 'Not working with the master branch. Skipping License Generation.'
        }
    }

    static LicensingSupportType getLicensingSupportType(script) {
        def result = LicensingSupportType.NONE
        if (Utils.hasGradleBuildFile(script)) {
            if (script.sh(script: 'grep -c "com.github.hierynomus.license" build.gradle',
                    returnStatus: true) == 0) {
                result = LicensingSupportType.GRADLE_HIERYNOMUS_LICENSE
            }
        } else if (Utils.hasPackageJsonFile(script)) {
            if (script.sh(script: 'grep -c "license_finder" package.json', returnStatus: true) == 0) {
                result = LicensingSupportType.RUBY_LICENSE_FINDER
            }
        }
        result
    }
}
