package software.sava.anchor.programs.drift.anchor.types;

import java.util.OptionalLong;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record PrelaunchOracleParams(int perpMarketIndex,
                                    OptionalLong price,
                                    OptionalLong maxPrice) implements Borsh {

  public static PrelaunchOracleParams read(final byte[] _data, final int offset) {
    int i = offset;
    final var perpMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var price = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (price.isPresent()) {
      i += 8;
    }
    final var maxPrice = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    return new PrelaunchOracleParams(perpMarketIndex, price, maxPrice);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, perpMarketIndex);
    i += 2;
    i += Borsh.writeOptional(price, _data, i);
    i += Borsh.writeOptional(maxPrice, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 2 + 9 + 9;
  }
}
