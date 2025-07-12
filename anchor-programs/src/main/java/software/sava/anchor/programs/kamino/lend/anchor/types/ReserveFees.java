package software.sava.anchor.programs.kamino.lend.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// Additional fee information on a reserve
// 
// These exist separately from interest accrual fees, and are specifically for the program owner
// and referral fee. The fees are paid out as a percentage of liquidity token amounts during
// repayments and liquidations.
public record ReserveFees(// Fee assessed on `BorrowObligationLiquidity`, as scaled fraction (60 bits fractional part)
                          // Must be between `0` and `2^60`, such that `2^60 = 1`.  A few examples for
                          // clarity:
                          // 1% = (1 << 60) / 100 = 11529215046068470
                          // 0.01% (1 basis point) = 115292150460685
                          // 0.00001% (Aave borrow fee) = 115292150461
                          long borrowFeeSf,
                          // Fee for flash loan, expressed as scaled fraction.
                          // 0.3% (Aave flash loan fee) = 0.003 * 2^60 = 3458764513820541
                          long flashLoanFeeSf,
                          // Used for allignment
                          byte[] padding) implements Borsh {

  public static final int BYTES = 24;
  public static final int PADDING_LEN = 8;

  public static ReserveFees read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var borrowFeeSf = getInt64LE(_data, i);
    i += 8;
    final var flashLoanFeeSf = getInt64LE(_data, i);
    i += 8;
    final var padding = new byte[8];
    Borsh.readArray(padding, _data, i);
    return new ReserveFees(borrowFeeSf, flashLoanFeeSf, padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, borrowFeeSf);
    i += 8;
    putInt64LE(_data, i, flashLoanFeeSf);
    i += 8;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
