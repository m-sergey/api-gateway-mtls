package jwt.security;

import com.auth0.jwk.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Сервисный класс
 */
public class JwtTokenUtilTest {

    private final static String KEY_ID = "8e35beb7-a8fe-45d8-88c8-b79823378b57";
    private final static String ISSUER = "sample";
    private final static String EXP_DATE = "3000-05-01 10:16:00";
    private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static String[] ROLES = {"admin", "user"};
    private final static String TOKEN = "eyJraWQiOiI4ZTM1YmViNy1hOGZlLTQ1ZDgtODhjOC1iNzk4MjMzNzhiNTciLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJVcmFsc2liIiwicm9sZXMiOlsiYWRtaW4iLCJ1c2VyIl0sImlzcyI6InNhbXBsZSIsImV4cCI6MzI1MTQwNzQxNjB9.Q9zZJ_Zxjy3FVNUl_mogs0AraZHHD94EimACNUFAeKLxGa9rVJVCniUogsoJ4ApVbLSGgRI85B8eXvS4_BV9v8rQ4mwVBJTOknC-Xn_3T-snGZr7QrnmzIHMYfj3j_MOR7P4W6oyMIWvravtQY9DaWesf5RpQ0CazWpr9Uf45ga3lbDWLg3mDizoqzj_7EY-ZVq_eLMzr_lOBRVIFllSC8Ev_H_NOiqjMzivjuoafPMmcpcn2yFbqYBlHLn00MqWa61r4e23GLxX2AY1I5BtqmHjITy79hKN8zGtJK-qOhGX3eV4u458Gh-RQvJNS949o_OXzfd_xLZcmiDIfuPXbg";

    @Test
    public void generateJwt() throws IOException, ParseException {
        String token = JWT.create()
                .withKeyId(KEY_ID)
                .withIssuer(ISSUER)
                .withSubject("Client")
                .withArrayClaim("roles", ROLES)
                .withExpiresAt(format.parse(EXP_DATE))
                .sign(getAlgorithm());
        System.out.println("Token: " + token);
    }

    @Test
    public void validateJWT() throws IOException {
        JWTVerifier verifier = JWT.require(getAlgorithm())
                .withIssuer(ISSUER)
                .build();

        DecodedJWT jwt = JWT.decode(TOKEN);

        verifier.verify(jwt);
    }

    @Test
    public void validateWithJWKS() throws JwkException {
//        JwkProvider provider = new JwkProviderBuilder(getClass().getClassLoader().getResource("jwks.json"))
        JwkProvider provider = new JwkProviderBuilder("http://localhost:8081")
                .cached(10, 24, TimeUnit.HOURS)
                .build();

        DecodedJWT jwt = JWT.decode(TOKEN);

        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) provider.get(jwt.getKeyId()).getPublicKey(), null);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();

        verifier.verify(jwt);
    }

    private Algorithm getAlgorithm() throws IOException {
        RSAPublicKey publicKey = (RSAPublicKey) PemUtils.readPublicKeyFromFile(getClass().getClassLoader().getResource("public.pem").getPath(), "RSA");;
        RSAPrivateKey privateKey = (RSAPrivateKey) PemUtils.readPrivateKeyFromFile(getClass().getClassLoader().getResource("private.pem").getPath(), "RSA");//Get the key instance
        Algorithm algorithmRS = Algorithm.RSA256(publicKey, privateKey);
        return algorithmRS;
    }
}
