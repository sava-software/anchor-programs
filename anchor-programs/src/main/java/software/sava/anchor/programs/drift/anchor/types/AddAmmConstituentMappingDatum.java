package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record AddAmmConstituentMappingDatum(int constituentIndex,
                                            int perpMarketIndex,
                                            long weight) implements Borsh {

  public static final int BYTES = 12;

  public static AddAmmConstituentMappingDatum read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var constituentIndex = getInt16LE(_data, i);
    i += 2;
    final var perpMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var weight = getInt64LE(_data, i);
    return new AddAmmConstituentMappingDatum(constituentIndex, perpMarketIndex, weight);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, constituentIndex);
    i += 2;
    putInt16LE(_data, i, perpMarketIndex);
    i += 2;
    putInt64LE(_data, i, weight);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
