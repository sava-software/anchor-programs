package software.sava.anchor.programs.jupiter.dca.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record WithdrawParams(long withdrawAmount, Withdrawal withdrawal) implements Borsh {

  public static final int BYTES = 9;

  public static WithdrawParams read(final byte[] _data, final int offset) {
    int i = offset;
    final var withdrawAmount = getInt64LE(_data, i);
    i += 8;
    final var withdrawal = Withdrawal.read(_data, i);
    return new WithdrawParams(withdrawAmount, withdrawal);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, withdrawAmount);
    i += 8;
    i += Borsh.write(withdrawal, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
