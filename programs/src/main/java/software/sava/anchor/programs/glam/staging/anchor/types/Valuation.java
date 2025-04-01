package software.sava.anchor.programs.glam.staging.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Valuation(ValuationModel model,
                        long noticePeriod,
                        long settlementPeriod,
                        TimeUnit timeUnit) implements Borsh {

  public static final int BYTES = 18;

  public static Valuation read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var model = ValuationModel.read(_data, i);
    i += Borsh.len(model);
    final var noticePeriod = getInt64LE(_data, i);
    i += 8;
    final var settlementPeriod = getInt64LE(_data, i);
    i += 8;
    final var timeUnit = TimeUnit.read(_data, i);
    return new Valuation(model,
                         noticePeriod,
                         settlementPeriod,
                         timeUnit);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(model, _data, i);
    putInt64LE(_data, i, noticePeriod);
    i += 8;
    putInt64LE(_data, i, settlementPeriod);
    i += 8;
    i += Borsh.write(timeUnit, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
