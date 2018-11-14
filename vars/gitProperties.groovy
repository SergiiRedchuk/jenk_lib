package gov.ca.cwds.jenkins

def call() {
    def gitProps = [:]
    gitProps.user = 'Jenkins'
    gitProps.email = 'cwdsdoeteam@osi.ca.gov'
    gitProps
}