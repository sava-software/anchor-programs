package software.sava.anchor.programs.pyth.lazer.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

// A message with a verified ed25519 signature.
public record VerifiedMessage(// Public key that signed the message.
                              PublicKey publicKey,
                              // Signed message payload.
                              byte[] payload) implements Borsh {

  public static VerifiedMessage read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var publicKey = readPubKey(_data, i);
    i += 32;
    final byte[] payload = Borsh.readbyteVector(_data, i);
    return new VerifiedMessage(publicKey, payload);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    publicKey.write(_data, i);
    i += 32;
    i += Borsh.writeVector(payload, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 32 + Borsh.lenVector(payload);
  }
}
