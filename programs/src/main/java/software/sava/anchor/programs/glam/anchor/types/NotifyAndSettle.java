package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record NotifyAndSettle(ValuationModel model,
                              long noticePeriod,
                              NoticePeriodType noticePeriodType,
                              boolean permissionlessFulfillment,
                              long settlementPeriod,
                              long cancellationWindow,
                              int padding) implements Borsh {

  public static final int BYTES = 28;

  public static NotifyAndSettle read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var model = ValuationModel.read(_data, i);
    i += Borsh.len(model);
    final var noticePeriod = getInt64LE(_data, i);
    i += 8;
    final var noticePeriodType = NoticePeriodType.read(_data, i);
    i += Borsh.len(noticePeriodType);
    final var permissionlessFulfillment = _data[i] == 1;
    ++i;
    final var settlementPeriod = getInt64LE(_data, i);
    i += 8;
    final var cancellationWindow = getInt64LE(_data, i);
    i += 8;
    final var padding = _data[i] & 0xFF;
    return new NotifyAndSettle(model,
                               noticePeriod,
                               noticePeriodType,
                               permissionlessFulfillment,
                               settlementPeriod,
                               cancellationWindow,
                               padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(model, _data, i);
    putInt64LE(_data, i, noticePeriod);
    i += 8;
    i += Borsh.write(noticePeriodType, _data, i);
    _data[i] = (byte) (permissionlessFulfillment ? 1 : 0);
    ++i;
    putInt64LE(_data, i, settlementPeriod);
    i += 8;
    putInt64LE(_data, i, cancellationWindow);
    i += 8;
    _data[i] = (byte) padding;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
