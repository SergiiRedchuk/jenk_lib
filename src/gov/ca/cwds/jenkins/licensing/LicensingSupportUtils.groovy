package gov.ca.cwds.jenkins.licensing

import gov.ca.cwds.jenkins.utils.ProjectUtils

class LicensingSupportUtils implements Serializable {
    static LicensingSupportType getLicensingSupportType(script) {
        def result = LicensingSupportType.NONE
        if (ProjectUtils.hasGradleBuildFile(script)) {
            if (script.sh(script: 'grep -c "com.github.hierynomus.license" build.gradle',
                    returnStatus: true) == 0) {
                result = LicensingSupportType.GRADLE_HIERYNOMUS_LICENSE
            }
        } else if (ProjectUtils.hasPackageJsonFile(script)) {
            if (script.sh(script: 'grep -c "license_finder" package.json', returnStatus: true) == 0) {
                result = LicensingSupportType.RUBY_LICENSE_FINDER
            }
        }
        result
    }
}
