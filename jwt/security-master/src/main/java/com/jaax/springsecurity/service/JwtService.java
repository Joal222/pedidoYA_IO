package com.jaax.springsecurity.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "cHL5E41qNTtMSLXr3djdEI5JFCg9DpTovQCOhhPNV/pyo9Y357RXidj08B3Z3Q3KZZzwyzzz21YmZ4a7Z9LcSzOg51pt/bFssEc2dUU0lqkoq1zVb4W83+1TTbjwzREvEUdmL2hD9tWEOpomvTvY4vH1N9bXzWtGOILeZAxYo6zXZ8V8LnmP36GFZsXFUpveHTk+5+3CDctQGlZNA2NnEyK7XEom97aVuOlSBBH3Js2XOaL74zDr3COs0X95oNQqJCd0dbBA1u1Y0/meXHd7DCmCjm6TtIAdUuetF8nttVxg6VTCmwWxeoqY6Ha2N6+ZxFymy9w8xu+94kueXD8YVIY8Pt6NtRniKTSV4BB1F9s=";

    public String generateToken(UserDetails userDetails) {return generateToken(Map.of(), userDetails);//new HashMap<>(), userDetails
    }
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {//Generar token con claims
        return Jwts.builder().setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60*60 * 24))//Equivalente a 24 horas
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)//Algoritmo de la firma
                .compact();//Para convertirlo a un string
    }
    public String getUserName( String token ) {
        return getClain( token, Claims::getSubject);//Obtener nombre del usuario del token que se esta enviando
    }

    public <T>  T getClain( String token, Function<Claims,T> claimsResolver ) {//La clase Claims es una interfaz que se utiliza para representar los claims de un JWT, Funcion que se utiliza para obtener el valor de un claim
        final Claims claims = getAllClaims( token );//La interfae Claims nos permite obtener distinta información del token, junto a su implementación.
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey( getSigningKey() )//Clave secreta que se utiliza para firmar el token, se crea el método getSigningKey que se creará a continuación
                .build()
                .parseClaimsJws( token )
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);//Convertir nuestra secretKey y convertirla en base 64
        return Keys.hmacShaKeyFor(keyBytes);//Crear una clave secreta con el algoritmo HMAC, Keys es una clase de Java
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDate(token).before( new Date() );
    }

    private Date getExpirationDate(String token) {
        return getClain(token, Claims::getExpiration);
    }//La intefaz Claims tiene un método getExpiration que devuelve la fecha de expiración del token
}
