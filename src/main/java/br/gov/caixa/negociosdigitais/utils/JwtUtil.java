package br.gov.caixa.negociosdigitais.utils;

import br.gov.caixa.negociosdigitais.config.exception.BusinessException;
import br.gov.caixa.negociosdigitais.config.exception.EnumBusinessExceptionMessage;
import br.gov.caixa.negociosdigitais.utils.enums.ParametroDoToken;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.eclipse.microprofile.config.ConfigProvider;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

import static java.util.Objects.nonNull;

public class JwtUtil {

    private JwtUtil() {
    }

    private static final String SSO_PUBLIC_KEY = ConfigProvider.getConfig().getValue("config.sso.publickey", String.class);
    private static final String AUD_TOKEN = ConfigProvider.getConfig().getValue("config.sso.audiences", String.class);
    private static final String ISS_TOKEN = ConfigProvider.getConfig().getValue("config.sso.issuer", String.class);

    public static boolean validaToken(String token) throws JWTVerificationException, NoSuchAlgorithmException, InvalidKeySpecException {
        JWTVerifier jwtVerifier = criarInstanciaValidadorJWT();
        if (jwtVerifier == null) return false;
        jwtVerifier.verify(token);
        return true;
    }

    private static JWTVerifier criarInstanciaValidadorJWT() throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decoded = Base64.getDecoder().decode(SSO_PUBLIC_KEY);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        KeyFactory kf;
        kf = KeyFactory.getInstance("RSA");
        RSAPublicKey generatePublic = (RSAPublicKey) kf.generatePublic(spec);
        Algorithm algorithm = Algorithm.RSA256(generatePublic, null);
        return JWT.require(algorithm)
                .withIssuer(ISS_TOKEN)
                .acceptIssuedAt(60L)
                .withAudience(AUD_TOKEN).build();
    }

    public static String recuperarParametroDoToken(String token, ParametroDoToken parametro) {
        Map<String, Claim> claims = recuperarParametros(token);
        try {
            return claims.get(parametro.getClaim()).asString();
        } catch (NullPointerException exception) {
            throw new BusinessException(EnumBusinessExceptionMessage.BAD_REQUEST);
        }
    }

    public static String recuperarParametroDoToken(String token, ParametroDoToken parametro, String valorPadrao) {
        try {
            Map<String, Claim> claims = recuperarParametros(token);
            return claims.get(parametro.getClaim()).asString();
        } catch (Exception exception) {
            return valorPadrao;
        }
    }

    private static Map<String, Claim> recuperarParametros(String token) {
        String tokenSso = removerPrefixoDoToken(token);
        DecodedJWT jwt = JWT.decode(tokenSso);
        return jwt.getClaims();
    }

    public static String removerPrefixoDoToken(String token) {
        return isTextoPreenchido(token)
                ? token.substring("Bearer ".length()).trim()
                : token;
    }

    public static boolean isTextoPreenchido(String texto) {
        return nonNull(texto) && (!texto.trim().isEmpty());
    }
}
