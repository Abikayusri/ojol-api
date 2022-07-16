package abika.sinau.ojolapi.authentication

import abika.sinau.ojolapi.exception.OjolException
import abika.sinau.ojolapi.user.service.UserService
import abika.sinau.ojolapi.utils.BaseResponse
import abika.sinau.ojolapi.utils.Constant.CLAIMS
import abika.sinau.ojolapi.utils.Constant.SECRET
import abika.sinau.ojolapi.utils.Empty
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var userService: UserService

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

//        val claims = validate(request)
//        if (claims[CLAIMS] != null) {
//            // setup
//        } else {
//            SecurityContextHolder.clearContext()
//        }
//
//        filterChain.doFilter(request, response)

        try {
            if (JWTConfig.isPermit(request)) {
                filterChain.doFilter(request, response)
            } else {
                val claims = validate(request)
                if (claims[CLAIMS] != null) {
                    setupAuthentication(claims) {
                        filterChain.doFilter(request, response)
                    }
                } else {
                    SecurityContextHolder.clearContext()
                    throw OjolException("Token Required bro!!!")
                }
            }
        } catch (e: Exception) {
            val errorResponse = BaseResponse<Empty>()
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.contentType = "application/json"

            when (e) {
                is UnsupportedJwtException -> {
                    errorResponse.message = "Error Unsupported!!!"
                    val responseString = ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(errorResponse)
                    response.writer.println(responseString)
                }
                else -> {
                    errorResponse.message = e.message
                            ?: "Token is Invalid!!!"

                    val responseString = ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(errorResponse)
                    response.writer.println(responseString)
                }
            }
        }
    }

    private fun validate(request: HttpServletRequest): Claims {
        val jwtToken = request.getHeader("Authorization")
        return Jwts.parserBuilder().setSigningKey(SECRET.toByteArray()).build().parseClaimsJws(jwtToken).body
    }

    private fun setupAuthentication(claims: Claims, doOnNext: () -> Unit) {
        val authorities = claims[CLAIMS] as List<String>
        val authStream = authorities.stream().map { SimpleGrantedAuthority(it) }
                .collect(Collectors.toList())

        val auth = UsernamePasswordAuthenticationToken(claims.subject, null, authStream)
        SecurityContextHolder.getContext().authentication = auth
        val userId = SecurityContextHolder.getContext().authentication.principal as? String
        println("ASUUUUU subject ---> ${claims}")
        println("ASUUUUU user id ---> $userId")
        doOnNext.invoke()
    }

}