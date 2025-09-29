package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record GuardianQuoteVerifyParams(long timestamp,
                                        byte[] mrEnclave,
                                        int reserved1,
                                        PublicKey ed25519Key,
                                        byte[] secp256k1Key,
                                        long slot,
                                        byte[] signature,
                                        int recoveryId,
                                        int[] advisories) implements Borsh {

  public static final int MR_ENCLAVE_LEN = 32;
  public static final int SECP_222K_1_KEY_LEN = 64;
  public static final int SIGNATURE_LEN = 64;
  public static GuardianQuoteVerifyParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var timestamp = getInt64LE(_data, i);
    i += 8;
    final var mrEnclave = new byte[32];
    i += Borsh.readArray(mrEnclave, _data, i);
    final var reserved1 = getInt32LE(_data, i);
    i += 4;
    final var ed25519Key = readPubKey(_data, i);
    i += 32;
    final var secp256k1Key = new byte[64];
    i += Borsh.readArray(secp256k1Key, _data, i);
    final var slot = getInt64LE(_data, i);
    i += 8;
    final var signature = new byte[64];
    i += Borsh.readArray(signature, _data, i);
    final var recoveryId = _data[i] & 0xFF;
    ++i;
    final var advisories = Borsh.readintVector(_data, i);
    return new GuardianQuoteVerifyParams(timestamp,
                                         mrEnclave,
                                         reserved1,
                                         ed25519Key,
                                         secp256k1Key,
                                         slot,
                                         signature,
                                         recoveryId,
                                         advisories);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, timestamp);
    i += 8;
    i += Borsh.writeArrayChecked(mrEnclave, 32, _data, i);
    putInt32LE(_data, i, reserved1);
    i += 4;
    ed25519Key.write(_data, i);
    i += 32;
    i += Borsh.writeArrayChecked(secp256k1Key, 64, _data, i);
    putInt64LE(_data, i, slot);
    i += 8;
    i += Borsh.writeArrayChecked(signature, 64, _data, i);
    _data[i] = (byte) recoveryId;
    ++i;
    i += Borsh.writeVector(advisories, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8
         + Borsh.lenArray(mrEnclave)
         + 4
         + 32
         + Borsh.lenArray(secp256k1Key)
         + 8
         + Borsh.lenArray(signature)
         + 1
         + Borsh.lenVector(advisories);
  }
}
