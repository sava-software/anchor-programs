package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.util.OptionalInt;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record GetAddCollateralQuoteParams(long amountIn,
                                          boolean isIncreaseSize,
                                          long sizeDelta,
                                          Privilege privilege,
                                          OptionalInt discountIndex) implements Borsh {

  public static GetAddCollateralQuoteParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var amountIn = getInt64LE(_data, i);
    i += 8;
    final var isIncreaseSize = _data[i] == 1;
    ++i;
    final var sizeDelta = getInt64LE(_data, i);
    i += 8;
    final var privilege = Privilege.read(_data, i);
    i += Borsh.len(privilege);
    final var discountIndex = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
    return new GetAddCollateralQuoteParams(amountIn,
                                           isIncreaseSize,
                                           sizeDelta,
                                           privilege,
                                           discountIndex);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, amountIn);
    i += 8;
    _data[i] = (byte) (isIncreaseSize ? 1 : 0);
    ++i;
    putInt64LE(_data, i, sizeDelta);
    i += 8;
    i += Borsh.write(privilege, _data, i);
    i += Borsh.writeOptionalbyte(discountIndex, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8
         + 1
         + 8
         + Borsh.len(privilege)
         + (discountIndex == null || discountIndex.isEmpty() ? 1 : (1 + 1));
  }
}
