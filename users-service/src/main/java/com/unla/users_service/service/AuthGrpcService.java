package com.unla.users_service.service;





import com.unla.users_service.jwt.JwtAuthProvider;
import com.userservice.grpc.AuthServiceGrpc;
import com.userservice.grpc.JwtRequest;
import com.userservice.grpc.JwtToken;
import io.grpc.stub.StreamObserver;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;


@GrpcService
public class AuthGrpcService extends AuthServiceGrpc.AuthServiceImplBase {

    @Value("${jwt.secret.key}")
    String jwtSecretKey;

    private final JwtAuthProvider jwtAuthProvider;

    public AuthGrpcService(JwtAuthProvider jwtAuthProvider) {
        this.jwtAuthProvider = jwtAuthProvider;
    }

    @Override
    public void authorize(JwtRequest request, StreamObserver<JwtToken> responseObserver) {
        Authentication authenticate = jwtAuthProvider.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));

        Instant now = Instant.now();
        Instant expiration = now.plus(1, ChronoUnit.HOURS);

        String authorities = authenticate.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        responseObserver.onNext(JwtToken.newBuilder().setJwtToken(Jwts.builder()
                .setSubject((String) authenticate.getPrincipal())
                .claim("auth", authorities)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                .compact()).build());

        responseObserver.onCompleted();
    }
}