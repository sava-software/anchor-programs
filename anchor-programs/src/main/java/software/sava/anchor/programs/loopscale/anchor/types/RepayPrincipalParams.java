package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// No remaining accounts or asset index guidance needed. In full repayment, we zero out the matrix for the specific ledger using default loan values.
public record RepayPrincipalParams(long amount,
                                   int ledgerIndex,
                                   boolean repayAll) implements Borsh {

  public static final int BYTES = 10;

  public static RepayPrincipalParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var ledgerIndex = _data[i] & 0xFF;
    ++i;
    final var repayAll = _data[i] == 1;
    return new RepayPrincipalParams(amount, ledgerIndex, repayAll);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, amount);
    i += 8;
    _data[i] = (byte) ledgerIndex;
    ++i;
    _data[i] = (byte) (repayAll ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
