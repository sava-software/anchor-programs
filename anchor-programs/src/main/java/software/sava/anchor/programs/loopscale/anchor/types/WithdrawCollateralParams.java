package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// Remaining accounts:
//
//num ledgers = L
//
//1. 0 -> 2L-1: Strategy + MarketInformation for ledger L_i
//
//2. Healthcheck:
//    For each ledger:
//    Ledger market information
//    Principal oracle accounts + conversion oracle accounts
//    Then again for each ledger and collateral:
//        Collateral oracle accounts + conversion oracle accounts
//
//
//Asset index guidance:
//1. Healthcheck:
//    For each ledger:
//        For each collateral in the loan:
//            Principal index
//            Collateral index
public record WithdrawCollateralParams(long amount,
                                       int collateralIndex,
                                       byte[] assetIndexGuidance,
                                       ExpectedLoanValues expectedLoanValues,
                                       boolean closeIfEligible) implements Borsh {

  public static WithdrawCollateralParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var collateralIndex = _data[i] & 0xFF;
    ++i;
    final var assetIndexGuidance = Borsh.readbyteVector(_data, i);
    i += Borsh.lenVector(assetIndexGuidance);
    final var expectedLoanValues = ExpectedLoanValues.read(_data, i);
    i += Borsh.len(expectedLoanValues);
    final var closeIfEligible = _data[i] == 1;
    return new WithdrawCollateralParams(amount,
                                        collateralIndex,
                                        assetIndexGuidance,
                                        expectedLoanValues,
                                        closeIfEligible);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, amount);
    i += 8;
    _data[i] = (byte) collateralIndex;
    ++i;
    i += Borsh.writeVector(assetIndexGuidance, _data, i);
    i += Borsh.write(expectedLoanValues, _data, i);
    _data[i] = (byte) (closeIfEligible ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return 8
         + 1
         + Borsh.lenVector(assetIndexGuidance)
         + Borsh.len(expectedLoanValues)
         + 1;
  }
}
