package gov.ca.cwds.jenkins.licensing

class LicensingSupport implements Serializable {
    final def LICENSE_FOLDER = 'legal'

    def script
    def branchName
    def sshAgent
    def licensingSupportType

    LicensingSupport(script, branchName, sshAgent) {
        this.script = script
        this.branchName = branchName
        this.sshAgent = sshAgent
        this.licensingSupportType = null
    }

    def initLicensingSupportType() {
        if (null == this.licensingSupportType) {
            this.licensingSupportType = LicensingSupportUtils.getLicensingSupportType(script)
            this.script.echo('Detected Licensing Support Type: ' + this.licensingSupportType.title)
        }
        if (LicensingSupportType.NONE == this.licensingSupportType) {
            throw new Exception('No known Licensing Support is found in the project')
        }
    }

    def generateLicenseReport() {
        if ('master' == this.branchName) {
            initLicensingSupportType()
            script.echo 'Generating License Information'
            switch (this.licensingSupportType) {
                case LicensingSupportType.GRADLE_HIERYNOMUS_LICENSE:
                    this.script.sh './gradlew deleteLicenses downloadLicenses copyLicenses'
                    break
                case LicensingSupportType.RUBY_LICENSE_FINDER:
                    this.script.sh 'yarn licenses-report'
                    break
            }
        } else {
            script.echo 'Not working with the master branch. Skipping License Generation for the other branch.'
        }
    }

    def pushLicenseReport() {
        if ('master' == this.branchName) {
            initLicensingSupportType()
            script.echo 'Updating License Information'
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
        } else {
            script.echo 'Not working with the master branch. Skipping Push License Report for the other branch.'
        }
    }
}
