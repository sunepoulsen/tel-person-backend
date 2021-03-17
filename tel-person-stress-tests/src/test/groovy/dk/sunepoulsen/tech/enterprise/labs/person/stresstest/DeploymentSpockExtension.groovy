package dk.sunepoulsen.tech.enterprise.labs.person.stresstest

import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.spock.DefaultDeploymentSpockExtension

class DeploymentSpockExtension extends DefaultDeploymentSpockExtension {
    static String COMPOSE_NAME = 'stress-tests'
    static String CONTAINER_NAME = 'tel-person-service'

    DeploymentSpockExtension() {
        super(COMPOSE_NAME, [CONTAINER_NAME])
    }
}
