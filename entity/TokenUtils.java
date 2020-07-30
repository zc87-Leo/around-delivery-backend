package entity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {
    //setting expiration time
//    private static final long EXPIRE_DATE= 1000 * 60 * 10;
    //token secret key
    private static final String TOKEN_SECRET = "DronbotDevTeam2020";

    public static String createToken (String userId,String password){

        String token = "";
        try {
            //when expired? 5 mins after the token created.
//            Date date = new Date(System.currentTimeMillis()+EXPIRE_DATE);
            //Inject our token_secret into the encoding algorithm
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //setting header
            Map<String,Object> header = new HashMap<>();
            header.put("typ","JWT");
            header.put("alg","HS256");
            //generate signature with u
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("userId",userId) //setting pay-loads
                    .withClaim("password",password)
//                    .withExpiresAt(date)
                    .sign(algorithm);//generate signature
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return token;
    }

    //verify the token, if the token is valid, we return true, else return false.
    public static boolean verifyToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    //test
//    public static void main(String[] args) {
//        String username ="Zebaracc";
//        String password = "ABCD";
//        String token = createToken(username,password);
//        System.out.println(token);
//        boolean b = verifyToken(token);
//        System.out.println(b);
//    }
}

