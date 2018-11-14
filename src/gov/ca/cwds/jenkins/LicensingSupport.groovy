package gov.ca.cwds.jenkins

class LicensingSupport implements Serializable {
    final def LICENSE_FOLDER = 'legal'

    def script
    def branchName
    def sshCredentialsId
    def sshAgent
    def licensingSupportType

    LicensingSupport(script, branchName, sshCredentialsId) {
        this.script = script
        this.branchName = branchName
        this.sshCredentialsId = sshCredentialsId
        //this.sshAgent = new SshAgent(script, sshCredentialsId)
        //this.licensingSupportType = getLicensingSupportType(script)
        //this.script.echo('Detected Licensing Support Type: ' + this.licensingSupportType.title)
    }

    def checkLicensingSupportType() {
        if (LicensingSupportType.NONE == this.licensingSupportType) {
            throw new Exception('No known Licensing Support is found in the project')
        }
    }

    def generateLicenseReport() {
        if ('master' == this.branchName) {
            //checkLicensingSupportType()
            script.echo 'Generating License Information'
            /*
            switch (this.licensingSupportType) {
                case LicensingSupportType.GRADLE_HIERYNOMUS_LICENSE:
                    this.script.sh './gradlew deleteLicenses downloadLicenses copyLicenses'
                    break
                case LicensingSupportType.RUBY_LICENSE_FINDER:
                    this.script.sh 'yarn licenses-report'
                    break
            }*/
        } else {
            script.echo 'Not working with the master branch. Skipping License Generation for the other branch.'
        }
    }

    def pushLicenseReport() {
        if ('master' == this.branchName) {
            //checkLicensingSupportType()
            script.echo 'Updating License Information'
            /*
            switch (this.licensingSupportType) {
                case LicensingSupportType.GRADLE_HIERYNOMUS_LICENSE:
                case LicensingSupportType.RUBY_LICENSE_FINDER:
                    this.sshAgent.exec('git config --global user.email cwdsdoeteam@osi.ca.gov')
                    this.sshAgent.exec('git config --global user.name Jenkins')
                    this.sshAgent.exec('git add ' + LICENSE_FOLDER)
                    this.sshAgent.exec('git commit -m "updated license info"')
                    this.sshAgent.exec('git push --set-upstream origin master')
                    break
            }
            */
        } else {
            script.echo 'Not working with the master branch. Skipping Push License Report for the other branch.'
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
