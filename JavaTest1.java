public class QrCodePayloadGenerator {

    public static void main(String[] args) {
        String message = "Test";
        byte[] payload = generatePayload(message);
        System.out.println("Payload: " + byteArrayToBinaryString(payload));
    }

    public static byte[] generatePayload(String message) {
        int totalLength = message.length() + 1;

        byte[] payload = new byte[totalLength];

        payload[0] = (byte) totalLength;

        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            byte characterByte = (byte) c;
        
            byte errorCorrectingCode = calculateErrorCorrectingCode(characterByte);
        
            payload[i + 1] = characterByte;
            if (i + 2 < payload.length) {
                payload[i + 2] = errorCorrectingCode;
            }
        }

        return payload;
    }

    // Method to calculate the error correcting code for a byte
    private static byte calculateErrorCorrectingCode(byte characterByte) {
        // XOR the 8 bits of the character byte to get the error correcting code
        int xorResult = 0;
        for (int i = 0; i < 8; i++) {
            xorResult ^= ((characterByte >> i) & 1); // Extract each bit and XOR
        }
        // Convert the XOR result to a byte (padded with zeros)
        return (byte) xorResult;
    }

    // Method to convert a byte array to a binary string for printing
    private static String byteArrayToBinaryString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0')).append(" ");
        }
        return sb.toString().trim();
    }
}

