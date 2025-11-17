package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// Price data for standard Chainlink types (v3, v7, v8, v9)
public record ChainlinkStandardPriceData(long observationsTimestamp) implements Borsh {

  public static final int BYTES = 8;

  public static ChainlinkStandardPriceData read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var observationsTimestamp = getInt64LE(_data, offset);
    return new ChainlinkStandardPriceData(observationsTimestamp);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, observationsTimestamp);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
