/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.api.db.hibernate;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.openmrs.api.context.Context;
import org.openmrs.util.OpenmrsConstants;
import org.springframework.stereotype.Component;

@Component
public class MultiTenantIdentifierResolver implements CurrentTenantIdentifierResolver {

  @Override
  public String resolveCurrentTenantIdentifier() {
    String tenantId = null;
    if (Context.isSessionOpen()) {
      tenantId = Context.getUserContext().getTenantId();
    }
    if (tenantId == null || tenantId.length() == 0) {
      return OpenmrsConstants.DATABASE_NAME;
    }
    return tenantId;
  }

  @Override
  public boolean validateExistingCurrentSessions() {
    return true;
  }
}
