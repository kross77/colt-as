package codeOrchestra.colt.as.model.beans

import codeOrchestra.colt.as.model.IModelElement
import codeOrchestra.colt.as.model.LiveType
import codeOrchestra.groovyfx.FXBindable
import groovy.transform.Canonical

/**
 * @author Dima Kruk
 */
@Canonical
@FXBindable
class LiveSettingsModel implements IModelElement{
    String liveType = LiveType.ANNOTATED.name()
    boolean startSessionPaused
    boolean makeGSLive
    String maxLoop = "1000"

    @Override
    Closure buildXml() {
        return {
            'live-type'(liveType)
            'paused'(startSessionPaused)
            'make-gs-live'(makeGSLive)
            'max-loop'(maxLoop)
        }
    }

    @Override
    void buildModel(Object node) {
        liveType = node.'live-type'
        startSessionPaused = node.'paused' == "true"
        makeGSLive = node.'make-gs-live' == "true"
        maxLoop = node.'max-loop'
    }
}