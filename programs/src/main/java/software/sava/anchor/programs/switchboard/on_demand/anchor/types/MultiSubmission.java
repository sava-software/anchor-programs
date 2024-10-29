package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

public record MultiSubmission(BigInteger[] values,
                              byte[] signature,
                              int recoveryId) implements Borsh {

  public static MultiSubmission read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var values = Borsh.readBigIntegerVector(_data, i);
    i += Borsh.lenVector(values);
    final var signature = new byte[64];
    i += Borsh.readArray(signature, _data, i);
    final var recoveryId = _data[i] & 0xFF;
    return new MultiSubmission(values, signature, recoveryId);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(values, _data, i);
    i += Borsh.writeArray(signature, _data, i);
    _data[i] = (byte) recoveryId;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(values) + Borsh.lenArray(signature) + 1;
  }
}
