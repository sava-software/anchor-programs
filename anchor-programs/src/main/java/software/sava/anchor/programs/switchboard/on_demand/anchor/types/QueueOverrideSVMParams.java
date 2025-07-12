package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record QueueOverrideSVMParams(byte[] secp256k1Signer,
                                     long maxQuoteVerificationAge,
                                     byte[] mrEnclave,
                                     long slot) implements Borsh {

  public static final int BYTES = 112;
  public static final int SECP_222K_1_SIGNER_LEN = 64;
  public static final int MR_ENCLAVE_LEN = 32;

  public static QueueOverrideSVMParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var secp256k1Signer = new byte[64];
    i += Borsh.readArray(secp256k1Signer, _data, i);
    final var maxQuoteVerificationAge = getInt64LE(_data, i);
    i += 8;
    final var mrEnclave = new byte[32];
    i += Borsh.readArray(mrEnclave, _data, i);
    final var slot = getInt64LE(_data, i);
    return new QueueOverrideSVMParams(secp256k1Signer,
                                      maxQuoteVerificationAge,
                                      mrEnclave,
                                      slot);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(secp256k1Signer, _data, i);
    putInt64LE(_data, i, maxQuoteVerificationAge);
    i += 8;
    i += Borsh.writeArray(mrEnclave, _data, i);
    putInt64LE(_data, i, slot);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
