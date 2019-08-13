/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.synapse.transport.http.conn;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AUTH;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.auth.params.AuthPNames;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;
import org.apache.synapse.transport.passthru.PassThroughConstants;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * ProfileProxyAuthenticator will be initialized when proxy profile is configured
 */
public class ProfileProxyAuthenticator implements ProxyAuthenticator {
    private ProxyConfig proxyConfig;
    private BasicScheme basicScheme;

    public ProfileProxyAuthenticator(ProxyConfig proxyConfig) throws MalformedChallengeException {
        this.proxyConfig = proxyConfig;
        basicScheme = new BasicScheme();
        basicScheme.processChallenge(new BasicHeader(AUTH.PROXY_AUTH, PassThroughConstants.PROXY_BASIC_REALM));
    }

    /**
     * this will add authentication header to the request
     * @param request outgoing http request
     * @param context http context
     * @throws AuthenticationException
     */
    public void authenticatePreemptively(HttpRequest request, HttpContext context) throws AuthenticationException {
        String targetHost = (String) context.getAttribute(PassThroughConstants.PROXY_PROFILE_TARGET_HOST);
        UsernamePasswordCredentials proxyCredentials = proxyConfig.getCredentialsForTargetHost(targetHost);
        if (proxyCredentials != null) {
            String username = proxyCredentials.getUserName();
            String password = proxyCredentials.getPassword();
            String usernameAndPassword = username + ":" + password;
            byte[] bytes;
            try {
                bytes = usernameAndPassword.getBytes(getCredentialsCharset(request));
            } catch (UnsupportedEncodingException e) {
                throw new AuthenticationException("Specified a unsupported encoding for decoding", e);
            }
            String encoded = DatatypeConverter.printBase64Binary(bytes);

            Header authHeader = new BasicHeader("Proxy-Authorization", "Basic " + encoded);
            request.addHeader(authHeader);
        }
    }

    private String getCredentialsCharset(final HttpRequest request) {
        String charset = (String) request.getParams().getParameter(AuthPNames.CREDENTIAL_CHARSET);
        if (charset == null) {
            charset = Charset.forName("US-ASCII").name();
        }
        return charset;
    }
}
