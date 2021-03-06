/********************************************************************************************
 * Copyright (c) 2020 Ewan Choi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************************/

package me.ewan.cellit.global.security.filters

import me.ewan.cellit.global.security.dtos.JwtAuthenticationDto
import me.ewan.cellit.global.security.tokens.PreAuthorizationToken
import org.codehaus.jackson.map.ObjectMapper
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 *
 * @author Ewan
 */
class JwtAuthenticationFilter(defaultFilterProcessesUrl: String?) : AbstractAuthenticationProcessingFilter(defaultFilterProcessesUrl) {

    private var myAuthenticationSuccessHandler: AuthenticationSuccessHandler? = null
    private var myAuthenticationFailureHandler: AuthenticationFailureHandler? = null

    constructor(defaultFilterProcessesUrl: String, authenticationSuccessHandler: AuthenticationSuccessHandler, authenticationFailureHandler: AuthenticationFailureHandler) : this(defaultFilterProcessesUrl){
        this.myAuthenticationSuccessHandler = authenticationSuccessHandler
        this.myAuthenticationFailureHandler = authenticationFailureHandler
    }

    /**
     * Authentication for jwt token as UsernamePasswordAuthenticationToken.
     *
     * @author Ewan
     * @param req
     * @param res
     * @return Authentication
     */
    override fun attemptAuthentication(req: HttpServletRequest, res: HttpServletResponse): Authentication {

        val formLoginDto = ObjectMapper().readValue(req.reader, JwtAuthenticationDto::class.java)

        val token = PreAuthorizationToken(formLoginDto)

        return super.getAuthenticationManager().authenticate(token)
    }

    /**
     * Try authentication at FormLoginAuthenticationProvider.
     * This method perform to make JWT token and inject to response.
     *
     * @author Ewan
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @return
     */
    override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?, authResult: Authentication?) {
        this.myAuthenticationSuccessHandler?.onAuthenticationSuccess(request, response, authResult)
    }

    /**
     * Try authentication at FormLoginAuthenticationProvider.
     * This method perform to handle error about FormLoginAuthenticationProvider result.
     *
     * @author Ewan
     * @param request
     * @param response
     * @param failed
     * @return
     */
    override fun unsuccessfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, failed: AuthenticationException?) {
        //super.unsuccessfulAuthentication(request, response, failed)
        this.myAuthenticationFailureHandler?.onAuthenticationFailure(request, response, failed)
    }
}