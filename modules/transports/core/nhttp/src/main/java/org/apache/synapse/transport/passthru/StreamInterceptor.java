/*
 * Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.synapse.transport.passthru;

import java.nio.ByteBuffer;
import java.util.Map;

/**
 * Intercepts the streams of source/target request and responses.
 */
public interface StreamInterceptor {

    /**
     * Intercepts the output ready method of source handler.
     *
     * @param buffer     buffer with data written to channel.
     * @param properties custom properties.
     * @return whether to flush the data to channel or not.
     */
    boolean proceedSourceResponse(ByteBuffer buffer, Map<String, Object> properties);

}
