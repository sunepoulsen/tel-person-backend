package dk.sunepoulsen.tech.enterprise.labs.person.ct

import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.db.DbHelper
import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.spock.DefaultDeploymentSpockExtension

class DeploymentSpockExtension extends DefaultDeploymentSpockExtension {
    static String COMPOSE_NAME = 'ct'
    static String CONTAINER_NAME = 'tel-person-service'
    static String DB_CONTAINER_NAME = 'tel-persons-db'
    static String DB_USER = 'persons'
    static String DB_PASSWORD = 'jukilo90'
    static String DB_NAME = 'persons'

    DeploymentSpockExtension() {
        super(COMPOSE_NAME, [CONTAINER_NAME])
    }

    static void clearDatabase() {
        DbHelper dbHelper = new DbHelper(deployment, COMPOSE_NAME, DB_CONTAINER_NAME, DB_USER, DB_PASSWORD, DB_NAME)

        dbHelper.clearDatabase(['persons'])
    }
}
