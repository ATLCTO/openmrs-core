/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.web.filter.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.util.OpenmrsConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiTenantIdentifierResolver {
	
	private MultiTenantIdentifierResolver() {
	}
	
	private static final Logger log = LoggerFactory.getLogger(MultiTenantIdentifierResolver.class);
	
	private static String stategy = null;
	
	/**
	 * Tries to resolve the multi-tenant identifier from the http request, using the strategy defined in the 
	 * multitentant.strategy runtime properties file
	 *
	 * @param request http request
	 * @return multi-tenant identifier for this request
	 */
	public static String resolveTenantId(HttpServletRequest request) {
		
		if (stategy == null) {
			stategy = Context.getRuntimeProperties().getProperty(OpenmrsConstants.MULTI_TENANT_STRATEGY_RUNTIME_PROPERTY,
			    "none");
			if (StringUtils.isEmpty(stategy)) {
				stategy = "none";
			}
		}
		
		if (stategy.equals("none")) {
			return null;
		}
		
		if (stategy.equals("wildcard")) {
			
			String host = request.getHeader("host");
			if (StringUtils.isEmpty(host)) {
				return null;
			}
			
			int dot = host.indexOf('.');
			if (dot <= 0) {
				return null;
			}
			
			String tenantId = host.substring(0, dot);
			return tenantId;
		}
		
		throw new RuntimeException("Multi-tenant strategy '" + stategy + "' is invalid.");
	}
}
