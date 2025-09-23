package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record AddInternalOracleParams(int expo, PublicKey extOracle) implements Borsh {

  public static final int BYTES = 36;

  public static AddInternalOracleParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var expo = getInt32LE(_data, i);
    i += 4;
    final var extOracle = readPubKey(_data, i);
    return new AddInternalOracleParams(expo, extOracle);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, expo);
    i += 4;
    extOracle.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
