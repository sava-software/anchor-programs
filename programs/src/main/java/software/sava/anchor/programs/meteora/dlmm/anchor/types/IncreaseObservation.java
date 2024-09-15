package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record IncreaseObservation(PublicKey oracle, long newObservationLength) implements Borsh {

  public static final int BYTES = 40;

  public static IncreaseObservation read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var oracle = readPubKey(_data, i);
    i += 32;
    final var newObservationLength = getInt64LE(_data, i);
    return new IncreaseObservation(oracle, newObservationLength);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    oracle.write(_data, i);
    i += 32;
    putInt64LE(_data, i, newObservationLength);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
