package dk.sunepoulsen.tech.enterprise.labs.person.ct

import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.spock.DefaultDeploymentSpockExtension

class DeploymentSpockExtension extends DefaultDeploymentSpockExtension {
    static String CONTAINER_NAME = 'tel-person-service'

    DeploymentSpockExtension() {
        super('ct', [CONTAINER_NAME])
    }
}
