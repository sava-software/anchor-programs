package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record InitParams(int minSignatures,
                         Permissions permissions,
                         VoltageMultiplier voltageMultiplier,
                         long[] tradingDiscount,
                         long[] referralRebate,
                         long defaultRebate) implements Borsh {

  public static final int BYTES = 142;
  public static final int TRADING_DISCOUNT_LEN = 6;
  public static final int REFERRAL_REBATE_LEN = 6;

  public static InitParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var minSignatures = _data[i] & 0xFF;
    ++i;
    final var permissions = Permissions.read(_data, i);
    i += Borsh.len(permissions);
    final var voltageMultiplier = VoltageMultiplier.read(_data, i);
    i += Borsh.len(voltageMultiplier);
    final var tradingDiscount = new long[6];
    i += Borsh.readArray(tradingDiscount, _data, i);
    final var referralRebate = new long[6];
    i += Borsh.readArray(referralRebate, _data, i);
    final var defaultRebate = getInt64LE(_data, i);
    return new InitParams(minSignatures,
                          permissions,
                          voltageMultiplier,
                          tradingDiscount,
                          referralRebate,
                          defaultRebate);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) minSignatures;
    ++i;
    i += Borsh.write(permissions, _data, i);
    i += Borsh.write(voltageMultiplier, _data, i);
    i += Borsh.writeArrayChecked(tradingDiscount, 6, _data, i);
    i += Borsh.writeArrayChecked(referralRebate, 6, _data, i);
    putInt64LE(_data, i, defaultRebate);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
