package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record MarketIdentifier(MarketType marketType, int marketIndex) implements Borsh {

  public static final int BYTES = 3;

  public static MarketIdentifier read(final byte[] _data, final int offset) {
    int i = offset;
    final var marketType = MarketType.read(_data, i);
    i += Borsh.len(marketType);
    final var marketIndex = getInt16LE(_data, i);
    return new MarketIdentifier(marketType, marketIndex);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(marketType, _data, i);
    putInt16LE(_data, i, marketIndex);
    i += 2;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(marketType) + 2;
  }
}
