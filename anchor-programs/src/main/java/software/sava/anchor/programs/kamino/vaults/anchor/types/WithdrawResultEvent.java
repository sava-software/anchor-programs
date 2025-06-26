package software.sava.anchor.programs.kamino.vaults.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record WithdrawResultEvent(long sharesToBurn,
                                  long availableToSendToUser,
                                  long investedToDisinvestCtokens,
                                  long investedLiquidityToSendToUser) implements Borsh {

  public static final int BYTES = 32;

  public static WithdrawResultEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var sharesToBurn = getInt64LE(_data, i);
    i += 8;
    final var availableToSendToUser = getInt64LE(_data, i);
    i += 8;
    final var investedToDisinvestCtokens = getInt64LE(_data, i);
    i += 8;
    final var investedLiquidityToSendToUser = getInt64LE(_data, i);
    return new WithdrawResultEvent(sharesToBurn,
                                   availableToSendToUser,
                                   investedToDisinvestCtokens,
                                   investedLiquidityToSendToUser);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, sharesToBurn);
    i += 8;
    putInt64LE(_data, i, availableToSendToUser);
    i += 8;
    putInt64LE(_data, i, investedToDisinvestCtokens);
    i += 8;
    putInt64LE(_data, i, investedLiquidityToSendToUser);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
