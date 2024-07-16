package com.potato.util;

import com.potato.common.exception.CustomException;
import com.potato.common.ErrorCodeMap;
import com.potato.common.exception.TokenErrorException;
import com.potato.common.exception.TokenExpireException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
public class JwtUtil {
  private static final SecretKey secretKey = Jwts.SIG.HS256.key().build();

  private static final ErrorCodeMap errorCodeMap = new ErrorCodeMap();

  public static String generateToken(String username, Long userId, String status) {
    int expiration = 86400;
    Date expirationDate = new Date(System.currentTimeMillis() + expiration * 1000);
    return Jwts.builder().
            subject(username).
            issuedAt(new Date()).
            expiration(expirationDate).
            claim("userId",userId).
            claim("status",status).
            signWith(secretKey).
            compact();

  }

  public static Claims parseToken(String token) {
    String errorCode;
    String errorMessage;
    try {
      errorCode = "1005";
      errorMessage = errorCodeMap.getErrorMessage(errorCode);
      Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
      if (claims.getExpiration().before(new Date())) {
        log.info(errorMessage);
        throw new TokenExpireException(errorMessage);
      }
      return claims;
    } catch (Exception e) {
      errorCode = "1004";
      errorMessage = errorCodeMap.getErrorMessage(errorCode);
      log.error(errorMessage);
      throw new TokenErrorException(errorMessage);
    }
  }
}
