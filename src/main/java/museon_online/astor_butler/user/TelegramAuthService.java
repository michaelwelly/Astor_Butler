package museon_online.astor_butler.user;

import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.*;

@Component
public class TelegramAuthService {

    public boolean isValidTelegramUser(String telegramId, String hash, String secretKey) {
        try {
            Map<String, String> dataCheck = new HashMap<>();
            dataCheck.put("id", telegramId);

            List<String> dataStrings = new ArrayList<>();
            for (Map.Entry<String, String> entry : dataCheck.entrySet()) {
                dataStrings.add(entry.getKey() + "=" + entry.getValue());
            }
            Collections.sort(dataStrings);
            String dataCheckString = String.join("\n", dataStrings);

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] key = digest.digest(secretKey.getBytes());

            Mac sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(key, "HmacSHA256");
            sha256HMAC.init(keySpec);

            byte[] hmac = sha256HMAC.doFinal(dataCheckString.getBytes());
            String hexHash = bytesToHex(hmac);

            return hexHash.equalsIgnoreCase(hash);
        } catch (Exception e) {
            return false;
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}