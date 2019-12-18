/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * you may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.synapse.util;

import org.apache.synapse.MessageContext;
import javax.xml.stream.XMLStreamException;

/*
 *  This class contains the util methods with respect to mediator properties
 */
public class MediatorPropertyUtils {

    /**
     * This method just serializes the OMElement, when setting a message type, we need to serialize to access the
     * inner element.
     *
     * @param msgCtx Synapse MessageContext
     */
    public static void serializeOMElement(MessageContext msgCtx) throws XMLStreamException {

        msgCtx.getEnvelope().toString(); // This is an implemented method in OMElement
    }
}
