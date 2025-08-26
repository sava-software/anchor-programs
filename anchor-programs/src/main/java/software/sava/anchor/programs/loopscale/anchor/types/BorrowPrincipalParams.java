package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record BorrowPrincipalParams(long amount,
                                    byte[] assetIndexGuidance,
                                    int duration,
                                    ExpectedLoanValues expectedLoanValues,
                                    boolean skipSolUnwrap) implements Borsh {

  public static BorrowPrincipalParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var assetIndexGuidance = Borsh.readbyteVector(_data, i);
    i += Borsh.lenVector(assetIndexGuidance);
    final var duration = _data[i] & 0xFF;
    ++i;
    final var expectedLoanValues = ExpectedLoanValues.read(_data, i);
    i += Borsh.len(expectedLoanValues);
    final var skipSolUnwrap = _data[i] == 1;
    return new BorrowPrincipalParams(amount,
                                     assetIndexGuidance,
                                     duration,
                                     expectedLoanValues,
                                     skipSolUnwrap);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, amount);
    i += 8;
    i += Borsh.writeVector(assetIndexGuidance, _data, i);
    _data[i] = (byte) duration;
    ++i;
    i += Borsh.write(expectedLoanValues, _data, i);
    _data[i] = (byte) (skipSolUnwrap ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return 8
         + Borsh.lenVector(assetIndexGuidance)
         + 1
         + Borsh.len(expectedLoanValues)
         + 1;
  }
}
