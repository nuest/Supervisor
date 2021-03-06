/**
 * ﻿Copyright (C) 2013 52°North Initiative for Geospatial Open Source Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.n52.supervisor.checks.ows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Daniel Nüst
 * 
 */
public class SosCapabilitiesCheckRunner extends OwsCapabilitiesCheckRunner {

    private static Logger log = LoggerFactory.getLogger(SosCapabilitiesCheckRunner.class);

    private static final String SOS_SERVICE = "SOS";

    public SosCapabilitiesCheckRunner(final OwsCapabilitiesCheck check) {
        super(check);

        if ( !check.getServiceType().equals(SOS_SERVICE)) {
			log.warn("Checking non-SOS {} with SOS runner: {}", check, this);
		}
    }

    @Override
    public boolean check() {
        log.debug("Checking SOS Capabilities for " + check.getServiceUrl());
        return runGetRequestParseDocCheck();
    }
    
    @Override
    protected String buildGetRequest() {
    	final String kvpRequest = super.buildGetRequest();
    	// the parameter 'serviceVersion' is not supported by 52North implementation but 'AcceptVersions' is!
    	return kvpRequest.replaceFirst("serviceVersion", "AcceptVersions");
    }

}
