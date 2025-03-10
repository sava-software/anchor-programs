package software.sava.anchor.programs.jupiter.swap.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record AmountWithSlippage(long amount, int slippageBps) implements Borsh {

  public static final int BYTES = 10;

  public static AmountWithSlippage read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var slippageBps = getInt16LE(_data, i);
    return new AmountWithSlippage(amount, slippageBps);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, amount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
