package codeOrchestra.colt.as.model.beans.air

import codeOrchestra.colt.core.model.IModelElement
import codeOrchestra.groovyfx.FXBindable
import groovy.transform.Canonical

/**
 * @author Dima Kruk
 */
@Canonical
@FXBindable
class AIRModel implements IModelElement {
    String airSDKPath
    String provisionPath
    String keystorePath
    String storePass

    @Override
    Closure buildXml() {
        return {
            'sdk-path'(airSDKPath)
            'provision-path'(provisionPath)
            'keystore-path'(keystorePath)
            'pass'(storePass)
        }
    }

    @Override
    void buildModel(Object node) {
        airSDKPath = node.'sdk-path'
        provisionPath = node.'provision-path'
        keystorePath = node.'keystore-path'
        storePass = node.'pass'
    }
}
