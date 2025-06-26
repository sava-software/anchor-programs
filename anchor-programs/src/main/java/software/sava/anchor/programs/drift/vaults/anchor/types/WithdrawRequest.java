package software.sava.anchor.programs.drift.vaults.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record WithdrawRequest(// request shares of vault withdraw
                              BigInteger shares,
                              // requested value (in vault spot_market_index) of shares for withdraw
                              long value,
                              // request ts of vault withdraw
                              long ts) implements Borsh {

  public static final int BYTES = 32;

  public static WithdrawRequest read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var shares = getInt128LE(_data, i);
    i += 16;
    final var value = getInt64LE(_data, i);
    i += 8;
    final var ts = getInt64LE(_data, i);
    return new WithdrawRequest(shares, value, ts);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, shares);
    i += 16;
    putInt64LE(_data, i, value);
    i += 8;
    putInt64LE(_data, i, ts);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
