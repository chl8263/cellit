package me.ewan.cellit.global.security.filters

import com.fasterxml.jackson.databind.ObjectMapper
import me.ewan.cellit.global.exception.dtos.ErrorDto
import me.ewan.cellit.global.security.HeaderTokenExtractor
import me.ewan.cellit.global.security.JwtDecoder
import me.ewan.cellit.global.security.JwtProperties.HEADER_STRING
import me.ewan.cellit.global.security.JwtProperties.BEARER_PREFIX
import me.ewan.cellit.global.security.tokens.PostAuthorizationToken
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthorizationFilter(authenticationManager: AuthenticationManager, private val extractor: HeaderTokenExtractor, private val decoder: JwtDecoder)
    : BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        val tokenPayload = request.getHeader(HEADER_STRING)

        if(tokenPayload.isNullOrBlank() || !tokenPayload.startsWith(BEARER_PREFIX)){

            val objectMapper = ObjectMapper()

            val errorDto = ErrorDto("UNAUTHORIZED", "UNAUTHORIZED")

            response.contentType = MediaType.APPLICATION_JSON_VALUE
            response.status = HttpStatus.UNAUTHORIZED.value()
            response.writer.write(objectMapper.writeValueAsString(errorDto))
            return
        }

        val accountContext = decoder.decodeJwt(extractor.extract(tokenPayload))
        val token = PostAuthorizationToken(accountContext.account, accountContext.password, accountContext.authorities)
        SecurityContextHolder.getContext().authentication = token
        chain.doFilter(request, response)
    }

}