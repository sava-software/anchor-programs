package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record VoltagePointsLog(PublicKey tokenStake,
                               long voltagePoints,
                               long rebateUsd,
                               int voltagePointsType,
                               long[] padding) implements Borsh {

  public static final int BYTES = 81;
  public static final int PADDING_LEN = 4;

  public static VoltagePointsLog read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var tokenStake = readPubKey(_data, i);
    i += 32;
    final var voltagePoints = getInt64LE(_data, i);
    i += 8;
    final var rebateUsd = getInt64LE(_data, i);
    i += 8;
    final var voltagePointsType = _data[i] & 0xFF;
    ++i;
    final var padding = new long[4];
    Borsh.readArray(padding, _data, i);
    return new VoltagePointsLog(tokenStake,
                                voltagePoints,
                                rebateUsd,
                                voltagePointsType,
                                padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    tokenStake.write(_data, i);
    i += 32;
    putInt64LE(_data, i, voltagePoints);
    i += 8;
    putInt64LE(_data, i, rebateUsd);
    i += 8;
    _data[i] = (byte) voltagePointsType;
    ++i;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
