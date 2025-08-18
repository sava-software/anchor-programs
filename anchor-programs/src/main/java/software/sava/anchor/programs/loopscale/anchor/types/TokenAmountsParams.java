package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record TokenAmountsParams(long tokenAAmount, long tokenBAmount) implements Borsh {

  public static final int BYTES = 16;

  public static TokenAmountsParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var tokenAAmount = getInt64LE(_data, i);
    i += 8;
    final var tokenBAmount = getInt64LE(_data, i);
    return new TokenAmountsParams(tokenAAmount, tokenBAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, tokenAAmount);
    i += 8;
    putInt64LE(_data, i, tokenBAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
