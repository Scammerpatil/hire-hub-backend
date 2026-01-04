package com.hirehub.SpringServer.Controller;

import com.hirehub.SpringServer.DTO.LoginRequest;
import com.hirehub.SpringServer.DTO.LoginResponse;
import com.hirehub.SpringServer.DTO.UserRequest;
import com.hirehub.SpringServer.DTO.UserResponse;
import com.hirehub.SpringServer.Entity.User;
import com.hirehub.SpringServer.Services.UserService;
import com.hirehub.SpringServer.UtilityClasses.JwtParameter;
import com.hirehub.SpringServer.UtilityClasses.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {
        try {
            // Hash password properly
            String encryptedPassword = passwordEncoder.encode(request.getPassword());

            User user = new User();
            user.setFullName(request.getFullName());
            user.setEmail(request.getEmail());
            user.setPassword(encryptedPassword);
            user.setRole(request.getRole());
            user.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            User saved = userService.createUser(user);

            // Prepare response
            UserResponse response = new UserResponse();
            response.setUserId(saved.getUserId());
            response.setFullName(saved.getFullName());
            response.setEmail(saved.getEmail());
            response.setRole(saved.getRole());
            response.setCreatedAt(saved.getCreatedAt());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest login) {

        Optional<User> userOpt = userService.getUserByEmail(login.getEmail());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        User user = userOpt.get();

        // BCrypt Password Check
        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        // Prepare JWT claims
        JwtParameter param = new JwtParameter(
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole()
        );

        // Generate JWT
        String token = jwtUtil.generateToken(param);

        ResponseCookie cookie = ResponseCookie.from("authToken", token)
                .httpOnly(true)
                .secure(false) // set true in production with https
                .sameSite("Lax") // or "None" if frontend is on different domain
                .path("/")
                .maxAge(60 * 60) // 1 hour
                .build();

        // Response
        LoginResponse response = new LoginResponse(
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole()
        );
//        Set-Cookie: authToken = <token>;
        return ResponseEntity.ok().header("Set-Cookie", cookie.toString()).body(response);
    }

    @GetMapping("/verify")
    public ResponseEntity<User> verifyUser(@CookieValue(name = "authToken", required = false) String authToken) {
        if (authToken == null || authToken.isEmpty()) {
            return ResponseEntity.status(401).build();
        }
        try {
            if(!jwtUtil.validateToken(authToken)) {
                return ResponseEntity.status(401).build();
            }
            String email = jwtUtil.extractAllClaims(authToken).getSubject();
            Optional<User> userOpt = userService.getUserByEmail(email);
            return userOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(401).build());
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/verify-token")
    public ResponseEntity<?> verifyToken(@CookieValue(name = "authToken", required = false) String authToken) {
        if (authToken == null || authToken.isEmpty()) {
            return ResponseEntity.status(401).body("No token provided");
        }
        try {
            if(!jwtUtil.validateToken(authToken)) {
                return ResponseEntity.status(401).body("Invalid or expired token");
            }
            return ResponseEntity.ok("Token is valid");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(name = "authToken", required = false) String authToken) {
        ResponseCookie cookie = ResponseCookie.from("authToken", "")
                .httpOnly(true)
                .secure(false) // set true in production with https
                .sameSite("Lax") // or "None" if frontend is on different domain
                .path("/")
                .maxAge(0) // Expire immediately
                .build();

        return ResponseEntity.ok().header("Set-Cookie", cookie.toString()).body("Logged out successfully");
    }
}
