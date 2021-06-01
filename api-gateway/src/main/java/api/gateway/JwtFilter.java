package api.gateway;

import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.ZuulFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class JwtFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(JwtFilter.class);

    private final static String KEY_ID = "8e35beb7-a8fe-45d8-88c8-b79823378b57";
    private final static String ISSUER = "sample";
    private final static String EXP_DATE = "3000-05-01 10:16:00";
    private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static String[] ROLES = {"admin", "user"};

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 20;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        ctx.addZuulRequestHeader("Authorization", "Bearer " + generateJwt());

        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        return null;
    }

    private String generateJwt() {
        try {
            String token = JWT.create()
                    .withKeyId(KEY_ID)
                    .withIssuer(ISSUER)
                    .withSubject("Client")
                    .withArrayClaim("roles", ROLES)
                    .withExpiresAt(format.parse(EXP_DATE))
                    .sign(getAlgorithm());

            return token;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private Algorithm getAlgorithm() throws IOException {
        RSAPublicKey publicKey = (RSAPublicKey) PemUtils.readPublicKeyFromFile(getClass().getClassLoader().getResource("public.pem").getPath(), "RSA");;
        RSAPrivateKey privateKey = (RSAPrivateKey) PemUtils.readPrivateKeyFromFile(getClass().getClassLoader().getResource("private.pem").getPath(), "RSA");//Get the key instance
        Algorithm algorithmRS = Algorithm.RSA256(publicKey, privateKey);
        return algorithmRS;
    }
}