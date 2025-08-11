package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ProfitAndLoss(long profit, long loss) implements Borsh {

  public static final int BYTES = 16;

  public static ProfitAndLoss read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var profit = getInt64LE(_data, i);
    i += 8;
    final var loss = getInt64LE(_data, i);
    return new ProfitAndLoss(profit, loss);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, profit);
    i += 8;
    putInt64LE(_data, i, loss);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
