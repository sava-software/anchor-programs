package software.sava.anchor.programs.kamino.vaults.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record DepositResultEvent(long sharesToMint,
                                 long tokenToDeposit,
                                 long crankFundsToDeposit) implements Borsh {

  public static final int BYTES = 24;

  public static DepositResultEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var sharesToMint = getInt64LE(_data, i);
    i += 8;
    final var tokenToDeposit = getInt64LE(_data, i);
    i += 8;
    final var crankFundsToDeposit = getInt64LE(_data, i);
    return new DepositResultEvent(sharesToMint, tokenToDeposit, crankFundsToDeposit);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, sharesToMint);
    i += 8;
    putInt64LE(_data, i, tokenToDeposit);
    i += 8;
    putInt64LE(_data, i, crankFundsToDeposit);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
